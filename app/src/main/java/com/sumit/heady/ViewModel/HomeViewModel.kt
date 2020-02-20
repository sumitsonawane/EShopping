package com.sumit.heady.ViewModel

import androidx.lifecycle.MutableLiveData
import com.sumit.heady.Base.BaseViewmodel
import com.sumit.heady.Constants.subscribeNewResponse
import com.sumit.heady.Model.ApiResponseStats
import com.sumit.heady.Model.GetAllProducts
import com.sumit.heady.Repositories.ProductsRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomeViewModel : BaseViewmodel() {

    var tradeFeeLiveData : MutableLiveData<ApiResponseStats<GetAllProducts>>? = null
    fun callProductsAPI() {

        addToDisposable(
            ProductsRepository().callProducts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError { tradeFeeLiveData?.value = ApiResponseStats(ApiResponseStats.Status.ERROR, null, it) }
            .doOnSubscribe { tradeFeeLiveData?.value = ApiResponseStats(ApiResponseStats.Status.LOADING, null, null) }
            .doOnSuccess {
                tradeFeeLiveData?.value = ApiResponseStats(ApiResponseStats.Status.SUCCESS, null, null)
            }
            .subscribeNewResponse { tradeFeeLiveData?.value = ApiResponseStats(ApiResponseStats.Status.COMPLETED, it, null) }
        )/*.subscribeNewResponse {
                    when (it.errCode) {
                        HttpURLConnection.HTTP_OK -> tradeFeeLiveData.value = it.data
                        else -> tradeFeeLiveData.value = null
                    }
                })*/

    }

    fun getProductsList(): MutableLiveData<ApiResponseStats<GetAllProducts>>? {
        if (tradeFeeLiveData == null) {
            tradeFeeLiveData = MutableLiveData<ApiResponseStats<GetAllProducts>>()
            callProductsAPI()
        }
        return tradeFeeLiveData
    }

}
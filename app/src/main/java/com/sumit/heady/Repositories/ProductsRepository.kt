package com.sumit.heady.Repositories

import com.postgram.android.data.Remote.ApiUtils
import com.sumit.heady.Model.GetAllProducts
import io.reactivex.Single
import retrofit2.Response

class ProductsRepository {

    companion object {
        private var ProductsRepository: ProductsRepository? = null
        fun getInstance(): ProductsRepository {
            if (ProductsRepository == null) {
                ProductsRepository = ProductsRepository()
            }
            return ProductsRepository as ProductsRepository
        }

    }

    fun callProducts(): Single<Response<GetAllProducts>> {
        return ApiUtils.getProductsService()
            .getProductList()


    }

}
package com.postgram.android.data.Remote



import com.sumit.heady.Model.GetAllProducts
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.*

/**
 * Created by Sarvesh on 9/23/2019.
 */
interface ExchangeService {

    @GET("/json")
    fun getProductList(): Single<Response<GetAllProducts>>



}
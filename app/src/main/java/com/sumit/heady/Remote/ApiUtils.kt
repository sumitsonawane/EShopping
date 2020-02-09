package com.postgram.android.data.Remote

import android.content.Context

/**
 * Created by TECH on 02-12-2017.
 */

object ApiUtils {

    fun getProductsService(): ExchangeService {
        return RetrofitClient.getClient().create(ExchangeService::class.java)
    }
}
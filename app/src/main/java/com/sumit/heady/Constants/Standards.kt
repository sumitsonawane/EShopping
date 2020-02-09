package com.sumit.heady.Constants

import android.net.Uri
import android.widget.ImageView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import androidx.viewpager.widget.ViewPager

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import com.sumit.heady.Base.BaseResponse
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.exceptions.CompositeException
import io.reactivex.functions.Consumer
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Response
import java.net.ConnectException
import java.net.UnknownHostException

val PRODUCT_LIST = "product_list"


fun <T> Single<Response<T>>.subscribeNewResponse(listener: (BaseResponse<T>) -> Unit): Disposable {
    return this
            .flatMap {
                if (it.isSuccessful) {
                    return@flatMap Single.just(BaseResponse(it.code(), it.message(), it.body()))
                } else {
                    var body = it.body()
                    val (message, code) = try {
                        val jObj = JSONObject(it.errorBody()?.string())
                        if (jObj.has("data")) {
                            body = jObj as T
                        } else if (jObj.has("is_blocked")) {
                            body = jObj as T
                        } else if (it.code() == 406) {
                            body = jObj as T
                        } else if (it.code() == 429) {
                            body = jObj as T
                        }
                        Pair(jObj.optString("message"), jObj.optInt("error_code", it.code()))

                    } catch (e: JSONException) {
                        Pair(it.message(), it.code())
                    }
                    return@flatMap Single.just(BaseResponse(code, message, body))
                }
            }
            .onErrorReturn {
                return@onErrorReturn when (it) {
                    is HttpException -> BaseResponse(it.code(), it.message())
                    is JSONException -> BaseResponse(BaseResponse.INVALID_JSON, "Invalid JSON")
                    is UnknownHostException, is ConnectException -> BaseResponse(BaseResponse.NO_INTERNET, "No Internet connection")
                    is CompositeException -> BaseResponse(BaseResponse.NO_INTERNET, it.let {
                        val message = ""
                        it.exceptions.forEach { e -> message.plus(e.message).plus("\n") }
                        return@let message
                    })

                    else -> BaseResponse(0, it.message.orEmpty())
                }
            }
            .subscribe(Consumer { listener(it) })
}

fun <T : Any?> T.notnull(block: (T) -> Unit) {
    if (this != null) {
        block(this)
    }
}



/*
fun Spinner.populateItems(items: MutableList<String>, itemSelectedListener: (Int) -> Unit) {

    val dataAdapter = ArrayAdapter<String>(context, R.layout.spinner_filter_text, items)
    dataAdapter.setDropDownViewResource(R.layout.spinner_filter_dropdown)
    adapter = dataAdapter
    onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

        override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
            setBackgroundResource(R.drawable.bg_spinner_filter)
            itemSelectedListener(position)
        }

        override fun onNothingSelected(arg0: AdapterView<*>) {
            setBackgroundResource(R.drawable.bg_spinner_filter)
        }
    }
}*/



fun ImageView.toImageLoader(url: Int) {
    Glide.with(this)
            .load(url)
            .apply(
                RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
            )
            .into(this)

}


fun ImageView.toOnlineImageLoader(url: String) {
    Glide.with(this)
        .load(url)
        .apply(
            RequestOptions().skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)

        )
        .into(this)

}



fun <T> T.toJson() = Gson().toJson(this)

fun <T> String.fromJson(cls: Class<T>) = Gson().fromJson(this, cls)






fun onTabSelectedListener(viewPager: ViewPager): TabLayout.OnTabSelectedListener {

    return object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab) {
            viewPager.currentItem = tab.position
        }

        override fun onTabUnselected(tab: TabLayout.Tab) {

        }

        override fun onTabReselected(tab: TabLayout.Tab) {

        }
    }
}


fun <T : ViewModel> T.createFactory(): ViewModelProvider.Factory {
    val viewModel = this
    return object : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T = viewModel as T
    }
}

fun RecyclerView.disableItemAnimator() {
    (itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations = false
}





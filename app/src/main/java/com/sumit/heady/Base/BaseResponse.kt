package com.sumit.heady.Base

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


open class BaseResponse<T> {

    @SerializedName("errCode")
    var errCode: Int = 0

    @SerializedName(value = "errMsg", alternate = ["message"])
    var errMsg: String = ""

    @SerializedName("data")
    @Expose
    var data: T? = null


    constructor(errCode: Int, errMsg: String) {
        this.errCode = errCode
        this.errMsg = errMsg
    }

    constructor(errCode: Int, errMsg: String, data: T?) {
        this.errCode = errCode
        this.errMsg = errMsg
        this.data = data
    }

    constructor() {}

    override fun toString(): String {
        return "BaseResponse{" +
                "errCode=" + errCode +
                ", errMsg='" + errMsg + '\''.toString() +
                ", data=" + data +
                '}'.toString()
    }

    companion object {

        val SUCCESS_CODE: Int? = -1
        val NO_INTERNET = -100
        val INVALID_JSON = -120
    }
}

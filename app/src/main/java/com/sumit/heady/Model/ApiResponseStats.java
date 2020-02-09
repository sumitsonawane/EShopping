package com.sumit.heady.Model;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.sumit.heady.Base.BaseResponse;

public class ApiResponseStats<T> {

    public final Status status;

    @Expose
    @Nullable
    public BaseResponse<T> data;

    @Nullable
    public final Throwable error;

    public ApiResponseStats(Status status, @Nullable BaseResponse<T> data, @Nullable Throwable error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }

    public enum Status {
        LOADING,
        SUCCESS,
        ERROR,
        COMPLETED
    }
}
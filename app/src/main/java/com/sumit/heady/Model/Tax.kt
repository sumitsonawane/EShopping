package com.sumit.heady.Model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Tax(
    val name: String? =null,
    val value: Double? =null
):Parcelable
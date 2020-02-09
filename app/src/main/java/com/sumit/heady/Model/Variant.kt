package com.sumit.heady.Model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Variant(
    val color: String? = null,
    val id: Int? = null,
    val price: Int? = null,
    val size: Double? = null,
    var isSelected: Boolean = false
) : Parcelable
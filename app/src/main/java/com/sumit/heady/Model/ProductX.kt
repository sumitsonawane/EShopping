package com.sumit.heady.Model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.parceler.Parcel

@Parcelize
data class ProductX(


    val date_added: String ? =null,
    val id: Int? =null,
    val shares: Int? =null,
    val name: String? =null,
    val tax: Tax? =null,
    val variants: MutableList<Variant>? =null


) : Parcelable{
    override fun hashCode() = id.hashCode()
    override fun equals(other: Any?) = other?.let { id == (it as ProductX).id } ?: false
}
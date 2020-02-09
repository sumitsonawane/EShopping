package com.sumit.heady.Model

data class Category(
    val child_categories: List<Int>,
    val id: Int,
    val name: String,
    val products: List<ProductX>
)
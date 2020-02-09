package com.sumit.heady.Model

data class GetAllProducts(
    val categories: MutableList<Category>,
    val rankings: MutableList<Ranking>
)
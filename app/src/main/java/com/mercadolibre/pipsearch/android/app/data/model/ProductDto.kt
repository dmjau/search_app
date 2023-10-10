package com.mercadolibre.pipsearch.android.app.data.model

data class ProductDto(
    val title: String,
    val price: Double,
    val thumbnail: String,
    val tags: List<String>
)
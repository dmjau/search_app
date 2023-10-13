package com.mercadolibre.pipsearch.android.app.data.model

import com.mercadolibre.android.commons.serialization.annotations.Model

@Model
data class ItemDto(
    val title: String,
    val price: Double,
    val thumbnail: String,
    val tags: List<String>
)

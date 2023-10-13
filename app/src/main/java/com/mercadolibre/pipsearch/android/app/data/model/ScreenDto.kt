package com.mercadolibre.pipsearch.android.app.data.model

import com.mercadolibre.android.commons.serialization.annotations.Model

@Model
class ScreenDto(
    val query: String,
    val results: List<ProductDto>
)

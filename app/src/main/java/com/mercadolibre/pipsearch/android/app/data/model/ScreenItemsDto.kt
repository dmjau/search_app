package com.mercadolibre.pipsearch.android.app.data.model

import com.mercadolibre.android.commons.serialization.annotations.Model

@Model
data class ScreenItemsDto(
    val results: List<ItemDto>
)

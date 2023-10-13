package com.mercadolibre.pipsearch.android.app.data.repository

import com.mercadolibre.android.restclient.model.RestClientResult
import com.mercadolibre.pipsearch.android.app.data.model.ScreenItemsDto
import com.mercadolibre.pipsearch.android.app.data.service.SearchItemsApiService

class SearchItemsRepository(private val service: SearchItemsApiService) {

    // Function to get all items from ApiService
    suspend fun getAll() : RestClientResult<ScreenItemsDto> = service.getSearchItems()
}
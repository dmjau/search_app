package com.mercadolibre.pipsearch.android.app.data.repository

import com.mercadolibre.android.restclient.model.RestClientResult
import com.mercadolibre.pipsearch.android.app.data.model.ScreenItemsDto
import com.mercadolibre.pipsearch.android.app.data.service.RestClientApiHelper
import com.mercadolibre.pipsearch.android.app.data.service.SearchItemsApiService

/**
 * Repository for fetching screen items from the api service and storing them on array.
 */
class SearchItemsRepository {

    private val service: SearchItemsApiService = RestClientApiHelper.getRestClient()

    // Function to get all items from ApiService
    suspend fun getAll(textToSearch: String) : RestClientResult<ScreenItemsDto> = service.getSearchItems(textToSearch)
}

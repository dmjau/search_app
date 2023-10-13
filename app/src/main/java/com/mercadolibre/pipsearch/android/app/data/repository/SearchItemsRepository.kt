package com.mercadolibre.pipsearch.android.app.data.repository

import com.mercadolibre.android.restclient.RepositoryFactory
import com.mercadolibre.android.restclient.model.RestClientResult
import com.mercadolibre.pipsearch.android.app.data.model.ScreenItemsDto
import com.mercadolibre.pipsearch.android.app.data.service.SearchItemsApiService
import retrofit2.converter.gson.GsonConverterFactory

class SearchItemsRepository {

    private val baseUrl = "https://api.mercadolibre.com"
    private val service = RepositoryFactory
        .newBuilder(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .create(SearchItemsApiService::class.java)

    // Function to get all items from ApiService
    suspend fun getAll() : RestClientResult<ScreenItemsDto> = service.getSearchItems()
}
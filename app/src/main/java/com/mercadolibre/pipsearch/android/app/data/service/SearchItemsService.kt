package com.mercadolibre.pipsearch.android.app.data.service

import com.mercadolibre.android.restclient.RepositoryFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * This is an object of rest client service build with RestClient lib.
 * Call like `SearchItemsService.service`
 */
object SearchItemsService {

    private val service = RepositoryFactory
        .newBuilder("https://api.mercadolibre.com")
        .addConverterFactory(GsonConverterFactory.create())
        .create(SearchItemsApiService::class.java)

    val searchItemService = service
}
package com.mercadolibre.pipsearch.android.app.data.service

import com.mercadolibre.android.restclient.RepositoryFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * This is an object of rest client service build with RestClient lib.
 * Call like `RestClientApiHelper.service`
 */
object RestClientApiHelper {

    private const val URL_BASE = "https://api.mercadolibre.com"
    fun getRestClient() : SearchItemsApiService {
        return RepositoryFactory
            .newBuilder(URL_BASE)
            .addConverterFactory(GsonConverterFactory.create())
            .create(SearchItemsApiService::class.java)
    }
}

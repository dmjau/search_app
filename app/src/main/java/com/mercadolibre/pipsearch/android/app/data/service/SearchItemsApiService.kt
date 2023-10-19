package com.mercadolibre.pipsearch.android.app.data.service

import com.mercadolibre.android.restclient.annotation.ConverterFactory
import com.mercadolibre.android.restclient.annotation.ResponseFormat
import com.mercadolibre.android.restclient.model.RestClientResult
import com.mercadolibre.pipsearch.android.app.data.model.ScreenItemsDto
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * A RestClient service to fetch a full screen with list of items and others fields.
 * @return full search screen
 */
interface SearchItemsApiService {

    // In nexts steps I will change the query for variable String to search any string.
    @GET("sites/MLA/search")
    @ConverterFactory(ResponseFormat.JSON)
    suspend fun getSearchItems(@Query("q") query: String) : RestClientResult<ScreenItemsDto>
}

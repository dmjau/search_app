package com.mercadolibre.pipsearch.android.app.data.service

import com.mercadolibre.android.restclient.annotation.ConverterFactory
import com.mercadolibre.android.restclient.annotation.ResponseFormat
import com.mercadolibre.android.restclient.model.RestClientResult
import com.mercadolibre.pipsearch.android.app.data.model.ScreenItemsDto
import retrofit2.http.GET

interface SearchItemsApiService {

    @GET("sites/MLA/search?q=heineken")
    @ConverterFactory(ResponseFormat.JSON)
    suspend fun getSearchItems() : RestClientResult<ScreenItemsDto>
}

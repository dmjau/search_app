package com.mercadolibre.pipsearch.android.app.data.repository

import com.mercadolibre.android.restclient.RepositoryFactory
import com.mercadolibre.android.restclient.model.RestClientResult
import com.mercadolibre.pipsearch.android.app.data.service.SearchItemsApiService
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

class SearchItemsRepositoryTest {

    private lateinit var repository: SearchItemsRepository
    private lateinit var mockWebServer: MockWebServer
    private lateinit var searchItemsApiService: SearchItemsApiService

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        val baseUrl = mockWebServer.url("/").toString()

        searchItemsApiService = RepositoryFactory
            .newBuilder(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .create(SearchItemsApiService::class.java)

        repository = SearchItemsRepository(searchItemsApiService)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `test call service when response is success`() {
        val responseJson = """{"query": "mock response query"}"""

        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(responseJson)

        mockWebServer.enqueue(response)

        runBlocking {
            val result = repository.getAll()
            assertTrue(result is RestClientResult.Success)

            (result as? RestClientResult.Success)?.data?.let { data ->
                assertEquals("mock response query", data.query)
            }
        }
    }
}
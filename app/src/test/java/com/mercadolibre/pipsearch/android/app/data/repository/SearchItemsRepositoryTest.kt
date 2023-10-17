package com.mercadolibre.pipsearch.android.app.data.repository

import com.mercadolibre.android.restclient.RepositoryFactory
import com.mercadolibre.android.restclient.model.RestClientResult
import com.mercadolibre.pipsearch.android.app.data.service.SearchItemsApiService
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.SocketPolicy
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

class SearchItemsRepositoryTest {

    private lateinit var repository: SearchItemsRepository
    private lateinit var mockWebServer: MockWebServer
    private lateinit var mockWebApiService: SearchItemsApiService

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        val baseUrl = mockWebServer.url("/").toString()

        mockWebApiService = RepositoryFactory
            .newBuilder(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .create(SearchItemsApiService::class.java)

        repository = SearchItemsRepository(mockWebApiService)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun testCallServiceWhenResponseIsSuccess() {
        // given
        val responseJson = """{"results": [{"title": "title dto item test"}]}"""

        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(responseJson)

        // when
        mockWebServer.enqueue(response)

        runBlocking {
            // then
            val result = repository.getAll()
            assertTrue(result is RestClientResult.Success)

            (result as? RestClientResult.Success)?.data?.let { data ->
                assertEquals("title dto item test", data.results[0].title)
            }
        }
    }

    @Test
    fun testCallServiceWhenResponseIsError() {
        // given
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_BAD_REQUEST)

        // when
        mockWebServer.enqueue(response)

        runBlocking {
            // then
            val result = repository.getAll()
            assertTrue(result is RestClientResult.Error)
        }
    }

    @Test
    fun testCallServiceWhenResponseIsException() {
        // given
        val response = MockResponse()
            .setSocketPolicy(SocketPolicy.NO_RESPONSE)

        // when
        mockWebServer.enqueue(response)

        runBlocking {
            // then
            val result = repository.getAll()
            assertTrue(result is RestClientResult.Exception)
        }
    }
}
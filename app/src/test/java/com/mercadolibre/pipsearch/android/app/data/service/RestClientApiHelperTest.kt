package com.mercadolibre.pipsearch.android.app.data.service

import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class RestClientApiHelperTest {

    @Test
    fun testRestClientApiHelperReturnObject() {
        val result = RestClientApiHelper.getRestClient()

        assertNotNull(result)
    }

    @Test
    fun testRestClientApiHelperType() {
        val result = RestClientApiHelper.getRestClient()

        assertTrue(result is SearchItemsApiService)
    }
}

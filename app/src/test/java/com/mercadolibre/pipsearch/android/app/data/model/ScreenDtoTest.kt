package com.mercadolibre.pipsearch.android.app.data.model

import org.junit.Assert.assertEquals
import org.junit.Test

class ScreenDtoTest {

    @Test
    fun `Screen DTO parses ok`() {
        // given
        val listOfTags = listOf("tag_1_test", "tag_1_test", "tag_1_test")
        val productTest1 = ProductDto("productTest 1", 1111.0, "https://test_image_product_test_1.jpg", listOfTags)
        val productTest2 = ProductDto("productTest 2", 2222.0, "https://test_image_product_test_2.jpg", listOfTags)
        val listOfProductsTest = listOf(productTest1, productTest2)
        val screenTest = ScreenDto("search test", listOfProductsTest)

        // then
        assertEquals("search test", screenTest.query)
        assertEquals(productTest1, screenTest.results[0])
        assertEquals(productTest2, screenTest.results[1])
    }
}

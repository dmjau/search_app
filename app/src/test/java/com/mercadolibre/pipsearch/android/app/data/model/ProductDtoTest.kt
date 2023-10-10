package com.mercadolibre.pipsearch.android.app.data.model

import org.junit.Assert.assertEquals
import org.junit.Test

class ProductDtoTest {

    @Test
    fun `Product DTO parses ok`() {
        // given
        val listOfTags = listOf("tag_1_test", "tag_1_test", "tag_1_test")
        val product = ProductDto("title dto test", 1234.0, "https://test_image.jpg", listOfTags)

        // then
        assertEquals("title dto test", product.title)
        assertEquals(1234.0, product.price, 0.0)
        assertEquals("https://test_image.jpg", product.thumbnail)
        assertEquals(listOfTags, product.tags)
    }
}
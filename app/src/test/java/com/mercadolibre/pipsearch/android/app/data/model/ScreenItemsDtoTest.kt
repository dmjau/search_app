package com.mercadolibre.pipsearch.android.app.data.model

import org.junit.Assert.assertEquals
import org.junit.Test

class ScreenItemsDtoTest {


    @Test
    fun `Screen Items DTO parses ok`() {
        // given
        val listOfTags = listOf("tag_1_test", "tag_1_test", "tag_1_test")
        val itemTest1 = ProductDto(
            "itemTest 1",
            1111.0,
            "https://test_image_item_test_1.jpg",
            listOfTags
        )
        val itemTest2 = ProductDto(
            "itemTest 2",
            2222.0,
            "https://test_image_item_test_2.jpg",
            listOfTags
        )
        val listOfProductsTest = listOf(itemTest1, itemTest2)
        val screenTest = ScreenItemsDto("search test", listOfProductsTest)

        // then
        assertEquals("search test", screenTest.query)
        assertEquals(itemTest1, screenTest.results[0])
        assertEquals(itemTest2, screenTest.results[1])
    }
}

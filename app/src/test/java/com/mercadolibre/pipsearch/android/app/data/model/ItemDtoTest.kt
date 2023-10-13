package com.mercadolibre.pipsearch.android.app.data.model

import org.junit.Assert.assertEquals
import org.junit.Test

class ItemDtoTest {

    @Test
    fun `Item DTO parses ok`() {
        // given
        val listOfTags = listOf("tag_1_test", "tag_1_test", "tag_1_test")
        val itemTest = ItemDto(
            "title dto item test",
            1234.0,
            "https://test_item_image.jpg",
            listOfTags
        )

        // then
        assertEquals("title dto test", itemTest.title)
        assertEquals(1234.0, itemTest.price, 0.0)
        assertEquals("https://test_item_image.jpg", itemTest.thumbnail)
        assertEquals(listOfTags, itemTest.tags)
    }
}

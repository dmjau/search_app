package com.mercadolibre.pipsearch.android.app.ui.view.adapters

import android.widget.LinearLayout
import com.facebook.drawee.backends.pipeline.Fresco
import com.mercadolibre.android.testing.AbstractRobolectricTest
import com.mercadolibre.pipsearch.android.app.data.model.ItemDto
import com.mercadolibre.pipsearch.android.app.ui.view.viewholders.MainViewHolder
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test


class MainAdapterTest : AbstractRobolectricTest() {

    override fun setUp() {
        super.setUp()
        Fresco.initialize(context)
    }

    @Test
    fun testMainAdapterInstance() {
        // given
        val mainAdapter = MainAdapter(listOf())

        // then
        assertNotNull(mainAdapter)
    }

    @Test
    fun testMainAdapterCountItems() {
        // given
        val mainAdapter = MainAdapter(listOf())

        // then
        assertEquals(0, mainAdapter.itemCount)
    }

    @Test
    fun testSetListOfItemsInTheMainAdapter() {
        // given
        val itemsMutableList = mutableListOf<ItemDto>()
        val tagsList = listOf("Tag test 1", "Tag test 2")
        val itemData = ItemDto("TestTitle", 1000.0, "http://test-image", tagsList)

        // when
        itemsMutableList.add(itemData)
        itemsMutableList.add(itemData)
        itemsMutableList.add(itemData)
        itemsMutableList.add(itemData)

        val mainAdapter = MainAdapter(itemsMutableList)

        // then
        assertEquals(4, mainAdapter.itemCount)
    }

    @Test
    fun testMainAdapterInstanceMainViewHolder() {
        // given
        val mainAdapter = MainAdapter(listOf())
        val mainViewHolder = mainAdapter.onCreateViewHolder(
            LinearLayout(context),
            0
        )

        // then
        val expectedClassName = "com.mercadolibre.pipsearch.android.app.ui.view.viewholders.MainViewHolder"
        assertEquals(expectedClassName, mainViewHolder.javaClass.canonicalName)
    }

    @Test
    fun testMainAdapterBindView() {
        // given
        val itemsMutableList = mutableListOf<ItemDto>()
        val tagsList = listOf("Tag test 1", "Tag test 2")
        val itemData = ItemDto("TestTitle", 1000.0, "http://test-image", tagsList)

        itemsMutableList.add(itemData)
        itemsMutableList.add(itemData)

        val mockMainViewHolder = mockk<MainViewHolder>()
        every { mockMainViewHolder.bind(any()) } returns Unit

        // when
        val mainAdapter = MainAdapter(itemsMutableList)
        mainAdapter.onBindViewHolder(mockMainViewHolder, 0)

        // then
        verify { mockMainViewHolder.bind(any()) }
    }
}
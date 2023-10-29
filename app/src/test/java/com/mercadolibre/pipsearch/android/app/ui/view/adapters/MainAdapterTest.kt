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
import org.junit.Test


class MainAdapterTest : AbstractRobolectricTest() {

    private lateinit var mainAdapter: MainAdapter

    override fun setUp() {
        super.setUp()
        Fresco.initialize(context)

        this.mainAdapter = MainAdapter { }
    }

    @Test
    fun testMainAdapterInstance() {
        assertNotNull(mainAdapter)
    }

    @Test
    fun testMainAdapterCountItemsOnInit() {
        assertEquals(0, mainAdapter.itemCount)
    }

    @Test
    fun testMainAdapterCountItemsInitialized() {
        // when
        mainAdapter.setItems(listOf())

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

        mainAdapter.setItems(itemsMutableList)

        // then
        assertEquals(4, mainAdapter.itemCount)
    }

    @Test
    fun testMainAdapterInstanceMainViewHolder() {
        // given
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
        every { mockMainViewHolder.bind(any(), any()) } returns Unit

        // when
        mainAdapter.setItems(itemsMutableList)
        mainAdapter.onBindViewHolder(mockMainViewHolder, 0)

        // then
        verify { mockMainViewHolder.bind(any(), any()) }
    }
}
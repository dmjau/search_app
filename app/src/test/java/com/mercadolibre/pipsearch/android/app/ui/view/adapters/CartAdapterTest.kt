package com.mercadolibre.pipsearch.android.app.ui.view.adapters

import android.widget.LinearLayout
import com.facebook.drawee.backends.pipeline.Fresco
import com.mercadolibre.android.testing.AbstractRobolectricTest
import com.mercadolibre.pipsearch.android.app.data.model.ItemDto
import com.mercadolibre.pipsearch.android.app.ui.view.viewholders.CartViewHolder
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Test
import org.robolectric.util.ReflectionHelpers


class CartAdapterTest : AbstractRobolectricTest() {

    private lateinit var cartAdapter: CartAdapter

    override fun setUp() {
        super.setUp()
        Fresco.initialize(context)

        this.cartAdapter = CartAdapter()
    }

    @Test
    fun testCartAdapterCountItemsOnInit() {
        assertEquals(0, cartAdapter.itemCount)
    }

    @Test
    fun testCartAdapterCountItemsInitialized() {
        // when
        cartAdapter.setItems(listOf())

        // then
        assertEquals(0, cartAdapter.itemCount)
    }

    @Test
    fun testSetListOfItemsInTheCartAdapter() {
        // given
        val itemsMutableList = mutableListOf<ItemDto>()
        val tagsList = listOf("Tag test 1", "Tag test 2")
        val itemData = ItemDto("TestTitle", 1000.0, "http://test-image", tagsList)

        // when
        itemsMutableList.add(itemData)
        itemsMutableList.add(itemData)
        itemsMutableList.add(itemData)
        itemsMutableList.add(itemData)

        cartAdapter.setItems(itemsMutableList)

        // then
        assertEquals(4, cartAdapter.itemCount)
    }

    @Test
    fun testCartAdapterInstanceCartViewHolder() {
        // given
        val cartViewHolder = cartAdapter.onCreateViewHolder(
            LinearLayout(context),
            0
        )

        // then
        val expectedType = CartViewHolder::class.javaObjectType
        assertEquals(expectedType, cartViewHolder.javaClass)
    }

    @Test
    fun testCartAdapterBindView() {
        // given
        val itemsMutableList = mutableListOf<ItemDto>()
        val tagsList = listOf("Tag test 1", "Tag test 2")
        val itemData = ItemDto("TestTitle", 1000.0, "http://test-image", tagsList)

        itemsMutableList.add(itemData)
        itemsMutableList.add(itemData)

        val mockCartViewHolder = mockk<CartViewHolder>()
        every { mockCartViewHolder.bind(any(), any()) } returns Unit

        // when
        cartAdapter.setItems(itemsMutableList)
        cartAdapter.onBindViewHolder(mockCartViewHolder, 0)

        // then
        verify { mockCartViewHolder.bind(any(), any()) }
    }

    @Test
    fun testSetOnItemDataClickListener() {
        // given
        val mockListener: (ItemDto) -> Unit = mockk()

        // when
        cartAdapter.setOnItemDataClickListener(mockListener)


        val reflectionOnItemDataClickListener = ReflectionHelpers.getField<((ItemDto) -> Unit)?>(cartAdapter, "onItemDataClickListener")

        assertEquals(mockListener, reflectionOnItemDataClickListener)
    }
}

package com.mercadolibre.pipsearch.android.app.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mercadolibre.pipsearch.android.app.data.model.ItemDto
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.robolectric.util.ReflectionHelpers

class CartManagerTest {

    private lateinit var cartManager: CartManager

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        cartManager = CartManager
    }

    @After
    fun tearDown() {
        cartManager.resetState()
    }

    @Test
    fun testGetInstanceReturnTheSameInstance() {

        // given
        val cartManagerInstance1 = CartManager
        val cartManagerInstance2 = CartManager

        // then
        assertEquals(cartManagerInstance1, cartManagerInstance2)
    }

    @Test
    fun testCartManagerAddItemToCart() {
        // given
        val testItem1 = ItemDto("Item 1", 10.0, "test_1", emptyList())
        val testItem2 = ItemDto("Item 2", 20.0, "test_2", emptyList())

        // when added first item
        cartManager.addItemToCart(testItem1)

        var reflectionListItemsOnCart =
            ReflectionHelpers.getField<MutableList<ItemDto>>(
                cartManager,
                "itemsOnCart"
            )

        // then
        assertEquals(1, reflectionListItemsOnCart.size)
        assertEquals(testItem1, cartManager.itemsOnCart[0])

        // when added second item
        cartManager.addItemToCart(testItem2)

        reflectionListItemsOnCart = ReflectionHelpers.getField(cartManager, "itemsOnCart")

        // then
        assertEquals(2, reflectionListItemsOnCart.size)
        assertEquals(testItem2, cartManager.itemsOnCart[1])
    }

    @Test
    fun testCartManagerAddItemToCartAndRemoveItemFromCart() {
        // given
        val testItem1 = ItemDto("Item 1", 10.0, "test_1", emptyList())
        val testItem2 = ItemDto("Item 2", 20.0, "test_2", emptyList())

        // when added first item
        cartManager.addItemToCart(testItem1)

        var reflectionListItemsOnCart =
            ReflectionHelpers.getField<MutableList<ItemDto>>(
                cartManager,
                "itemsOnCart"
            )

        // then
        assertEquals(1, reflectionListItemsOnCart.size)
        assertEquals(testItem1, cartManager.itemsOnCart[0])

        // when added second item
        cartManager.addItemToCart(testItem2)

        reflectionListItemsOnCart = ReflectionHelpers.getField(cartManager, "itemsOnCart")

        // then
        assertEquals(2, reflectionListItemsOnCart.size)
        assertEquals(testItem2, cartManager.itemsOnCart[1])

        // when remove first item
        cartManager.removeItemFromCart(testItem1)

        // then
        assertEquals(1, reflectionListItemsOnCart.size)
        assertEquals(testItem2, cartManager.itemsOnCart[0])

        // when remove second item
        cartManager.removeItemFromCart(testItem2)

        // then
        assertEquals(0, reflectionListItemsOnCart.size)
        assertEquals(mutableListOf<ItemDto>(), cartManager.itemsOnCart)
    }

    @Test
    fun testCartManagerRemoveItemInEmptyCartList() {
        // given
        val testItem1 = ItemDto("Item 1", 10.0, "test_1", emptyList())

        // when added first item
        cartManager.removeItemFromCart(testItem1)

        val reflectionListItemsOnCart =
            ReflectionHelpers.getField<MutableList<ItemDto>>(
                cartManager,
                "itemsOnCart"
            )

        // then
        assertEquals(0, reflectionListItemsOnCart.size)
        assertEquals(mutableListOf<ItemDto>(), cartManager.itemsOnCart)
    }
}

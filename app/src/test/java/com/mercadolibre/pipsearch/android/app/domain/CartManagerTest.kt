package com.mercadolibre.pipsearch.android.app.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.mercadolibre.pipsearch.android.app.data.model.ItemDto
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.robolectric.util.ReflectionHelpers

class CartManagerTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun TestGetInstanceReturnTheSameInstance() {

        // given
        val cartManagerInstance1 = CartManager.getInstance()
        val cartManagerInstance2 = CartManager.getInstance()

        // then
        assertEquals(cartManagerInstance1, cartManagerInstance2)
    }

    @Test
    fun TestCartManagerSetListOfItems() {

        // given
        val mockItem1 = ItemDto("Item 1", 10.0, "test_1", emptyList())
        val mockMutableListOfItems: MutableList<ItemDto> = mutableListOf()

        val cartManager = CartManager.getInstance()

        var reflectionListItemsOnCart =
            ReflectionHelpers.getField<MutableLiveData<MutableList<ItemDto>>>(cartManager, "_itemsOnCart")

        assertNull(reflectionListItemsOnCart.value)

        // when
        mockMutableListOfItems.add(mockItem1)
        cartManager.updateItemList(mockMutableListOfItems)

        reflectionListItemsOnCart = ReflectionHelpers.getField(cartManager, "_itemsOnCart")

        // then
        assertEquals(1, reflectionListItemsOnCart.value!!.size)
    }
}

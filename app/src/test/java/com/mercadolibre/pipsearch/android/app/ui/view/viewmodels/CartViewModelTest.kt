package com.mercadolibre.pipsearch.android.app.ui.view.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.mercadolibre.pipsearch.android.app.data.model.ItemDto
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.robolectric.util.ReflectionHelpers


class CartViewModelTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private var cartViewModel = CartViewModel()

    @Test
    fun testAddItem() {
        // given
        val mockItem1 = ItemDto("Item 1", 10.0, "test1", emptyList())
        val mockItem2 = ItemDto("Item 2", 20.0, "test2", emptyList())
        val mockItemsList = mutableListOf<ItemDto>()
        mockItemsList.add(mockItem1)
        mockItemsList.add(mockItem2)

        var reflectionItemsOnTheList = ReflectionHelpers.getField<MutableLiveData<List<ItemDto>>>(cartViewModel, "itemsOnCart")

        assertNull(reflectionItemsOnTheList.value)

        // when
        cartViewModel.updateItemList(mockItemsList)

        reflectionItemsOnTheList = ReflectionHelpers.getField(cartViewModel, "itemsOnCart")

        assertNotNull(cartViewModel.itemsOnCart)
        assertEquals(mockItemsList, cartViewModel.itemsOnCart.value)
        assertEquals(2, reflectionItemsOnTheList.value?.size)
    }
}

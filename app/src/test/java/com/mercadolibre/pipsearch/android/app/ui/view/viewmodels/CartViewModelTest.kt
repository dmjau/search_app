package com.mercadolibre.pipsearch.android.app.ui.view.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.mercadolibre.pipsearch.android.app.data.model.ItemDto
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.robolectric.util.ReflectionHelpers


class CartViewModelTest {

    private lateinit var viewModel: CartViewModel

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        viewModel = CartViewModel()
    }

    @Test
    fun testInitializeSelectedItemsLiveData() {
        // given
        val reflectionSelectedItems = ReflectionHelpers.getField<MutableLiveData<List<ItemDto>>>(viewModel, "selectedItems")

        // then
        assertEquals(mutableListOf<ItemDto>(), reflectionSelectedItems.value)
        assertEquals(mutableListOf<ItemDto>(), viewModel.selectedItems.value)
    }

    @Test
    fun testRemoveItemFromCart() {
        // given
        val mockItem1 = ItemDto("Item 1", 10.0, "test1", emptyList())
        val mockItem2 = ItemDto("Item 2", 20.0, "test2", emptyList())
        val mockItemsList = mutableListOf<ItemDto>()
        mockItemsList.add(mockItem1)
        mockItemsList.add(mockItem2)

        var reflectionSelectedItems = ReflectionHelpers.getField<MutableLiveData<List<ItemDto>>>(viewModel, "selectedItems")

        // before delete items
        assertEquals(mutableListOf<ItemDto>(), reflectionSelectedItems.value)
        assertEquals(mutableListOf<ItemDto>(), viewModel.selectedItems.value)

        val mockMutableLiveDataSelectedItems: MutableLiveData<MutableList<ItemDto>> = MutableLiveData(mutableListOf())
        mockMutableLiveDataSelectedItems.postValue(mockItemsList)


        // when added items in the cart manager
        ReflectionHelpers.setField(viewModel, "selectedItems", mockMutableLiveDataSelectedItems)
        reflectionSelectedItems = ReflectionHelpers.getField(viewModel, "selectedItems")

        // then
        assertEquals(mockItem1, reflectionSelectedItems.value!![0])
        assertEquals(mockItem2, reflectionSelectedItems.value!![1])
        assertEquals(mockItem1, viewModel.selectedItems.value!![0])
        assertEquals(mockItem2, viewModel.selectedItems.value!![1])

        // when remove item1
        viewModel.removeItemFromCart(mockItem1)

        // when
        assertEquals(mockItem2, reflectionSelectedItems.value!![0])
        assertEquals(mockItem2, viewModel.selectedItems.value!![0])

        // when remove item2
        viewModel.removeItemFromCart(mockItem2)

        reflectionSelectedItems = ReflectionHelpers.getField(viewModel, "selectedItems")

        assertEquals(mutableListOf<ItemDto>(), reflectionSelectedItems.value)
        assertEquals(mutableListOf<ItemDto>(), viewModel.selectedItems.value)
    }

    @Test
    fun testRemoveItemFromCartWhenSelectedItemsListIsNull() {
        // given
        val mockItemToDelete = ItemDto("Item 1", 10.0, "test1", emptyList())

        var reflectionSelectedItems = ReflectionHelpers.getField<MutableLiveData<List<ItemDto>>>(viewModel, "selectedItems")

        // before delete items
        assertEquals(mutableListOf<ItemDto>(), reflectionSelectedItems.value)
        assertEquals(mutableListOf<ItemDto>(), viewModel.selectedItems.value)

        val mockMutableLiveDataNullSelectedItems: MutableLiveData<MutableList<ItemDto>> = MutableLiveData(null)

        // set null mutable list
        ReflectionHelpers.setField(viewModel, "selectedItems", mockMutableLiveDataNullSelectedItems)

        reflectionSelectedItems = ReflectionHelpers.getField(viewModel, "selectedItems")

        // then
        assertNull(reflectionSelectedItems.value)
        assertNull(viewModel.selectedItems.value)

        // when remove item1
        viewModel.removeItemFromCart(mockItemToDelete)

        reflectionSelectedItems = ReflectionHelpers.getField(viewModel, "selectedItems")

        // then
        assertNull(reflectionSelectedItems.value)
        assertNull(viewModel.selectedItems.value)
    }
}

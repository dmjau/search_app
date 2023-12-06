package com.mercadolibre.pipsearch.android.app.ui.view.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.mercadolibre.pipsearch.android.app.data.model.ItemDto
import com.mercadolibre.pipsearch.android.app.domain.CartManager
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.robolectric.util.ReflectionHelpers


class CartViewModelTest {

    private lateinit var viewModel: CartViewModel
    private lateinit var cartManager: CartManager

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        viewModel = CartViewModel()

        cartManager = CartManager
    }

    @After
    fun tearDown() {
        cartManager.resetState()
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
    fun testPublicUpdateItemsOnCart() {
        // given
        val testItem1 = ItemDto("Item 1", 10.0, "test1", emptyList())
        val testItem2 = ItemDto("Item 2", 20.0, "test2", emptyList())
        val mockItemsList = mutableListOf<ItemDto>()
        mockItemsList.add(testItem1)
        mockItemsList.add(testItem2)

        val reflectionSelectedItems = ReflectionHelpers.getField<MutableLiveData<List<ItemDto>>>(viewModel, "selectedItems")

        // before delete items
        assertEquals(mutableListOf<ItemDto>(), reflectionSelectedItems.value)
        assertEquals(mutableListOf<ItemDto>(), viewModel.selectedItems.value)

        // when
        viewModel.updateItemsOnCart()

        // then
        assertEquals(mutableListOf<ItemDto>(), reflectionSelectedItems.value)
        assertEquals(mutableListOf<ItemDto>(), viewModel.selectedItems.value)

        // when
        cartManager.addItemToCart(testItem1)
        viewModel.updateItemsOnCart()

        // then
        assertEquals(testItem1, viewModel.selectedItems.value!![0])
    }

    @Test
    fun testRemoveItemFromCartWhenSelectedItemsListIsNull() {
        // given
        val testItemToDelete = ItemDto("Item 1", 10.0, "test1", emptyList())

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
        viewModel.removeItemFromCart(testItemToDelete)

        reflectionSelectedItems = ReflectionHelpers.getField(viewModel, "selectedItems")
        // then
        assertEquals(mutableListOf<ItemDto>(), reflectionSelectedItems.value)
        assertEquals(mutableListOf<ItemDto>(), viewModel.selectedItems.value)
    }

    @Test
    fun testPublicVariableInitialSelectedItems() {
        // given
        val testItem1 = ItemDto("Item 1", 10.0, "test1", emptyList())
        val testItem2 = ItemDto("Item 2", 20.0, "test2", emptyList())
        val mockItemsList = mutableListOf<ItemDto>()
        mockItemsList.add(testItem1)
        mockItemsList.add(testItem2)

        val reflectionSelectedItems = ReflectionHelpers.getField<MutableLiveData<List<ItemDto>>>(viewModel, "selectedItems")

        // before delete items
        assertEquals(mutableListOf<ItemDto>(), reflectionSelectedItems.value)
        assertEquals(mutableListOf<ItemDto>(), viewModel.selectedItems.value)

        // when
        cartManager.addItemToCart(testItem1)
        viewModel.updateItemsOnCart()

        // then
        assertEquals(testItem1, viewModel.selectedItems.value!![0])

        // when
        cartManager.addItemToCart(testItem2)
        viewModel.updateItemsOnCart()

        // then
        assertEquals(testItem2, viewModel.selectedItems.value!![1])
    }

    @Test
    fun testPublicVariableSelectedItemsWhenRemoveItems() {
        // given
        val testItem1 = ItemDto("Item 1", 10.0, "test1", emptyList())
        val testItem2 = ItemDto("Item 2", 20.0, "test2", emptyList())
        val mockItemsList = mutableListOf<ItemDto>()
        mockItemsList.add(testItem1)
        mockItemsList.add(testItem2)

        val reflectionSelectedItems = ReflectionHelpers.getField<MutableLiveData<List<ItemDto>>>(viewModel, "selectedItems")

        // before delete items
        assertEquals(mutableListOf<ItemDto>(), reflectionSelectedItems.value)
        assertEquals(mutableListOf<ItemDto>(), viewModel.selectedItems.value)

        // set initial item from in cart manager
        cartManager.addItemToCart(testItem1)
        viewModel.updateItemsOnCart()

        // then
        assertEquals(testItem1, viewModel.selectedItems.value!![0])

        // when
        viewModel.removeItemFromCart(testItem1)

        // then
        assertEquals(mutableListOf<ItemDto>(), viewModel.selectedItems.value)
    }
}

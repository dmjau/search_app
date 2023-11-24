package com.mercadolibre.pipsearch.android.app.ui.view.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.mercadolibre.pipsearch.android.app.data.model.ItemDto
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import io.mockk.verify
import org.junit.After
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

    @MockK(relaxed = true)
    private lateinit var observer: Observer<MutableList<ItemDto>>

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        viewModel = CartViewModel()

        viewModel.selectedItems.observeForever(observer)
    }

    @After
    fun tearDown() {
        unmockkAll()
        viewModel.selectedItems.removeObserver(observer)
    }

    @Test
    fun testOnChangePublicListSelectedItemsLiveData() {
        // given
        val mockItem1 = ItemDto("Item 1", 10.0, "test1", emptyList())
        val mockItem2 = ItemDto("Item 2", 20.0, "test2", emptyList())
        val mockItemsList = mutableListOf<ItemDto>()
        mockItemsList.add(mockItem1)
        mockItemsList.add(mockItem2)

        val mockMutableLiveDataSelectedItems: MutableLiveData<MutableList<ItemDto>> = MutableLiveData(mutableListOf())
        mockMutableLiveDataSelectedItems.postValue(mockItemsList)

        val reflectionSelectedItems = ReflectionHelpers.getField<MutableLiveData<MutableList<ItemDto>>>(viewModel, "_selectedItems")

        // before delete items
        assertEquals(mutableListOf<ItemDto>(), reflectionSelectedItems.value)
        assertEquals(mutableListOf<ItemDto>(), viewModel.selectedItems.value)

        // when
        ReflectionHelpers.setField(viewModel, "selectedItems", mockMutableLiveDataSelectedItems)

        // then
        verify(exactly = 1) {
            observer.onChanged(any())
        }
    }

    @Test
    fun testRemoveItemFromCart() {
        // given
        val mockItem1 = ItemDto("Item 1", 10.0, "test1", emptyList())
        val mockItem2 = ItemDto("Item 2", 20.0, "test2", emptyList())
        val mockItemsList = mutableListOf<ItemDto>()
        mockItemsList.add(mockItem1)
        mockItemsList.add(mockItem2)

        var reflectionSelectedItems = ReflectionHelpers.getField<MutableLiveData<List<ItemDto>>>(viewModel, "_selectedItems")

        // before delete items
        assertEquals(mutableListOf<ItemDto>(), reflectionSelectedItems.value)
        assertEquals(mutableListOf<ItemDto>(), viewModel.selectedItems.value)

        val mockMutableLiveDataSelectedItems: MutableLiveData<MutableList<ItemDto>> = MutableLiveData(mutableListOf())
        mockMutableLiveDataSelectedItems.postValue(mockItemsList)


        // when added items in the cart manager
        ReflectionHelpers.setField(viewModel, "_selectedItems", mockMutableLiveDataSelectedItems)
        reflectionSelectedItems = ReflectionHelpers.getField(viewModel, "_selectedItems")

        // then
        assertEquals(mockItem1, reflectionSelectedItems.value!![0])
        assertEquals(mockItem2, reflectionSelectedItems.value!![1])

        // when remove item1
        viewModel.removeItemFromCart(mockItem1)

        // when
        assertEquals(mockItem2, reflectionSelectedItems.value!![0])

        // when remove item2
        viewModel.removeItemFromCart(mockItem2)

        reflectionSelectedItems = ReflectionHelpers.getField(viewModel, "_selectedItems")

        assertEquals(mutableListOf<ItemDto>(), reflectionSelectedItems.value)
    }

    @Test
    fun testRemoveItemFromCartWhenSelectedItemsListIsNull() {
        // given
        val mockItemToDelete = ItemDto("Item 1", 10.0, "test1", emptyList())

        var reflectionSelectedItems = ReflectionHelpers.getField<MutableLiveData<List<ItemDto>>>(viewModel, "_selectedItems")

        // before delete items
        assertEquals(mutableListOf<ItemDto>(), reflectionSelectedItems.value)
        assertEquals(mutableListOf<ItemDto>(), viewModel.selectedItems.value)

        val mockMutableLiveDataNullSelectedItems: MutableLiveData<MutableList<ItemDto>> = MutableLiveData(null)

        // set null mutable list
        ReflectionHelpers.setField(viewModel, "_selectedItems", mockMutableLiveDataNullSelectedItems)

        reflectionSelectedItems = ReflectionHelpers.getField(viewModel, "_selectedItems")

        // then
        assertNull(reflectionSelectedItems.value)

        // when remove item1
        viewModel.removeItemFromCart(mockItemToDelete)

        reflectionSelectedItems = ReflectionHelpers.getField(viewModel, "_selectedItems")

        // then
        assertNull(reflectionSelectedItems.value)
    }
}

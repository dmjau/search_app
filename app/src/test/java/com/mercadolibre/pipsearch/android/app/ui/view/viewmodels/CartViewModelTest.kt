package com.mercadolibre.pipsearch.android.app.ui.view.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.mercadolibre.pipsearch.android.app.data.model.ItemDto
import com.mercadolibre.pipsearch.android.app.domain.CartManager
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
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
    private lateinit var cartManager: CartManager

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

        cartManager = CartManager
    }

    @After
    fun tearDown() {
        unmockkAll()
        viewModel.selectedItems.removeObserver(observer)
        cartManager.resetState()
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

        val reflectionSelectedItems = ReflectionHelpers.getField<MutableLiveData<List<ItemDto>>>(viewModel, "selectedItems")

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

    @Test
    fun testPublicVariableInitialSelectedItems() {
        // given
        val mockItem1 = ItemDto("Item 1", 10.0, "test1", emptyList())
        val mockItem2 = ItemDto("Item 2", 20.0, "test2", emptyList())
        val mockItemsList = mutableListOf<ItemDto>()
        mockItemsList.add(mockItem1)
        mockItemsList.add(mockItem2)

        val reflectionSelectedItems = ReflectionHelpers.getField<MutableLiveData<List<ItemDto>>>(viewModel, "selectedItems")

        // before delete items
        assertEquals(mutableListOf<ItemDto>(), reflectionSelectedItems.value)
        assertEquals(mutableListOf<ItemDto>(), viewModel.selectedItems.value)

        // when
        cartManager.addItemToCart(mockItem1)

        // then
        assertEquals(mockItem1, viewModel.selectedItems.value!![0])

        // when
        cartManager.addItemToCart(mockItem2)

        // then
        assertEquals(mockItem2, viewModel.selectedItems.value!![1])
    }

    @Test
    fun testPublicVariableSelectedItemsWhenRemoveItems() {
        // given
        val mockItem1 = ItemDto("Item 1", 10.0, "test1", emptyList())
        val mockItem2 = ItemDto("Item 2", 20.0, "test2", emptyList())
        val mockItemsList = mutableListOf<ItemDto>()
        mockItemsList.add(mockItem1)
        mockItemsList.add(mockItem2)

        val reflectionSelectedItems = ReflectionHelpers.getField<MutableLiveData<List<ItemDto>>>(viewModel, "selectedItems")

        // before delete items
        assertEquals(mutableListOf<ItemDto>(), reflectionSelectedItems.value)
        assertEquals(mutableListOf<ItemDto>(), viewModel.selectedItems.value)

        // set initial item from in cart manager
        cartManager.addItemToCart(mockItem1)

        // then
        assertEquals(mockItem1, viewModel.selectedItems.value!![0])

        // when
        viewModel.removeItemFromCart(mockItem1)

        // then
        assertEquals(mutableListOf<ItemDto>(), viewModel.selectedItems.value)
    }

    @Test
    fun testCartViewModelCallOnCleared() {
        // given
        val cartManagerMock = mockk<CartManager>(relaxed = true)
        val viewModelWithMock = CartViewModel(cartManagerMock)
        val observer = mockk<Observer<MutableList<ItemDto>>>(relaxUnitFun = true)

        viewModelWithMock.selectedItems.observeForever(observer)

        verify {
            observer.onChanged(any())
        }

        // when
        viewModelWithMock.onCleared()

        // then
        verify {
            cartManagerMock.itemsOnCart.removeObserver(any())
        }
    }
}

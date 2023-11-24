package com.mercadolibre.pipsearch.android.app.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.mercadolibre.pipsearch.android.app.data.model.ItemDto
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.After
import org.junit.Assert.assertEquals
import io.mockk.unmockkAll
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.robolectric.util.ReflectionHelpers

class CartManagerTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @MockK(relaxed = true)
    private lateinit var mockObserver: Observer<MutableList<ItemDto>>

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun testGetInstanceReturnTheSameInstance() {

        // given
        val cartManagerInstance1 = CartManager.getInstance()
        val cartManagerInstance2 = CartManager.getInstance()

        // then
        assertEquals(cartManagerInstance1, cartManagerInstance2)
    }

    @Test
    fun testCartManagerAddItemToCart() {
        // given
        val mockItem1 = ItemDto("Item 1", 10.0, "test_1", emptyList())
        val mockItem2 = ItemDto("Item 2", 20.0, "test_2", emptyList())

        val cartManager = CartManager.getInstance()

        var reflectionListItemsOnCart =
            ReflectionHelpers.getField<MutableLiveData<MutableList<ItemDto>>>(cartManager, "_itemsOnCart")

        assertEquals(mutableListOf<ItemDto>(),reflectionListItemsOnCart.value)
        assertEquals(mutableListOf<ItemDto>(), cartManager.itemsOnCart.value)

        // when added first item
        cartManager.addItemToCart(mockItem1)

        reflectionListItemsOnCart = ReflectionHelpers.getField(cartManager, "_itemsOnCart")

        // then
        assertEquals(1, reflectionListItemsOnCart.value!!.size)
        assertEquals(mockItem1, cartManager.itemsOnCart.value!![0])

        // when added second item
        cartManager.addItemToCart(mockItem2)

        reflectionListItemsOnCart = ReflectionHelpers.getField(cartManager, "_itemsOnCart")

        // then
        assertEquals(2, reflectionListItemsOnCart.value!!.size)
        assertEquals(mockItem2, cartManager.itemsOnCart.value!![1])
    }

    @Test
    fun testCartManagerAddItemToCartAndRemoveItemFromCart() {
        // given
        val mockItem1 = ItemDto("Item 1", 10.0, "test_1", emptyList())
        val mockItem2 = ItemDto("Item 2", 20.0, "test_2", emptyList())

        val cartManager = CartManager.getInstance()

        var reflectionListItemsOnCart =
            ReflectionHelpers.getField<MutableLiveData<MutableList<ItemDto>>>(cartManager, "_itemsOnCart")

        assertEquals(mutableListOf<ItemDto>(),reflectionListItemsOnCart.value)
        assertEquals(mutableListOf<ItemDto>(), cartManager.itemsOnCart.value)

        // when added first item
        cartManager.addItemToCart(mockItem1)

        reflectionListItemsOnCart = ReflectionHelpers.getField(cartManager, "_itemsOnCart")

        // then
        assertEquals(1, reflectionListItemsOnCart.value!!.size)
        assertEquals(mockItem1, cartManager.itemsOnCart.value!![0])

        // when added second item
        cartManager.addItemToCart(mockItem2)

        reflectionListItemsOnCart = ReflectionHelpers.getField(cartManager, "_itemsOnCart")

        // then
        assertEquals(2, reflectionListItemsOnCart.value!!.size)
        assertEquals(mockItem2, cartManager.itemsOnCart.value!![1])

        // when remove first item
        cartManager.removeItemFromCart(mockItem1)

        // then
        assertEquals(1, reflectionListItemsOnCart.value!!.size)
        assertEquals(mockItem2, cartManager.itemsOnCart.value!![0])

        // when remove second item
        cartManager.removeItemFromCart(mockItem2)

        // then
        assertEquals(0, reflectionListItemsOnCart.value!!.size)
        assertEquals(mutableListOf<ItemDto>(), cartManager.itemsOnCart.value)
    }

    @Test
    fun testCartManagerUpdateLiveDataObservers() {
        // given
        val mockItem1 = ItemDto("Item 1", 10.0, "test_1", emptyList())

        val mockMutableListOfItems: MutableList<ItemDto> = mutableListOf()
        mockMutableListOfItems.add(mockItem1)

        val cartManager = CartManager.getInstance()
        val resetMutableLiveData: MutableLiveData<MutableList<ItemDto>> = MutableLiveData()
        ReflectionHelpers.setField(cartManager, "_itemsOnCart", resetMutableLiveData)

        cartManager.itemsOnCart.observeForever(mockObserver)

        var reflectionListItemsOnCart =
            ReflectionHelpers.getField<MutableLiveData<MutableList<ItemDto>>>(cartManager, "_itemsOnCart")

        assertEquals(mutableListOf<ItemDto>(),reflectionListItemsOnCart.value)
        assertEquals(mutableListOf<ItemDto>(),cartManager.itemsOnCart.value)

        // then
        verify(exactly = 1) {
            mockObserver.onChanged(any())
        }

        // when
        cartManager.addItemToCart(mockItem1)

        reflectionListItemsOnCart = ReflectionHelpers.getField(cartManager, "_itemsOnCart")

        // then
        assertEquals(1, reflectionListItemsOnCart.value!!.size)
        assertTrue(cartManager.itemsOnCart.value == mockMutableListOfItems)

        verify(exactly = 2) {
            mockObserver.onChanged(mockMutableListOfItems)
        }

        // when
        cartManager.removeItemFromCart(mockItem1)

        reflectionListItemsOnCart = ReflectionHelpers.getField(cartManager, "_itemsOnCart")

        // then
        assertEquals(0, reflectionListItemsOnCart.value!!.size)
        assertTrue(cartManager.itemsOnCart.value == mutableListOf<ItemDto>())

        verify(exactly = 3) {
            mockObserver.onChanged(any())
        }

        // remove observer
        cartManager.itemsOnCart.removeObserver(mockObserver)
    }

    @Test
    fun testCartManagerRemoveItemInEmptyCartList() {
        // given
        val mockItem1 = ItemDto("Item 1", 10.0, "test_1", emptyList())

        val cartManager = CartManager.getInstance()

        var reflectionListItemsOnCart =
            ReflectionHelpers.getField<MutableLiveData<MutableList<ItemDto>>>(cartManager, "_itemsOnCart")

        assertEquals(mutableListOf<ItemDto>(),reflectionListItemsOnCart.value)
        assertEquals(mutableListOf<ItemDto>(), cartManager.itemsOnCart.value)

        // when added first item
        cartManager.removeItemFromCart(mockItem1)

        reflectionListItemsOnCart = ReflectionHelpers.getField(cartManager, "_itemsOnCart")

        // then
        assertEquals(0, reflectionListItemsOnCart.value!!.size)
        assertEquals(mutableListOf<ItemDto>(), cartManager.itemsOnCart.value)
    }
}

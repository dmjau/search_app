package com.mercadolibre.pipsearch.android.app.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.mercadolibre.pipsearch.android.app.data.model.ItemDto
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
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
        val resetMutableLiveData: MutableLiveData<MutableList<ItemDto>> = MutableLiveData()
        ReflectionHelpers.setField(cartManager, "_itemsOnCart", resetMutableLiveData)

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

    @Test
    fun TestCartManagerCallUpdateItemList() {

        // given
        val mockItem1 = ItemDto("Item 1", 10.0, "test_1", emptyList())
        val mockMutableListOfItems: MutableList<ItemDto> = mutableListOf()

        val cartManager = CartManager.getInstance()

        cartManager.itemsOnCart.observeForever(mockObserver)

        var reflectionListItemsOnCart =
            ReflectionHelpers.getField<MutableLiveData<MutableList<ItemDto>>>(cartManager, "_itemsOnCart")

        assertNull(reflectionListItemsOnCart.value)

        // when
        mockMutableListOfItems.add(mockItem1)
        cartManager.updateItemList(mockMutableListOfItems)

        reflectionListItemsOnCart = ReflectionHelpers.getField(cartManager, "_itemsOnCart")

        // then
        assertEquals(1, reflectionListItemsOnCart.value!!.size)
        assertTrue(cartManager.itemsOnCart.value == mockMutableListOfItems)

        verify(exactly = 1) {
            mockObserver.onChanged(mockMutableListOfItems)
        }

        // remove observer
        cartManager.itemsOnCart.removeObserver(mockObserver)
    }
}

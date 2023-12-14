package com.mercadolibre.pipsearch.android.app.ui.view

import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.test.core.app.launchActivity
import com.mercadolibre.pipsearch.android.app.data.model.ItemDto
import com.mercadolibre.pipsearch.android.app.domain.CartManager
import com.mercadolibre.pipsearch.android.app.ui.view.adapters.CartAdapter
import com.mercadolibre.pipsearch.android.app.ui.view.viewmodels.CartViewModel
import com.mercadolibre.pipsearch.android.databinding.PipSearchAppCartActivityBinding
import io.mockk.mockk
import io.mockk.verify
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Rule
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.util.ReflectionHelpers

@RunWith(RobolectricTestRunner::class)
class CartActivityTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: CartViewModel
    private lateinit var cartManager: CartManager

    @Before
    fun setup() {
        cartManager = CartManager
    }

    @After
    fun tearDown() {
        cartManager.resetState()
    }

    @Test
    fun testCartActivityInstance() {
        // given
        val cartActivity = launchActivity<CartActivity>()

        // then
        assertNotNull(cartActivity)
    }

    @Test
    fun testCartActivityInstanceBinding() {
        launchActivity<CartActivity>().onActivity { activity ->
            val reflectionBinding = ReflectionHelpers.getField<PipSearchAppCartActivityBinding>(activity, "binding")

            // then
            assertNotNull(reflectionBinding)
            assertNotNull(reflectionBinding.root)
        }
    }

    @Test
    fun testCartActivitySetBaseScreenTexts() {
        launchActivity<CartActivity>().onActivity { activity ->
            val reflectionBinding = ReflectionHelpers.getField<PipSearchAppCartActivityBinding>(activity, "binding")

            // then
            assertEquals("Carrito", reflectionBinding.pipCartHeaderText.text.toString())
            assertEquals("El carrito está vacío", reflectionBinding.pipCartBodyTitle.text.toString())
            assertEquals("Volvé a la pantalla de principal para buscar ítems.", reflectionBinding.pipCartBodySubtitle.text.toString())
        }
    }

    @Test
    fun testCartActivityShowBaseScreenAndHideReclerView() {
        launchActivity<CartActivity>().onActivity { activity ->
            val reflectionBinding = ReflectionHelpers.getField<PipSearchAppCartActivityBinding>(activity, "binding")

            // then
            assertEquals(GONE, reflectionBinding.pipCartBodyRecyclerContainer.visibility)
            assertEquals(VISIBLE, reflectionBinding.pipCartBodyImageContainer.visibility)
        }
    }

    @Test
    fun testCartActivityCallFinishWhenClickButtonBack() {
        launchActivity<CartActivity>().onActivity { activity ->
            val reflectionBinding = ReflectionHelpers.getField<PipSearchAppCartActivityBinding>(activity, "binding")

            // when
            reflectionBinding.pipCartHeaderBack.performClick()

            // then
            assertTrue(activity.isFinishing)
        }
    }

    @Test
    fun testCartActivityCreateViewWithCartAdapterAndRecyclerView() {
        // given
        launchActivity<CartActivity>().onActivity { activity ->
            val reflectionBinding =
                ReflectionHelpers.getField<PipSearchAppCartActivityBinding>(activity, "binding")

            val reflectionAdapter = ReflectionHelpers.getField<CartAdapter>(activity, "cartAdapter")

            // then
            assertNotNull(reflectionAdapter)
            assertNotNull(reflectionBinding.pipCartBodyRecyclerContainer.layoutManager)
        }
    }

    @Test
    fun testCartActivityInstanceCartViewModel() {
        // given
        launchActivity<CartActivity>().onActivity { activity ->
            viewModel = ViewModelProvider(activity).get(CartViewModel::class.java)

            // then
            assertNotNull(viewModel)
        }
    }

    @Test
    fun testSetItemsOnCartAdapter() {
        // given
        launchActivity<CartActivity>().onActivity { activity ->

            val mockCartAdapter = mockk<CartAdapter>(relaxed = true)
            ReflectionHelpers.setField(activity, "cartAdapter", mockCartAdapter)

            // add items on the list
            val testItemsOnCart: MutableList<ItemDto> = mutableListOf()
            val listOfTags = listOf("tag_1_test", "tag_1_test", "tag_1_test")
            val itemTest1 = ItemDto("itemTest 1", 1111.0, "https://test_image_item_test_1.jpg", listOfTags)
            val itemTest2 = ItemDto("itemTest 2", 2222.0, "https://test_image_item_test_2.jpg", listOfTags)

            testItemsOnCart.add(itemTest1)
            testItemsOnCart.add(itemTest2)

            val showListOfItemsMethod = activity.javaClass.getDeclaredMethod("showListOfItems", List::class.java)
            showListOfItemsMethod.isAccessible = true

            // when
            showListOfItemsMethod.invoke(activity, testItemsOnCart)

            // then
            verify {
                mockCartAdapter.setItems(any())
            }
        }
    }

    @Test
    fun testSetItemsOnCartAdapterWhenChangeItemsOnCart() {
        // given
        launchActivity<CartActivity>().onActivity { activity ->

            viewModel = ViewModelProvider(activity).get(CartViewModel::class.java)

            val mockCartAdapter = mockk<CartAdapter>(relaxed = true)
            ReflectionHelpers.setField(activity, "cartAdapter", mockCartAdapter)

            val reflectionSelectedItems = ReflectionHelpers.getField<MutableLiveData<MutableList<ItemDto>>>(viewModel, "selectedItems")

            // initial list
            assertEquals(0, reflectionSelectedItems.value!!.size)

            verify(inverse = true) {
                mockCartAdapter.setItems(any())
            }

            // add items on the cart list
            val listOfTagsTest = listOf("tag_1_test", "tag_1_test", "tag_1_test")
            val itemTest1 = ItemDto("itemTest 1", 1111.0, "https://test_image_item_test_1.jpg", listOfTagsTest)
            cartManager.addItemToCart(itemTest1)

            // when
            viewModel.updateItemsOnCart()

            // then
            verify {
                mockCartAdapter.setItems(any())
            }
        }
    }

    @Test
    fun testShowRecyclerAndHideBaseScreenWhenListOfItemsIsNotEmpty() {
        // given
        launchActivity<CartActivity>().onActivity { activity ->

            viewModel = ViewModelProvider(activity).get(CartViewModel::class.java)
            val reflectionBinding = ReflectionHelpers.getField<PipSearchAppCartActivityBinding>(activity, "binding")
            val reflectionSelectedItems = ReflectionHelpers.getField<MutableLiveData<MutableList<ItemDto>>>(viewModel, "selectedItems")

            // initial list
            assertEquals(0, reflectionSelectedItems.value!!.size)

            // then
            assertEquals(GONE, reflectionBinding.pipCartBodyRecyclerContainer.visibility)
            assertEquals(VISIBLE, reflectionBinding.pipCartBodyImageContainer.visibility)

            // add items on the cart list
            val listOfTagsTest = listOf("tag_1_test", "tag_1_test", "tag_1_test")
            val itemTest1 = ItemDto("itemTest 1", 1111.0, "https://test_image_item_test_1.jpg", listOfTagsTest)
            cartManager.addItemToCart(itemTest1)

            // when
            viewModel.updateItemsOnCart()

            // then
            assertEquals(VISIBLE, reflectionBinding.pipCartBodyRecyclerContainer.visibility)
            assertEquals(GONE, reflectionBinding.pipCartBodyImageContainer.visibility)
        }
    }

    @Test
    fun testShowBaseScreenWhenListOfItemsEmpty() {
        // given
        launchActivity<CartActivity>().onActivity { activity ->

            viewModel = ViewModelProvider(activity).get(CartViewModel::class.java)
            val reflectionBinding = ReflectionHelpers.getField<PipSearchAppCartActivityBinding>(activity, "binding")
            val reflectionSelectedItems = ReflectionHelpers.getField<MutableLiveData<MutableList<ItemDto>>>(viewModel, "selectedItems")

            // initial list
            assertEquals(0, reflectionSelectedItems.value!!.size)

            // then
            assertEquals(GONE, reflectionBinding.pipCartBodyRecyclerContainer.visibility)
            assertEquals(VISIBLE, reflectionBinding.pipCartBodyImageContainer.visibility)
        }
    }

    @Test
    fun testShowBaseScreenIfListOfItemsOnCartIsNull() {
        // given
        launchActivity<CartActivity>().onActivity { activity ->

            viewModel = ViewModelProvider(activity).get(CartViewModel::class.java)
            val reflectionBinding = ReflectionHelpers.getField<PipSearchAppCartActivityBinding>(activity, "binding")
            var reflectionSelectedItems = ReflectionHelpers.getField<MutableLiveData<MutableList<ItemDto>>>(viewModel, "selectedItems")

            // initial list
            assertEquals(0, reflectionSelectedItems.value!!.size)

            // when
            ReflectionHelpers.setField(cartManager, "itemsOnCart", null)
            viewModel.updateItemsOnCart()

            reflectionSelectedItems = ReflectionHelpers.getField(viewModel, "selectedItems")

            // then
            assertNull(reflectionSelectedItems.value)
            assertEquals(GONE, reflectionBinding.pipCartBodyRecyclerContainer.visibility)
            assertEquals(VISIBLE, reflectionBinding.pipCartBodyImageContainer.visibility)
        }
    }
}

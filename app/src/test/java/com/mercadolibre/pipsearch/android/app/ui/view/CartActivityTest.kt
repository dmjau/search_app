package com.mercadolibre.pipsearch.android.app.ui.view

import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.ViewModelProvider
import androidx.test.core.app.launchActivity
import com.mercadolibre.pipsearch.android.app.data.model.ItemDto
import com.mercadolibre.pipsearch.android.app.ui.view.adapters.CartAdapter
import com.mercadolibre.pipsearch.android.app.ui.view.viewmodels.CartViewModel
import com.mercadolibre.pipsearch.android.databinding.PipSearchAppCartActivityBinding
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Assert.assertTrue
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

            val reflectionItemsOnTheList = ReflectionHelpers.getField<MutableList<ItemDto>>(activity, "itemsOnCart")

            // initial list
            assertEquals(0, reflectionItemsOnTheList.size)

            // add items on the list
            val itemsOnCart: MutableList<ItemDto> = mutableListOf()
            val listOfTags = listOf("tag_1_test", "tag_1_test", "tag_1_test")
            val itemTest1 = ItemDto("itemTest 1", 1111.0, "https://test_image_item_test_1.jpg", listOfTags)
            val itemTest2 = ItemDto("itemTest 2", 2222.0, "https://test_image_item_test_2.jpg", listOfTags)

            itemsOnCart.add(itemTest1)
            itemsOnCart.add(itemTest2)

            ReflectionHelpers.setField(activity, "itemsOnCart", itemsOnCart)

            val showListOfItemsMethod = activity.javaClass.getDeclaredMethod("showListOfItems")
            showListOfItemsMethod.isAccessible = true

            // when
            showListOfItemsMethod.invoke(activity)

            // then
            verify {
                mockCartAdapter.setItems(any())
            }
        }
    }
}

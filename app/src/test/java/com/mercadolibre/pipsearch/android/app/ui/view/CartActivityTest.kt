package com.mercadolibre.pipsearch.android.app.ui.view

import android.content.Intent
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.test.core.app.launchActivity
import com.mercadolibre.pipsearch.android.app.ui.view.adapters.CartAdapter
import com.mercadolibre.pipsearch.android.databinding.PipSearchAppCartActivityBinding
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.util.ReflectionHelpers

@RunWith(RobolectricTestRunner::class)
class CartActivityTest {

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
    fun testCartActivityInitMainActivity() {
        launchActivity<CartActivity>().onActivity { activity ->
            val reflectionBinding = ReflectionHelpers.getField<PipSearchAppCartActivityBinding>(activity, "binding")

            var reflectionIntent = ReflectionHelpers.getField<Intent>(activity, "intent")

            // then
            assertNull(reflectionIntent)

            // when
            reflectionBinding.pipCartHeaderBack.performClick()

            reflectionIntent = ReflectionHelpers.getField(activity, "intent")

            // then
            assertNotNull(reflectionIntent)
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
}
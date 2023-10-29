package com.mercadolibre.pipsearch.android.app.ui.view

import androidx.test.core.app.launchActivity
import com.mercadolibre.pipsearch.android.databinding.PipSearchAppCartActivityBinding
import org.junit.Assert.assertNotNull
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
}
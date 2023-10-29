package com.mercadolibre.pipsearch.android.app.ui.view

import androidx.test.core.app.launchActivity
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class CartActivityTest {

    @Test
    fun testCartActivityInstance() {
        // given
        val cartActivity = launchActivity<CartActivity>()

        // then
        assertNotNull(cartActivity)
    }
}
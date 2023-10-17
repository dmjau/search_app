package com.mercadolibre.pipsearch.android.app

import com.mercadolibre.pipsearch.android.app.ui.view.MainActivity
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class MainActivityTest {

    @Test
    fun `test MainActivity instance`() {
        // given
        val activity = Robolectric.buildActivity(MainActivity::class.java).create().get()

        // then
        assertNotNull(activity)
    }
}
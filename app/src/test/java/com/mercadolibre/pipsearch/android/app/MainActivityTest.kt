package com.mercadolibre.pipsearch.android.app

import androidx.lifecycle.ViewModelProvider
import com.mercadolibre.pipsearch.android.app.ui.view.MainActivity
import com.mercadolibre.pipsearch.android.app.ui.view.MainViewModel
import com.mercadolibre.pipsearch.android.databinding.PipSearchAppMainActivityBinding
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.util.ReflectionHelpers

@RunWith(RobolectricTestRunner::class)
class MainActivityTest {

    @Test
    fun testMainActivityInstance() {
        // given
        val activity = Robolectric.buildActivity(MainActivity::class.java).create().get()

        // then
        assertNotNull(activity)
    }

    @Test
    fun testMainActivityInstanceViewModel() {
        // given
        val activity = Robolectric.buildActivity(MainActivity::class.java).create().get()
        val reflectionActivityMainViewModel = ReflectionHelpers.getField<MainViewModel>(activity, "mainViewModel")

        // then
        assertNotNull(reflectionActivityMainViewModel)
    }

    @Test
    fun testMainActivityInstanceBinding() {
        // given
        val activity = Robolectric.buildActivity(MainActivity::class.java).create().get()
        val reflectionActivityBinding = ReflectionHelpers.getField<PipSearchAppMainActivityBinding>(activity, "binding")

        // then
        assertNotNull(reflectionActivityBinding)
        assertNotNull(reflectionActivityBinding.root)
    }
}

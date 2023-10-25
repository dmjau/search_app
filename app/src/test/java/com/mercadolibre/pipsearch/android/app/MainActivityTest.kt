package com.mercadolibre.pipsearch.android.app

import androidx.test.core.app.launchActivity
import com.mercadolibre.android.andesui.searchbox.AndesSearchbox
import com.mercadolibre.pipsearch.android.app.ui.view.MainActivity
import com.mercadolibre.pipsearch.android.app.ui.view.MainViewModel
import com.mercadolibre.pipsearch.android.databinding.PipSearchAppMainActivityBinding
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.util.ReflectionHelpers

@RunWith(RobolectricTestRunner::class)
class MainActivityTest {

    @Test
    fun testMainActivityInstance() {
        // given
        val mainActivity = launchActivity<MainActivity>()

        // then
        assertNotNull(mainActivity)
    }

    @Test
    fun testMainActivityInstanceViewModel() {
        // given
        launchActivity<MainActivity>().onActivity { activity ->
            val reflectionMainViewModel = ReflectionHelpers.getField<MainViewModel>(activity, "mainViewModel")

            // then
            assertNotNull(reflectionMainViewModel)
        }
    }

    @Test
    fun testMainActivityInstanceBinding() {
        // given
        launchActivity<MainActivity>().onActivity { activity ->
            val reflectionActivityBinding = ReflectionHelpers.getField<PipSearchAppMainActivityBinding>(activity, "binding")

            // then
            assertNotNull(reflectionActivityBinding)
            assertNotNull(reflectionActivityBinding.root)
        }
    }

    @Test
    fun testMainActivityBindingSetListener() {
        // given
        launchActivity<MainActivity>().onActivity { activity ->
            val reflectionActivityBinding = ReflectionHelpers.getField<PipSearchAppMainActivityBinding>(activity, "binding")

            // then
            assertNotNull(reflectionActivityBinding.pipMainHeaderSearchbox.onSearchListener)
            assertTrue(reflectionActivityBinding.pipMainHeaderSearchbox.onSearchListener is AndesSearchbox.OnSearchListener)
        }
    }

    @Test
    fun testMainActivityTextToSearch() {
        // given
        launchActivity<MainActivity>().onActivity { activity ->
            val mockViewModel = mockk<MainViewModel>(relaxed = true)

            ReflectionHelpers.setField(activity, "mainViewModel", mockViewModel)

            val text = "test text"
            val sendTextToSearchMethod = activity.javaClass.getDeclaredMethod("sendTextToSearch", String::class.java)
            sendTextToSearchMethod.isAccessible = true

            // when
            sendTextToSearchMethod.invoke(activity, text)

            // then
            verify {
                mockViewModel.fetchResults(any())
            }
        }
    }

    @Test
    fun testMainActivityTextToSearchWithLongText() {
        // given
        launchActivity<MainActivity>().onActivity { activity ->
            val mockViewModel = mockk<MainViewModel>(relaxed = true)

            ReflectionHelpers.setField(activity, "mainViewModel", mockViewModel)

            val text = "This is a long text with 100 caracters for test function send to text in the view model and probe how it is work in this use case"
            val sendTextToSearchMethod = activity.javaClass.getDeclaredMethod("sendTextToSearch", String::class.java)
            sendTextToSearchMethod.isAccessible = true

            // when
            sendTextToSearchMethod.invoke(activity, text)

            // then
            verify(exactly = 0) {
                mockViewModel.fetchResults(any())
            }
        }
    }
}

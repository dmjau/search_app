package com.mercadolibre.pipsearch.android.app

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.mercadolibre.android.andesui.searchbox.AndesSearchbox
import com.mercadolibre.android.restclient.model.RestClientResult
import com.mercadolibre.pipsearch.android.app.data.model.ItemDto
import com.mercadolibre.pipsearch.android.app.data.model.ScreenItemsDto
import com.mercadolibre.pipsearch.android.app.data.repository.SearchItemsRepository
import com.mercadolibre.pipsearch.android.app.ui.view.MainActivity
import com.mercadolibre.pipsearch.android.app.ui.view.MainViewModel
import com.mercadolibre.pipsearch.android.databinding.PipSearchAppMainActivityBinding
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.util.ReflectionHelpers

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class MainActivityTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()
    private val mockRepository = mockk<SearchItemsRepository>(relaxed = true)
    private lateinit var viewModel: MainViewModel

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
        val reflectionMainViewModel =
            ReflectionHelpers.getField<MainViewModel>(activity, "mainViewModel")

        // then
        assertNotNull(reflectionMainViewModel)
    }

    @Test
    fun testMainActivityInstanceBinding() {
        // given
        val activity = Robolectric.buildActivity(MainActivity::class.java).create().get()
        val reflectionActivityBinding =
            ReflectionHelpers.getField<PipSearchAppMainActivityBinding>(activity, "binding")

        // then
        assertNotNull(reflectionActivityBinding)
        assertNotNull(reflectionActivityBinding.root)
    }

    @Test
    fun testMainActivityBindingSetListener() {
        // given
        val activity = Robolectric.buildActivity(MainActivity::class.java).create().get()
        val reflectionActivityBinding =
            ReflectionHelpers.getField<PipSearchAppMainActivityBinding>(activity, "binding")

        // then
        assertNotNull(reflectionActivityBinding.pipMainHeaderSearchbox.onSearchListener)
        assertTrue(reflectionActivityBinding.pipMainHeaderSearchbox.onSearchListener is AndesSearchbox.OnSearchListener)
    }

    @Test
    fun testMainActivityNotSetListenerWithoutBinding() {
        // given
        val activity = Robolectric.buildActivity(MainActivity::class.java).create().get()
        var reflectionActivityBinding =
            ReflectionHelpers.getField<PipSearchAppMainActivityBinding>(activity, "binding")

        assertNotNull(reflectionActivityBinding.pipMainHeaderSearchbox.onSearchListener)
        assertTrue(reflectionActivityBinding.pipMainHeaderSearchbox.onSearchListener is AndesSearchbox.OnSearchListener)

        // when
        ReflectionHelpers.setField(activity, "binding", null)
        reflectionActivityBinding = ReflectionHelpers.getField(activity, "binding")

        // then
        assertNull(reflectionActivityBinding)
    }

    @Test
    fun testMainActivityTextToSearch() {
        // given
        val mockViewModel = mockk<MainViewModel>(relaxed = true)
        val activity = Robolectric.buildActivity(MainActivity::class.java).create().get()

        ReflectionHelpers.setField(activity, "mainViewModel", mockViewModel)

        val text = "test text"
        val sendTextToSearchMethod =
            activity.javaClass.getDeclaredMethod("sendTextToSearch", String::class.java)
        sendTextToSearchMethod.isAccessible = true

        // when
        sendTextToSearchMethod.invoke(activity, text)

        // then
        verify {
            mockViewModel.fetchResults(any())
        }
    }

    @Test
    fun testMainActivitySendTextToSearchCallFetchResults() {
        testDispatcher.runBlockingTest {
            // given

            // set view model with mock repository
            Dispatchers.setMain(testDispatcher)
            viewModel = MainViewModel()
            ReflectionHelpers.setField(viewModel, "repository", mockRepository)
            val mockItem = ItemDto("Item 1", 10.0, "test", emptyList())
            coEvery {
                mockRepository.getAll(any())
            } returns RestClientResult.Success(ScreenItemsDto(listOf(mockItem)))

            val reflectionBeforeFetchResults =
                ReflectionHelpers.getField<MutableLiveData<ScreenItemsDto>>(
                    viewModel,
                    "_searchResults"
                )

            // verification before fetchResults()
            assertNull(reflectionBeforeFetchResults.value)

            // init activity to test
            val activity = Robolectric.buildActivity(MainActivity::class.java).create().get()
            ReflectionHelpers.setField(activity, "mainViewModel", viewModel)

            val text = "text to search test"
            val sendTextToSearchMethod =
                activity.javaClass.getDeclaredMethod("sendTextToSearch", String::class.java)
            sendTextToSearchMethod.isAccessible = true

            // when
            sendTextToSearchMethod.invoke(activity, text)

            val reflectionAfterFetchResults =
                ReflectionHelpers.getField<MutableLiveData<ScreenItemsDto>>(
                    viewModel,
                    "_searchResults"
                )

            // then
            assertNotNull(reflectionAfterFetchResults.value)
        }
    }

    @Test
    fun testMainActivitySendTextToSearchDonCallFetchResults() {
        testDispatcher.runBlockingTest {
            // given

            // set view model with mock repository
            Dispatchers.setMain(testDispatcher)
            viewModel = MainViewModel()
            ReflectionHelpers.setField(viewModel, "repository", mockRepository)
            val mockItem = ItemDto("Item 1", 10.0, "test", emptyList())
            coEvery {
                mockRepository.getAll(any())
            } returns RestClientResult.Success(ScreenItemsDto(listOf(mockItem)))

            val reflectionBeforeFetchResults =
                ReflectionHelpers.getField<MutableLiveData<ScreenItemsDto>>(
                    viewModel,
                    "_searchResults"
                )

            // verification before fetchResults()
            assertNull(reflectionBeforeFetchResults.value)

            // init activity to test
            val activity = Robolectric.buildActivity(MainActivity::class.java).create().get()
            ReflectionHelpers.setField(activity, "mainViewModel", viewModel)

            val text =
                "This is a long text with 100 caracters for test function send to text in the view model and probe how it is work in this use case"
            val sendTextToSearchMethod =
                activity.javaClass.getDeclaredMethod("sendTextToSearch", String::class.java)
            sendTextToSearchMethod.isAccessible = true

            // when
            sendTextToSearchMethod.invoke(activity, text)

            val reflectionAfterFetchResults =
                ReflectionHelpers.getField<MutableLiveData<ScreenItemsDto>>(
                    viewModel,
                    "_searchResults"
                )

            // then
            assertNull(reflectionAfterFetchResults.value)
        }
    }

    @Test
    fun testMainActivityTextToSearchWithLongText() {
        // given
        val mockViewModel = mockk<MainViewModel>(relaxed = true)
        val activity = Robolectric.buildActivity(MainActivity::class.java).create().get()

        ReflectionHelpers.setField(activity, "mainViewModel", mockViewModel)

        val text =
            "This is a long text with 100 caracters for test function send to text in the view model and probe how it is work in this use case"
        val sendTextToSearchMethod =
            activity.javaClass.getDeclaredMethod("sendTextToSearch", String::class.java)
        sendTextToSearchMethod.isAccessible = true

        // when
        sendTextToSearchMethod.invoke(activity, text)

        // then
        verify(exactly = 0) {
            mockViewModel.fetchResults(any())
        }
    }

    @Test
    fun testMainActivitySetInitialScreenTitle() {
        // given
        val activity = Robolectric.buildActivity(MainActivity::class.java).create().get()
        val reflectionActivityBinding =
            ReflectionHelpers.getField<PipSearchAppMainActivityBinding>(activity, "binding")

        // then
        assertEquals(
            "Surfing Mercado Libre",
            reflectionActivityBinding.pipMainBodyTitle.text.toString()
        )
    }
}

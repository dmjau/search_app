package com.mercadolibre.pipsearch.android.app

import android.content.Intent
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.test.core.app.launchActivity
import com.mercadolibre.android.andesui.searchbox.AndesSearchbox
import com.mercadolibre.android.restclient.model.RestClientResult
import com.mercadolibre.pipsearch.android.app.data.model.ItemDto
import com.mercadolibre.pipsearch.android.app.data.model.ScreenItemsDto
import com.mercadolibre.pipsearch.android.app.data.repository.SearchItemsRepository
import com.mercadolibre.pipsearch.android.app.ui.view.MainActivity
import com.mercadolibre.pipsearch.android.app.ui.view.MainViewModel
import com.mercadolibre.pipsearch.android.app.ui.view.adapters.MainAdapter
import com.mercadolibre.pipsearch.android.databinding.PipSearchAppMainActivityBinding
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.util.ReflectionHelpers

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class MainActivityTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val mockRepository = mockk<SearchItemsRepository>(relaxed = true)
    private lateinit var viewModel: MainViewModel

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
            val mainViewModel = ViewModelProvider(activity).get(MainViewModel::class.java)

            // then
            assertNotNull(mainViewModel)
        }
    }

    @Test
    fun testMainActivityInstanceBinding() {
        // given
        launchActivity<MainActivity>().onActivity { activity ->
            val reflectionActivityBinding =
                ReflectionHelpers.getField<PipSearchAppMainActivityBinding>(activity, "binding")

            // then
            assertNotNull(reflectionActivityBinding)
            assertNotNull(reflectionActivityBinding.root)
        }
    }

    @Test
    fun testMainActivityBindingSetListener() {
        // given
        launchActivity<MainActivity>().onActivity { activity ->
            val reflectionActivityBinding =
                ReflectionHelpers.getField<PipSearchAppMainActivityBinding>(activity, "binding")

            // then
            assertNotNull(reflectionActivityBinding.pipMainHeaderSearchbox.onSearchListener)
            assertTrue(reflectionActivityBinding.pipMainHeaderSearchbox.onSearchListener is AndesSearchbox.OnSearchListener)
        }
    }

    @Test
    fun testMainActivityNotSetListenerWithoutBinding() {
        // given
        launchActivity<MainActivity>().onActivity { activity ->
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
    }

    @Test
    fun testMainActivityTextToSearchIntegratedWithMainViewModel() {
        // given
        launchActivity<MainActivity>().onActivity { activity ->
            // set mockRepository in the MainViewModel
            viewModel = ViewModelProvider(activity).get(MainViewModel::class.java)
            ReflectionHelpers.setField(viewModel, "repository", mockRepository)

            // set mockResponse
            val mockItem = ItemDto("Item 1", 10.0, "test", emptyList())
            coEvery {
                mockRepository.getAll(any())
            } returns RestClientResult.Success(ScreenItemsDto(listOf(mockItem)))

            val reflectionBeforeFetchResults =
                ReflectionHelpers.getField<MutableLiveData<ScreenItemsDto>>(
                    viewModel,
                    "_searchResults"
                )

            // verification before call viewmodel.fetchResults()
            assertNull(reflectionBeforeFetchResults.value)

            // call viewmodel.fetchResults() in the MainActivity
            val text = "test text"
            val sendTextToSearchMethod = activity.javaClass.getDeclaredMethod("sendTextToSearch", String::class.java)
            sendTextToSearchMethod.isAccessible = true

            // when
            sendTextToSearchMethod.invoke(activity, text)

            // then
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
    fun testMainActivityTextToSearchWithLongTextIntegratedWithMainViewModel() {
        // given
        launchActivity<MainActivity>().onActivity { activity ->
            // set mockRepository in the MainViewModel
            viewModel = ViewModelProvider(activity).get(MainViewModel::class.java)
            ReflectionHelpers.setField(viewModel, "repository", mockRepository)

            // set mockResponse
            val mockItem = ItemDto("Item 1", 10.0, "test", emptyList())
            coEvery {
                mockRepository.getAll(any())
            } returns RestClientResult.Success(ScreenItemsDto(listOf(mockItem)))

            val reflectionBeforeFetchResults =
                ReflectionHelpers.getField<MutableLiveData<ScreenItemsDto>>(
                    viewModel,
                    "_searchResults"
                )

            // verification before call viewmodel.fetchResults()
            assertNull(reflectionBeforeFetchResults.value)

            // call viewmodel.fetchResults() in the MainActivity
            val text = "This is a long text with 100 caracters for test function send to text in the view model and probe how it is work in this use case"
            val sendTextToSearchMethod = activity.javaClass.getDeclaredMethod("sendTextToSearch", String::class.java)
            sendTextToSearchMethod.isAccessible = true

            // when
            sendTextToSearchMethod.invoke(activity, text)

            // then
            val reflectionAfterFetchResults =
                ReflectionHelpers.getField<MutableLiveData<ScreenItemsDto>>(
                    viewModel,
                    "_searchResults"
                )

            // then
            assertEquals(reflectionBeforeFetchResults, reflectionAfterFetchResults)
        }
    }

    @Test
    fun testMainActivityTextToSearchWithBlankTextIntegratedWithMainViewModel() {
        // given
        launchActivity<MainActivity>().onActivity { activity ->
            // set mockRepository in the MainViewModel
            viewModel = ViewModelProvider(activity).get(MainViewModel::class.java)
            ReflectionHelpers.setField(viewModel, "repository", mockRepository)

            // set mockResponse
            val mockItem = ItemDto("Item 1", 10.0, "test", emptyList())
            coEvery {
                mockRepository.getAll(any())
            } returns RestClientResult.Success(ScreenItemsDto(listOf(mockItem)))

            val reflectionBeforeFetchResults =
                ReflectionHelpers.getField<MutableLiveData<ScreenItemsDto>>(
                    viewModel,
                    "_searchResults"
                )

            // verification before call viewmodel.fetchResults()
            assertNull(reflectionBeforeFetchResults.value)

            // call viewmodel.fetchResults() in the MainActivity
            val text = ""
            val sendTextToSearchMethod = activity.javaClass.getDeclaredMethod("sendTextToSearch", String::class.java)
            sendTextToSearchMethod.isAccessible = true

            // when
            sendTextToSearchMethod.invoke(activity, text)

            // then
            val reflectionAfterFetchResults =
                ReflectionHelpers.getField<MutableLiveData<ScreenItemsDto>>(
                    viewModel,
                    "_searchResults"
                )

            // then
            assertEquals(reflectionBeforeFetchResults, reflectionAfterFetchResults)
        }
    }

    @Test
    fun testMainActivitySetBaseScreenTitle() {
        // given
        launchActivity<MainActivity>().onActivity { activity ->
            val reflectionBinding =
            ReflectionHelpers.getField<PipSearchAppMainActivityBinding>(activity, "binding")

            // then
            assertEquals("Surfing Mercado Libre", reflectionBinding.pipMainBodyTitle.text.toString())
        }
    }

    @Test
    fun testMainActivityShowBaseScreenAndHideRecyclerView() {
        // given
        launchActivity<MainActivity>().onActivity { activity ->
            val reflectionBinding =
                ReflectionHelpers.getField<PipSearchAppMainActivityBinding>(activity, "binding")

            // then
            assertEquals(GONE, reflectionBinding.pipMainBodyRecyclerContainer.visibility)
            assertEquals(VISIBLE, reflectionBinding.pipMainBodyImageContainer.visibility)
        }
    }

    @Test
    fun testMainActivityCreateViewWithMainAdapterAndRecyclerView() {
        // given
        launchActivity<MainActivity>().onActivity { activity ->
            val reflectionBinding =
                ReflectionHelpers.getField<PipSearchAppMainActivityBinding>(activity, "binding")

            val reflectionMainAdapter = ReflectionHelpers.getField<MainAdapter>(activity, "mainAdapter")

            // then
            assertNotNull(reflectionMainAdapter)
            assertNotNull(reflectionBinding.pipMainBodyRecyclerContainer.layoutManager)
        }
    }

    @Test
    fun testMainActivitySetListOfItemsInTheAdapter() {
        // given
        launchActivity<MainActivity>().onActivity { activity ->
            // set mockResponse
            val mockItem1 = ItemDto("Item 1", 10.0, "test1", emptyList())
            val mockItem2 = ItemDto("Item 2", 20.0, "test2", emptyList())
            val mockListOfResults = listOf(mockItem1, mockItem2)
            ReflectionHelpers.setField(activity, "listOfItems", mockListOfResults)

            val showListOfItemsMethod = activity.javaClass.getDeclaredMethod("showListOfItems")
            showListOfItemsMethod.isAccessible = true

            // when
            showListOfItemsMethod.invoke(activity)

            val reflectionAdapter = ReflectionHelpers.getField<MainAdapter>(activity, "mainAdapter")

            // then
            assertEquals(mockListOfResults.count(), reflectionAdapter.itemCount)

            val reflectionListOftemsAdpater = ReflectionHelpers.getField<List<ItemDto>>(reflectionAdapter, "listOfItems")

            // then
            assertEquals(mockListOfResults, reflectionListOftemsAdpater)
        }
    }

    @Test
    fun testMainActivityShowRecyclerViewAndHideBaseScreen() {
        // given
        launchActivity<MainActivity>().onActivity { activity ->
            var reflectionBinding =
                ReflectionHelpers.getField<PipSearchAppMainActivityBinding>(activity, "binding")

            // asserts before init show recycler
            assertEquals(GONE, reflectionBinding.pipMainBodyRecyclerContainer.visibility)
            assertEquals(VISIBLE, reflectionBinding.pipMainBodyImageContainer.visibility)

            val showListOfItemsMethod = activity.javaClass.getDeclaredMethod("showListOfItems")
            showListOfItemsMethod.isAccessible = true

            // when
            showListOfItemsMethod.invoke(activity)

            reflectionBinding = ReflectionHelpers.getField(activity, "binding")

            // asserts before init show recycler
            assertEquals(VISIBLE, reflectionBinding.pipMainBodyRecyclerContainer.visibility)
            assertEquals(GONE, reflectionBinding.pipMainBodyImageContainer.visibility)
        }
    }

    @Test
    fun testMainActivityInitCartActivity() {
        // given
        launchActivity<MainActivity>().onActivity { activity ->
            val reflectionBinding =
                ReflectionHelpers.getField<PipSearchAppMainActivityBinding>(activity, "binding")

            var reflectionIntent = ReflectionHelpers.getField<Intent>(activity, "intent")

            // then
            assertNull(reflectionIntent)

            // when
            reflectionBinding.pipMainHeaderCartIcon.performClick()

            reflectionIntent = ReflectionHelpers.getField(activity, "intent")

            // then
            assertNotNull(reflectionIntent)
        }
    }
}

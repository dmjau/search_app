package com.mercadolibre.pipsearch.android.app.ui.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.mercadolibre.android.restclient.model.RestClientResult
import com.mercadolibre.pipsearch.android.app.data.model.ItemDto
import com.mercadolibre.pipsearch.android.app.data.model.ScreenItemsDto
import com.mercadolibre.pipsearch.android.app.data.repository.SearchItemsRepository
import com.mercadolibre.pipsearch.android.app.ui.view.viewmodels.MainViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.unmockkAll
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.robolectric.util.ReflectionHelpers

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()
    private val mockRepository = mockk<SearchItemsRepository>(relaxed = true)
    private lateinit var viewModel: MainViewModel

    @MockK(relaxed = true)
    private lateinit var observer: Observer<MutableList<ItemDto>>

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = MainViewModel()

        viewModel.selectedItems.observeForever(observer)
        ReflectionHelpers.setField(viewModel, "repository", mockRepository)
    }

    @After
    fun tearDown() {
        unmockkAll()
        viewModel.selectedItems.removeObserver(observer)
    }

    @Test
    fun testFetchResultsShouldUpdateSearchResultsOnSuccessResponse() =
        testDispatcher.runBlockingTest {
            // given
            val testItem = ItemDto("Item 1", 10.0, "test", emptyList())
            coEvery {
                mockRepository.getAll(any())
            } returns RestClientResult.Success(ScreenItemsDto(listOf(testItem)))

            val reflectionBeforeFetchResults =
                ReflectionHelpers.getField<MutableLiveData<ScreenItemsDto>>(
                    viewModel,
                    "_searchResults"
                )

            // verification before fetchResults()
            assertNull(reflectionBeforeFetchResults.value)

            // when
            viewModel.fetchResults("test")

            val reflectionAfterFetchResults =
                ReflectionHelpers.getField<MutableLiveData<ScreenItemsDto>>(
                    viewModel,
                    "_searchResults"
                )

            // then
            assertNotNull(reflectionAfterFetchResults.value)
        }

    @Test
    fun testFetchResultsShouldUpdateErrorResultOnErrorResponse() =
        testDispatcher.runBlockingTest {
            // given
            val errorMessage = "Error message"
            coEvery {
                mockRepository.getAll(any())
            } returns RestClientResult.Error(0, errorMessage)

            val reflectionBeforeFetchResults =
                ReflectionHelpers.getField<MutableLiveData<String>>(
                    viewModel,
                    "_exceptionOrErrorResult"
                )

            // verification before fetchResults()
            assertNull(reflectionBeforeFetchResults.value)

            // when
            viewModel.fetchResults("test")

            val reflectionAfterFetchResults =
                ReflectionHelpers.getField<MutableLiveData<String>>(
                    viewModel,
                    "_exceptionOrErrorResult"
                )

            // then
            assertNotNull(reflectionAfterFetchResults.value)
        }

    @Test
    fun testFetchResultsShouldUpdateExceptionResultOnExceptionResponse() =
        testDispatcher.runBlockingTest {
            // given
            val exceptionMessage = "Exception message"
            coEvery {
                mockRepository.getAll(any())
            } throws Exception(exceptionMessage)

            val reflectionBeforeFetchResults =
                ReflectionHelpers.getField<MutableLiveData<String>>(
                    viewModel,
                    "_exceptionOrErrorResult"
                )

            // verification before fetchResults()
            assertNull(reflectionBeforeFetchResults.value)

            // when
            viewModel.fetchResults("test")

            val reflectionAfterFetchResults =
                ReflectionHelpers.getField<MutableLiveData<String>>(
                    viewModel,
                    "_exceptionOrErrorResult"
                )

            // then
            assertNotNull(reflectionAfterFetchResults.value)
        }

    @Test
    fun testGetSearchResultsOnSuccessResponse() = testDispatcher.runBlockingTest {
        // given
        val testItem = ItemDto("Item 1", 10.0, "test", emptyList())
        coEvery {
            mockRepository.getAll(any())
        } returns RestClientResult.Success(ScreenItemsDto(listOf(testItem)))

        // when
        viewModel.fetchResults("test")

        val searchResult = viewModel.searchResults

        // then
        assertNotNull(searchResult)
        assertEquals("Item 1", searchResult.value!!.results[0].title)
        assertEquals(testItem, searchResult.value!!.results[0])
    }

    @Test
    fun testGetErrorResultOnErrorResponse() = testDispatcher.runBlockingTest {
        // given
        val errorMessage = "Error message"
        coEvery {
            mockRepository.getAll(any())
        } returns RestClientResult.Error(0, errorMessage)

        // when
        viewModel.fetchResults("test")

        val errorResult = viewModel.exceptionOrErrorResult

        // then
        assertNotNull(errorResult)
        assertEquals("Error message", errorResult.value.toString())
    }

    @Test
    fun testGetExceptionResultOnExceptionResponse() = testDispatcher.runBlockingTest {
        // given
        val exceptionMessage = "Exception message"
        coEvery {
            mockRepository.getAll(any())
        } throws Exception(exceptionMessage)

        // when
        viewModel.fetchResults("test")

        val exceptionResult = viewModel.exceptionOrErrorResult

        // then
        assertNotNull(exceptionResult)
        assertEquals("Exception message", exceptionResult.value)
    }

    @Test
    fun testOnChangePublicListSelectedItemsLiveData() {
        // given
        val reflectionSelectedItems = ReflectionHelpers.getField<MutableLiveData<MutableList<ItemDto>>>(viewModel, "selectedItems")

        // then
        assertEquals(emptyList<ItemDto>(), reflectionSelectedItems.value)
        assertEquals(emptyList<ItemDto>(), viewModel.selectedItems.value)

        verify(exactly = 1) {
            observer.onChanged(any())
        }
    }
}

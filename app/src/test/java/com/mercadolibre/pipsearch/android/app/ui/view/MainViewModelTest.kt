package com.mercadolibre.pipsearch.android.app.ui.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.mercadolibre.android.restclient.model.RestClientResult
import com.mercadolibre.pipsearch.android.app.data.model.ItemDto
import com.mercadolibre.pipsearch.android.app.data.model.ScreenItemsDto
import com.mercadolibre.pipsearch.android.app.data.repository.SearchItemsRepository
import com.mercadolibre.pipsearch.android.app.ui.view.viewmodels.MainViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
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

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = MainViewModel()

        ReflectionHelpers.setField(viewModel, "repository", mockRepository)
    }

    @Test
    fun `fetchResults should update searchResults on success response`() =
        testDispatcher.runBlockingTest {
            // given
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
    fun `fetchResults should update errorResult on error response`() =
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
    fun `fetchResults should update exceptionResult on exception response`() =
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
    fun `Get searchResults on success response`() = testDispatcher.runBlockingTest {
        // given
        val mockItem = ItemDto("Item 1", 10.0, "test", emptyList())
        coEvery {
            mockRepository.getAll(any())
        } returns RestClientResult.Success(ScreenItemsDto(listOf(mockItem)))

        // when
        viewModel.fetchResults("test")

        val searchResult = viewModel.searchResults

        // then
        assertNotNull(searchResult)
        assertEquals("Item 1", searchResult.value!!.results[0].title)
        assertEquals(mockItem, searchResult.value!!.results[0])
    }

    @Test
    fun `Get errorResult on error response`() = testDispatcher.runBlockingTest {
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
    fun `Get exceptionResult on exception response`() = testDispatcher.runBlockingTest {
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
}

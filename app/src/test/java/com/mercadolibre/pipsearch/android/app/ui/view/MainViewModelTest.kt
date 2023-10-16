package com.mercadolibre.pipsearch.android.app.ui.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.mercadolibre.android.restclient.model.RestClientResult
import com.mercadolibre.pipsearch.android.app.data.model.ItemDto
import com.mercadolibre.pipsearch.android.app.data.model.ScreenItemsDto
import com.mercadolibre.pipsearch.android.app.data.service.SearchItemsApiService
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertNotNull
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
    private val mockSearchItemsApiService = mockk<SearchItemsApiService>(relaxed = true)
    private lateinit var viewModel: MainViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = MainViewModel(mockSearchItemsApiService)
    }

    @Test
    fun `fetchResults should update searchResults on success`() = testDispatcher.runBlockingTest {
        // given
        val mockListItems = ItemDto("Item 1", 10.0, "test", emptyList())
        coEvery {
            mockSearchItemsApiService.getSearchItems()
        } returns RestClientResult.Success(ScreenItemsDto("query mock", listOf(mockListItems)))

        // when
        viewModel.fetchResults()

        val reflectionSearchResults =
            ReflectionHelpers.getField<MutableLiveData<ScreenItemsDto>>(viewModel, "searchResults")

        // then
        assertNotNull(reflectionSearchResults)
    }

    @Test
    fun `fetchResults should update errorResult on error`() = testDispatcher.runBlockingTest {
        // given
        val errorMessage = "Error message"
        coEvery {
            mockSearchItemsApiService.getSearchItems()
        } returns RestClientResult.Error(0, errorMessage)

        // when
        viewModel.fetchResults()

        val reflectionErrorResult =
            ReflectionHelpers.getField<MutableLiveData<String>>(viewModel, "errorResult")

        // then
        assertNotNull(reflectionErrorResult)
    }

    @Test
    fun `fetchResults should not update searchResults on error`() = testDispatcher.runBlockingTest {
        // given
        val errorMessage = "Error message"
        coEvery {
            mockSearchItemsApiService.getSearchItems()
        } returns RestClientResult.Error(0, errorMessage)

        // when
        viewModel.fetchResults()

        val reflectionExceptionResult =
            ReflectionHelpers.getField<MutableLiveData<String>>(viewModel, "exceptionResult")

        // then
        assertNotNull(reflectionExceptionResult)
    }
}

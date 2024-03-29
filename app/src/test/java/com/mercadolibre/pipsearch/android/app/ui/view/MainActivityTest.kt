package com.mercadolibre.pipsearch.android.app.ui.view

import android.content.ComponentName
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.mercadolibre.android.andesui.searchbox.AndesSearchbox
import com.mercadolibre.android.restclient.model.RestClientResult
import com.mercadolibre.pipsearch.android.R
import com.mercadolibre.pipsearch.android.app.data.model.ItemDto
import com.mercadolibre.pipsearch.android.app.data.model.ScreenItemsDto
import com.mercadolibre.pipsearch.android.app.data.repository.SearchItemsRepository
import com.mercadolibre.pipsearch.android.app.domain.CartManager
import com.mercadolibre.pipsearch.android.app.ui.view.viewmodels.MainViewModel
import com.mercadolibre.pipsearch.android.app.ui.view.adapters.MainAdapter
import com.mercadolibre.pipsearch.android.databinding.PipSearchAppMainActivityBinding
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.util.ReflectionHelpers

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
@Config(instrumentedPackages = ["androidx.loader.content"])
class MainActivityTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val activityTestRule = ActivityTestRule(MainActivity::class.java)

    @Rule
    @JvmField
    var intentsRule = IntentsTestRule(MainActivity::class.java)

    private val mockRepository = mockk<SearchItemsRepository>(relaxed = true)
    private lateinit var viewModel: MainViewModel
    private lateinit var cartManager: CartManager

    @Before
    fun setup() {
        cartManager = CartManager
    }

    @After
    fun tearDown() {
        cartManager.resetState()
    }

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
            val reflectionBinding = ReflectionHelpers.getField<PipSearchAppMainActivityBinding>(activity, "binding")

            // then
            assertNotNull(reflectionBinding)
            assertNotNull(reflectionBinding.root)
        }
    }

    @Test
    fun testMainActivityBindingSetListener() {
        // given
        launchActivity<MainActivity>().onActivity { activity ->
            val reflectionBinding = ReflectionHelpers.getField<PipSearchAppMainActivityBinding>(activity, "binding")

            // then
            assertNotNull(reflectionBinding.pipMainHeaderSearchbox.onSearchListener)
            assertTrue(reflectionBinding.pipMainHeaderSearchbox.onSearchListener is AndesSearchbox.OnSearchListener)
        }
    }

    @Test
    fun testMainActivityNotSetListenerWithoutBinding() {
        // given
        launchActivity<MainActivity>().onActivity { activity ->
            var reflectionBinding = ReflectionHelpers.getField<PipSearchAppMainActivityBinding>(activity, "binding")

            assertNotNull(reflectionBinding.pipMainHeaderSearchbox.onSearchListener)
            assertTrue(reflectionBinding.pipMainHeaderSearchbox.onSearchListener is AndesSearchbox.OnSearchListener)

            // when
            ReflectionHelpers.setField(activity, "binding", null)
            reflectionBinding = ReflectionHelpers.getField(activity, "binding")

            // then
            assertNull(reflectionBinding)
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
            val testItem = ItemDto("Item 1", 10.0, "test", emptyList())
            coEvery {
                mockRepository.getAll(any())
            } returns RestClientResult.Success(ScreenItemsDto(listOf(testItem)))

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
            val reflectionAfterFetchResults = ReflectionHelpers.getField<MutableLiveData<ScreenItemsDto>>(viewModel, "_searchResults")

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
            val testItem = ItemDto("Item 1", 10.0, "test", emptyList())
            coEvery {
                mockRepository.getAll(any())
            } returns RestClientResult.Success(ScreenItemsDto(listOf(testItem)))

            val reflectionBeforeFetchResults = ReflectionHelpers.getField<MutableLiveData<ScreenItemsDto>>(viewModel, "_searchResults")

            // verification before call viewmodel.fetchResults()
            assertNull(reflectionBeforeFetchResults.value)

            // call viewmodel.fetchResults() in the MainActivity
            val text = "This is a long text with 100 caracters for test function send to text in the view model and probe how it is work in this use case"
            val sendTextToSearchMethod = activity.javaClass.getDeclaredMethod("sendTextToSearch", String::class.java)
            sendTextToSearchMethod.isAccessible = true

            // when
            sendTextToSearchMethod.invoke(activity, text)

            // then
            val reflectionAfterFetchResults = ReflectionHelpers.getField<MutableLiveData<ScreenItemsDto>>(viewModel, "_searchResults")

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
            val testItem = ItemDto("Item 1", 10.0, "test", emptyList())
            coEvery {
                mockRepository.getAll(any())
            } returns RestClientResult.Success(ScreenItemsDto(listOf(testItem)))

            val reflectionBeforeFetchResults = ReflectionHelpers.getField<MutableLiveData<ScreenItemsDto>>(viewModel, "_searchResults")

            // verification before call viewmodel.fetchResults()
            assertNull(reflectionBeforeFetchResults.value)

            // call viewmodel.fetchResults() in the MainActivity
            val text = ""
            val sendTextToSearchMethod = activity.javaClass.getDeclaredMethod("sendTextToSearch", String::class.java)
            sendTextToSearchMethod.isAccessible = true

            // when
            sendTextToSearchMethod.invoke(activity, text)

            // then
            val reflectionAfterFetchResults = ReflectionHelpers.getField<MutableLiveData<ScreenItemsDto>>(viewModel, "_searchResults")

            // then
            assertEquals(reflectionBeforeFetchResults, reflectionAfterFetchResults)
        }
    }

    @Test
    fun testMainActivitySetBaseScreenTitle() {
        // given
        launchActivity<MainActivity>().onActivity { activity ->
            val reflectionBinding = ReflectionHelpers.getField<PipSearchAppMainActivityBinding>(activity, "binding")

            // then
            assertEquals(
                "Surfing Mercado Libre",
                reflectionBinding.pipMainBodyTitle.text.toString()
            )
        }
    }

    @Test
    fun testMainActivityShowBaseScreenAndHideRecyclerView() {
        // given
        launchActivity<MainActivity>().onActivity { activity ->
            val reflectionBinding = ReflectionHelpers.getField<PipSearchAppMainActivityBinding>(activity, "binding")

            // then
            assertEquals(GONE, reflectionBinding.pipMainBodyRecyclerContainer.visibility)
            assertEquals(VISIBLE, reflectionBinding.pipMainBodyImageContainer.visibility)
        }
    }

    @Test
    fun testMainActivityCreateViewWithMainAdapterAndRecyclerView() {
        // given
        launchActivity<MainActivity>().onActivity { activity ->
            val reflectionBinding = ReflectionHelpers.getField<PipSearchAppMainActivityBinding>(activity, "binding")

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
            val testItem1 = ItemDto("Item 1", 10.0, "test1", emptyList())
            val testItem2 = ItemDto("Item 2", 20.0, "test2", emptyList())
            val testListOfResults = listOf(testItem1, testItem2)
            ReflectionHelpers.setField(activity, "listOfItems", testListOfResults)

            val showListOfItemsMethod = activity.javaClass.getDeclaredMethod("showListOfItems")
            showListOfItemsMethod.isAccessible = true

            // when
            showListOfItemsMethod.invoke(activity)

            val reflectionAdapter = ReflectionHelpers.getField<MainAdapter>(activity, "mainAdapter")

            // then
            assertEquals(testListOfResults.count(), reflectionAdapter.itemCount)

            val reflectionListOftemsAdpater = ReflectionHelpers.getField<List<ItemDto>>(reflectionAdapter, "listOfItems")

            // then
            assertEquals(testListOfResults, reflectionListOftemsAdpater)
        }
    }

    @Test
    fun testMainActivityShowRecyclerViewAndHideBaseScreen() {
        // given
        launchActivity<MainActivity>().onActivity { activity ->
            var reflectionBinding = ReflectionHelpers.getField<PipSearchAppMainActivityBinding>(activity, "binding")

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
    fun testMainActivityIntentStartCartActivityWhenIconIsClicked() {
        // when
        Espresso.onView(ViewMatchers.withId(R.id.pip_main_header_cart_icon)).perform(ViewActions.click())

        // then
        Intents.intended(IntentMatchers.hasComponent(ComponentName(InstrumentationRegistry.getInstrumentation().context, CartActivity::class.java)))
    }

    @Test
    fun testInitialQuantityItemsOnCartWhenStartMainActivity() {
        // given
        launchActivity<MainActivity>().onActivity { activity ->

            val reflectionBinding = ReflectionHelpers.getField<PipSearchAppMainActivityBinding>(activity, "binding")
            viewModel = ViewModelProvider(activity).get(MainViewModel::class.java)

            // then
            assertEquals("0", reflectionBinding.pipMainHeaderCartPill.text)
        }
    }

    @Test
    fun testUpdateViewQuantityItemsOnCartFromMainViewModel() {
        // given
        launchActivity<MainActivity>().onActivity { activity ->

            val reflectionBinding = ReflectionHelpers.getField<PipSearchAppMainActivityBinding>(activity, "binding")
            viewModel = ViewModelProvider(activity).get(MainViewModel::class.java)

            val testItem1 = ItemDto("Item 1", 10.0, "test_1", emptyList())
            val mutableListItemsOnCart: MutableList<ItemDto> = mutableListOf()

            val reflectionMutableLiveDataSelectedItems = ReflectionHelpers.getField<MutableLiveData<MutableList<ItemDto>>>(viewModel, "selectedItems")

            // then
            assertEquals("0", reflectionBinding.pipMainHeaderCartPill.text)

            // added first item
            mutableListItemsOnCart.add(testItem1)
            reflectionMutableLiveDataSelectedItems.value = mutableListItemsOnCart

            ReflectionHelpers.setField(viewModel, "selectedItems", reflectionMutableLiveDataSelectedItems)

            var reflectionSelectedItems = ReflectionHelpers.getField<MutableLiveData<MutableList<ItemDto>>>(viewModel, "selectedItems")

            // then
            assertEquals(1, reflectionSelectedItems.value!!.size)
            assertEquals(1, viewModel.selectedItems.value!!.size)
            assertEquals("1", reflectionBinding.pipMainHeaderCartPill.text)

            // added second item
            val testItem2 = ItemDto("Item 2", 20.0, "test_2", emptyList())
            mutableListItemsOnCart.add(testItem2)
            reflectionMutableLiveDataSelectedItems.value = mutableListItemsOnCart

            ReflectionHelpers.setField(viewModel, "selectedItems", reflectionMutableLiveDataSelectedItems)

            reflectionSelectedItems = ReflectionHelpers.getField(viewModel, "selectedItems")

            // then
            assertEquals(2, reflectionSelectedItems.value!!.size)
            assertEquals(2, viewModel.selectedItems.value!!.size)
            assertEquals("2", reflectionBinding.pipMainHeaderCartPill.text)
        }
    }

    @Test
    fun testMainActivityAddItemDataOnCart() {
        // given
        launchActivity<MainActivity>().onActivity { activity ->

            viewModel = ViewModelProvider(activity).get(MainViewModel::class.java)
            val testItem1 = ItemDto("Item 1", 10.0, "test_1", emptyList())
            val testItem2 = ItemDto("Item 2", 20.0, "test_2", emptyList())

            // call viewmodel.addItemToCart(itemData) in the MainActivity
            val onItemToAddToCartMethod = activity.javaClass.getDeclaredMethod("onItemAddToCart", ItemDto::class.java)
            onItemToAddToCartMethod.isAccessible = true

            // when
            onItemToAddToCartMethod.invoke(activity, testItem1)

            var reflectionItemsOnCart = ReflectionHelpers.getField<MutableLiveData<MutableList<ItemDto>>>(viewModel, "selectedItems")

            // then first item was added
            assertEquals(1, reflectionItemsOnCart.value!!.size)
            assertEquals(1, viewModel.selectedItems.value!!.size)

            // when
            onItemToAddToCartMethod.invoke(activity, testItem2)

            reflectionItemsOnCart = ReflectionHelpers.getField(viewModel, "selectedItems")

            // then second item was added
            assertEquals(2, reflectionItemsOnCart.value!!.size)
            assertEquals(2, viewModel.selectedItems.value!!.size)
        }
    }
}

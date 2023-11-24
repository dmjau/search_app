package com.mercadolibre.pipsearch.android.app.ui.view.viewholders

import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.facebook.drawee.backends.pipeline.Fresco
import com.mercadolibre.android.commons.utils.generics.TestResourceParser
import com.mercadolibre.android.testing.AbstractRobolectricTest
import com.mercadolibre.pipsearch.android.app.data.model.ItemDto
import com.mercadolibre.pipsearch.android.databinding.PipSearchAppMainListItemBinding
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.robolectric.util.ReflectionHelpers

class MainViewHolderTest : AbstractRobolectricTest() {

    private val parent = LinearLayout(context)
    private val layoutInflater = LayoutInflater.from(parent.context)
    private val itemDataFull = "item_data_full.json"
    private val itemDataWithoutTags = "item_data_without_tags.json"

    @Before
    override fun setUp() {
        super.setUp()
        Fresco.initialize(context)
    }

    @Test
    fun TestInstanceMainViewHolder() {
        // given
        val mainViewHolder = MainViewHolder.instance(layoutInflater, parent)

        // then
        assertNotNull(mainViewHolder)
        assertEquals(MainViewHolder::class.java, mainViewHolder::class.java)
    }

    @Test
    fun TestBindViewInMainViewHolder() {
        // given
        TestResourceParser.getTestResourceObject(itemDataFull, ItemDto::class.java).let { data ->
            val mainViewHolder = MainViewHolder.instance(layoutInflater, parent)

            // when
            mainViewHolder.bind(data!!) { }

            ReflectionHelpers.getField<PipSearchAppMainListItemBinding>(mainViewHolder, "binding").let { binding ->

                // then
                assertEquals("Cerveza Heineken Rubia", binding.title.text.toString())
                assertEquals("12885.6", binding.price.text.toString())
                assertEquals("$", binding.iconPrice.text.toString())
                assertNotNull(binding.image)
                assertNotNull(binding.image.drawable)
                assertNotNull(binding.image.controller)
                assertEquals(View.VISIBLE, binding.market.visibility)
                assertEquals("Supermercado", binding.market.text.toString())
                assertEquals(View.VISIBLE, binding.buttomAddToCart.visibility)
            }
        }
    }

    @Test
    fun TestBindViewInMainViewHolderCleanTagView() {
        // given
        TestResourceParser.getTestResourceObject(itemDataFull, ItemDto::class.java).let { data ->
            val mainViewHolder = MainViewHolder.instance(layoutInflater, parent)

            // when set first ItemDto Data
            mainViewHolder.bind(data!!) { }

            ReflectionHelpers.getField<PipSearchAppMainListItemBinding>(mainViewHolder, "binding").let { binding ->
                // then
                assertEquals("Cerveza Heineken Rubia", binding.title.text.toString())
                assertEquals("12885.6", binding.price.text.toString())
                assertEquals("$", binding.iconPrice.text.toString())
                assertNotNull(binding.image)
                assertNotNull(binding.image.drawable)
                assertNotNull(binding.image.controller)
                assertEquals(View.VISIBLE, binding.market.visibility)
                assertEquals("Supermercado", binding.market.text.toString())
                assertEquals(View.VISIBLE, binding.buttomAddToCart.visibility)
            }

            // when set second ItemDto Data
            val secondItemDto = ItemDto("second item", 100.0, "http://test", listOf("supermarket_eligible", "no_tag_important"))
            mainViewHolder.bind(secondItemDto) { }

            ReflectionHelpers.getField<PipSearchAppMainListItemBinding>(mainViewHolder, "binding").let { binding ->
                // then
                assertEquals("second item", binding.title.text.toString())
                assertEquals("100.0", binding.price.text.toString())
                assertEquals("$", binding.iconPrice.text.toString())
                assertNotNull(binding.image)
                assertNotNull(binding.image.drawable)
                assertNotNull(binding.image.controller)
                assertEquals(View.VISIBLE, binding.market.visibility)
                assertEquals("Supermercado", binding.market.text.toString())
                assertEquals(View.VISIBLE, binding.buttomAddToCart.visibility)
            }
        }
    }

    @Test
    fun TestBindViewWithEmptyTagsList() {
        // given
        TestResourceParser.getTestResourceObject(itemDataWithoutTags, ItemDto::class.java).let { data ->
            val mainViewHolder = MainViewHolder.instance(layoutInflater, parent)

            // when
            mainViewHolder.bind(data!!) { }

            ReflectionHelpers.getField<PipSearchAppMainListItemBinding>(mainViewHolder, "binding").let { binding ->

                // then
                assertEquals("Cerveza Heineken Rubia", binding.title.text.toString())
                assertEquals("12885.6", binding.price.text.toString())
                assertEquals("$", binding.iconPrice.text.toString())
                assertNotNull(binding.image)
                assertNotNull(binding.image.drawable)
                assertNotNull(binding.image.controller)
                assertEquals(View.GONE, binding.market.visibility)
                assertEquals(View.GONE, binding.buttomAddToCart.visibility)
            }
        }
    }

    @Test
    fun TestReturnItemDataWhenButtonIsClicked() {
        // given
        TestResourceParser.getTestResourceObject(itemDataWithoutTags, ItemDto::class.java).let { data ->
            val mainViewHolder = MainViewHolder.instance(layoutInflater, parent)

            lateinit var itemDataFromView: ItemDto

            mainViewHolder.bind(data!!) { itemData ->
                itemDataFromView = itemData
            }

            ReflectionHelpers.getField<PipSearchAppMainListItemBinding>(mainViewHolder, "binding").let { binding ->

                // when
                binding.buttomAddToCart.performClick()

                // then
                assertEquals(itemDataFromView, data)
            }
        }
    }
}
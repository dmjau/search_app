package com.mercadolibre.pipsearch.android.app.ui.view.viewholders

import android.view.LayoutInflater
import android.widget.LinearLayout
import com.facebook.drawee.backends.pipeline.Fresco
import com.mercadolibre.android.commons.utils.generics.TestResourceParser
import com.mercadolibre.android.testing.AbstractRobolectricTest
import com.mercadolibre.pipsearch.android.app.data.model.ItemDto
import com.mercadolibre.pipsearch.android.databinding.PipSearchAppCartListItemBinding
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.robolectric.util.ReflectionHelpers

class CartViewHolderTest : AbstractRobolectricTest() {

    private val parent = LinearLayout(context)
    private val layoutInflater = LayoutInflater.from(parent.context)
    private val itemDataFull = "item_data_full.json"

    @Before
    override fun setUp() {
        super.setUp()
        Fresco.initialize(context)
    }

    @Test
    fun TestInstanceMainViewHolder() {
        // given
        val cartViewHolder = CartViewHolder.instance(layoutInflater, parent)

        // then
        assertNotNull(cartViewHolder)
        assertEquals(CartViewHolder::class.java, cartViewHolder::class.java)
    }

    @Test
    fun TestBindViewInCartViewHolder() {
        // given
        TestResourceParser.getTestResourceObject(itemDataFull, ItemDto::class.java).let { data ->
            val cartViewHolder = CartViewHolder.instance(layoutInflater, parent)

            // when
            cartViewHolder.bind(data!!)

            ReflectionHelpers.getField<PipSearchAppCartListItemBinding>(cartViewHolder, "binding").let { binding ->

                // then
                assertEquals("Cerveza Heineken Rubia", binding.title.text.toString())
                assertEquals("12885.6", binding.price.text.toString())
                assertEquals("$", binding.iconPrice.text.toString())
                assertNotNull(binding.image)
                assertNotNull(binding.image.drawable)
                assertNotNull(binding.image.controller)
            }
        }
    }
}

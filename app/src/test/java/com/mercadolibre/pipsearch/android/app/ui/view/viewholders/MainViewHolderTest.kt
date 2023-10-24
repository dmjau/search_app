package com.mercadolibre.pipsearch.android.app.ui.view.viewholders

import android.widget.LinearLayout
import com.facebook.drawee.backends.pipeline.Fresco
import com.mercadolibre.android.testing.AbstractRobolectricTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class MainViewHolderTest : AbstractRobolectricTest() {

    private val parent = LinearLayout(context)
    private val itemDataFull = "item_data_full.json"

    override fun setUp() {
        super.setUp()
        Fresco.initialize(context)
    }

    @Test
    fun TestInstanceMainViewHolder() {
        // given
        val mainViewHolder = MainViewHolder.instance(parent)

        // then
        assertNotNull(mainViewHolder)
        assertEquals(MainViewHolder::class.java, mainViewHolder::class.java)
    }
}
package com.mercadolibre.pipsearch.android.app.ui.view

import android.os.Bundle
import com.mercadolibre.android.commons.core.AbstractActivity
import com.mercadolibre.pipsearch.android.R

/**
 * Main activity class
 */
class MainActivity : AbstractActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pip_search_app_main_activity)
    }
}

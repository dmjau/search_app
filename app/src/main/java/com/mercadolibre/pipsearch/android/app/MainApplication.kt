package com.mercadolibre.pipsearch.android.app

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import com.mercadolibre.android.andesui.configurator.AndesConfigurator
import com.mercadolibre.android.configuration.manager.ConfigurationManager
import com.mercadolibre.android.font.configurator.FontConfigurator

/**
 * Main Application class that extends from Application to execute the start method only once.
 */
class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ConfigurationManager.configure(this)
        Fresco.initialize(this)
        AndesConfigurator().configure(this)
        FontConfigurator().configure(this)
    }
}

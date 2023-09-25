package com.mercadolibre.pipsearch.android.app

import android.app.Application
import com.mercadolibre.android.configuration.manager.ConfigurationManager

/**
 * Main Application class that extends from Application to execute the start method only once.
 */
class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ConfigurationManager.configure(this)
    }
}

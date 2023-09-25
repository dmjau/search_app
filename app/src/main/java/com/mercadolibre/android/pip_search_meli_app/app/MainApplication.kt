package com.mercadolibre.android.pip_search_meli_app.app

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

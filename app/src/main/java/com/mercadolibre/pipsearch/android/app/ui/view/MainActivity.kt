package com.mercadolibre.pipsearch.android.app.ui.view

import android.os.Bundle
import androidx.activity.viewModels
import com.mercadolibre.android.commons.core.AbstractActivity
import com.mercadolibre.pipsearch.android.R
import com.mercadolibre.pipsearch.android.databinding.PipSearchAppMainActivityBinding

/**
 * Main activity class
 */
class MainActivity : AbstractActivity() {

    private lateinit var binding: PipSearchAppMainActivityBinding
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = PipSearchAppMainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}

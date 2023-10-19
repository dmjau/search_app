package com.mercadolibre.pipsearch.android.app.ui.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.mercadolibre.android.andesui.searchbox.AndesSearchbox
import com.mercadolibre.android.commons.core.AbstractActivity
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

        initSearchBox()
    }

    private fun initSearchBox() {
        val searchBox = binding.pipMainHeaderSearchbox

        searchBox.onSearchListener = object : AndesSearchbox.OnSearchListener {
            override fun onSearch(text: String) {
                sendTextToSearch(text)
            }
        }

        searchBox.onSearchboxCloseClickedListener = AndesSearchbox.OnSearchboxCloseClickedListener {
            // clean the result search list
        }
    }

    private fun sendTextToSearch(text: String) {
        var textoRecibido = text
    }
}

package com.mercadolibre.pipsearch.android.app.ui.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.mercadolibre.android.andesui.searchbox.AndesSearchbox
import com.mercadolibre.android.andesui.snackbar.AndesSnackbar
import com.mercadolibre.android.andesui.snackbar.duration.AndesSnackbarDuration
import com.mercadolibre.android.andesui.snackbar.type.AndesSnackbarType
import com.mercadolibre.android.commons.core.AbstractActivity
import com.mercadolibre.pipsearch.android.databinding.PipSearchAppMainActivityBinding

/**
 * Main activity class
 */
class MainActivity : AbstractActivity() {

    private var binding: PipSearchAppMainActivityBinding? = null
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = PipSearchAppMainActivityBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        initSearchBox()
    }

    /**
     * Instance and init searchbox listeners.
     *
     */
    private fun initSearchBox() {
        binding?.let {
            it.pipMainHeaderSearchbox.onSearchListener = object : AndesSearchbox.OnSearchListener {
                override fun onSearch(text: String) {
                    sendTextToSearch(text)
                }
            }
        }
    }

    /**
     * Receive text from Searchbox UI.
     * Verify if it is too long, if it is not, send to search.
     */
    private fun sendTextToSearch(text: String) {
        if (text.length < 100) {
            mainViewModel.fetchResults(text.lowercase())
        } else {
            showSnackbar()
        }
    }

    private fun showSnackbar() {
        AndesSnackbar(
            this,
            binding!!.root,
            AndesSnackbarType.ERROR,
            "Intenta nuevamente",
            AndesSnackbarDuration.SHORT
        ).show()
    }
}

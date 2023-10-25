package com.mercadolibre.pipsearch.android.app.ui.view

import android.os.Bundle
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
        setInitialScreen()
    }

    /**
     * Instance and init searchbox listeners.
     *
     */
    private fun initSearchBox() {
        binding!!.pipMainHeaderSearchbox.onSearchListener =
            object : AndesSearchbox.OnSearchListener {
                override fun onSearch(text: String) {
                    sendTextToSearch(text)
                }
            }
    }

    private fun setInitialScreen() {
        val initialTitle = "Surfing Mercado Libre"
        setMainTitle(initialTitle)
    }

    /**
     * Receive text from Searchbox UI.
     * Verify if it is too long, if it is not, send to search.
     */
    private fun sendTextToSearch(text: String) {
        if (text.length < 100) {
            mainViewModel.fetchResults(text)
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

    private fun setMainTitle(title: String) {
        binding!!.pipMainBodyTitle.append(title)
    }
}

package com.mercadolibre.pipsearch.android.app.ui.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mercadolibre.android.andesui.searchbox.AndesSearchbox
import com.mercadolibre.android.andesui.snackbar.AndesSnackbar
import com.mercadolibre.android.andesui.snackbar.duration.AndesSnackbarDuration
import com.mercadolibre.android.andesui.snackbar.type.AndesSnackbarType
import com.mercadolibre.android.andesui.textview.color.AndesTextViewColor
import com.mercadolibre.android.commons.core.AbstractActivity
import com.mercadolibre.pipsearch.android.R
import com.mercadolibre.pipsearch.android.app.data.model.ItemDto
import com.mercadolibre.pipsearch.android.app.ui.view.adapters.MainAdapter
import com.mercadolibre.pipsearch.android.app.ui.view.viewmodels.MainViewModel
import com.mercadolibre.pipsearch.android.databinding.PipSearchAppMainActivityBinding

/**
 * Main activity class
 */
class MainActivity : AbstractActivity() {

    private var binding: PipSearchAppMainActivityBinding? = null
    private var mainAdapter: MainAdapter = MainAdapter()
    private val mainViewModel: MainViewModel by viewModels()
    private var listOfItems: List<ItemDto> = emptyList()
    private var intent: Intent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = PipSearchAppMainActivityBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        initSearchBox()
        setBaseScreen()
        linkedWithCartActivity()
        initRecyclerViewAndAdapter()
        observe()
    }

    /**
     * Instance and init searchbox listeners.
     */
    private fun initSearchBox() {
        binding!!.pipMainHeaderSearchbox.onSearchListener =
            object : AndesSearchbox.OnSearchListener {
                override fun onSearch(text: String) {
                    sendTextToSearch(text)
                }
            }
    }

    /**
     * Init recycler view and adapter.
     */
    private fun initRecyclerViewAndAdapter() {
        with(binding!!.pipMainBodyRecyclerContainer) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = mainAdapter
        }
    }

    /**
     * Set base screen with initial title before any search.
     */
    private fun setBaseScreen() {
        showBaseScreenHideRecyclerView()
        setBaseTitle(
            R.string.pip_search_app_main_title_initial_text,
            AndesTextViewColor.Disabled
        )
    }

    private fun linkedWithCartActivity() {
        binding!!.pipMainHeaderCartIcon.setOnClickListener {
            setIntentAndStartCartActivity()
        }
    }

    private fun setIntentAndStartCartActivity() {
        if (intent == null) {
            intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        } else {
            startActivity(intent)
        }
    }

    /**
     * Observes livedata variables from view model.
     */
    private fun observe() {
        observeSerachResults()
        observeExceptionsOrErrors()
    }

    private fun observeSerachResults() {
        mainViewModel.searchResults.observe(
            this
        ) {
            listOfItems = it.results
            showListOfItems()
        }
    }

    private fun observeExceptionsOrErrors() {
        mainViewModel.exceptionOrErrorResult.observe(
            this
        ) {
            // added logic to manage errors views
        }
    }

    /**
     * Init Use Case when receive results of search to show.
     */
    private fun showListOfItems() {
        showRecyclerViewHideBaseScreen()
        setItemsToAdapter()
    }

    private fun setItemsToAdapter() {
        mainAdapter.setItems(listOfItems)
    }

    /**
     * Receive text from Searchbox UI.
     * Verify if it is too long or it is blank and send to search or show error message.
     */
    private fun sendTextToSearch(text: String) {
        when {
            validateText(text) -> mainViewModel.fetchResults(text)
            else -> showSnackbar()
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

    private fun setBaseTitle(title: Int, color: AndesTextViewColor) {
        binding!!.pipMainBodyTitle.apply {
            this.text = getString(title)
            this.setTextColor(color)
        }
    }

    private fun validateText(text: String) = text.length < 100 && text.isNotBlank()

    private fun showRecyclerViewHideBaseScreen() {
        binding!!.pipMainBodyRecyclerContainer.apply {
            this.removeAllViews()
            this.visibility = View.VISIBLE
        }
        binding!!.pipMainBodyImageContainer.visibility = View.GONE
    }

    private fun showBaseScreenHideRecyclerView() {
        binding!!.pipMainBodyRecyclerContainer.visibility = View.GONE
        binding!!.pipMainBodyImageContainer.visibility = View.VISIBLE
    }
}

package com.mercadolibre.pipsearch.android.app.ui.view

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mercadolibre.android.andesui.searchbox.AndesSearchbox
import com.mercadolibre.android.andesui.snackbar.AndesSnackbar
import com.mercadolibre.android.andesui.snackbar.duration.AndesSnackbarDuration
import com.mercadolibre.android.andesui.snackbar.type.AndesSnackbarType
import com.mercadolibre.android.commons.core.AbstractActivity
import com.mercadolibre.pipsearch.android.app.data.model.ItemDto
import com.mercadolibre.pipsearch.android.app.ui.view.adapters.MainAdapter
import com.mercadolibre.pipsearch.android.databinding.PipSearchAppMainActivityBinding

/**
 * Main activity class
 */
class MainActivity : AbstractActivity() {

    private var binding: PipSearchAppMainActivityBinding? = null
    private var mainAdapter: MainAdapter = MainAdapter()
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = PipSearchAppMainActivityBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        initSearchBox()
        setInitialScreen()
        initRecyclerViewAndAdapter()
        observe()
    }

    /**
     * Instance and init searchbox listeners.
     */
    private fun initSearchBox() {
        binding!!.pipMainHeaderSearchbox.onSearchListener = object : AndesSearchbox.OnSearchListener {
                override fun onSearch(text: String) {
                    sendTextToSearch(text)
                }
            }
    }

    /**
     * Init screen before any search.
     */
    private fun setInitialScreen() {
        showInitialScreenHideRecyclerView()
        val initialTitle = "Surfing Mercado Libre"
        setMainTitle(initialTitle)
    }

    /**
     * Init recycler view.
     */
    private fun initRecyclerViewAndAdapter() {
        with(binding!!.pipMainBodyRecyclerContainer) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = mainAdapter
        }
    }

    private fun showRecyclerViewHideInitialScreen() {
        binding!!.pipMainBodyRecyclerContainer.removeAllViews()
        binding!!.pipMainBodyRecyclerContainer.visibility= View.VISIBLE
        binding!!.pipMainBodyImageContainer.visibility = View.GONE
    }

    private fun showInitialScreenHideRecyclerView() {
        binding!!.pipMainBodyImageContainer.removeAllViews()
        binding!!.pipMainBodyRecyclerContainer.visibility= View.GONE
        binding!!.pipMainBodyImageContainer.visibility = View.VISIBLE
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
            { lifecycle },
            {
                setItemsToAdapter(it.results)
            }
        )
    }

    private fun observeExceptionsOrErrors() {
        mainViewModel.exceptionOrErrorResult.observe(
            { lifecycle },
            {
                //val data = it
            }
        )
    }

    /**
     * Set items in the adapter from search result.
     */
    private fun setItemsToAdapter(listItems: List<ItemDto> = emptyList()) {
        mainAdapter.setItems(listItems)
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

    private fun setMainTitle(title: String) {
        binding!!.pipMainBodyTitle.append(title)
    }

    private fun validateText(text: String) = text.length < 100 && text.isNotBlank()
}

package com.mercadolibre.pipsearch.android.app.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mercadolibre.pipsearch.android.app.ui.view.adapters.CartAdapter
import com.mercadolibre.pipsearch.android.app.ui.view.viewmodels.CartViewModel
import com.mercadolibre.pipsearch.android.R
import com.mercadolibre.pipsearch.android.app.data.model.ItemDto
import com.mercadolibre.pipsearch.android.databinding.PipSearchAppCartActivityBinding

class CartActivity : AppCompatActivity() {

    private var binding: PipSearchAppCartActivityBinding? = null
    private var cartAdapter: CartAdapter = CartAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = PipSearchAppCartActivityBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        setTitleHeader()
        setBaseScreen()
        setBackToMainActivityListener()
        initRecyclerViewAndAdapter()
    }

    /**
     * Set base screen with initial title and subtitle.
     */
    private fun setBaseScreen() {
        showBaseScreenHideRecyclerView()
        setBaseTitle(R.string.pip_search_app_cart_body_title_text)
        setBaseSubtitle(R.string.pip_search_app_cart_body_subtitle_text)
    }

    private fun setBackToMainActivityListener() {
        binding!!.pipCartHeaderBack.setOnClickListener {
            finish()
        }
    }

    /**
     * Set base screen attributes.
     */
    private fun setTitleHeader() {
        binding!!.pipCartHeaderText.text = getString(R.string.pip_search_app_cart_header_title_text)
    }

    private fun setBaseTitle(title: Int) {
        binding!!.pipCartBodyTitle.text = getString(title)
    }

    private fun setBaseSubtitle(subtitle: Int) {
        binding!!.pipCartBodySubtitle.text = getString(subtitle)
    }

    private fun showBaseScreenHideRecyclerView() {
        binding!!.pipCartBodyRecyclerContainer.visibility = View.GONE
        binding!!.pipCartBodyImageContainer.visibility = View.VISIBLE
    }

    /**
     * Init recycler view and adapter.
     */
    private fun initRecyclerViewAndAdapter() {
        with(binding!!.pipCartBodyRecyclerContainer) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = cartAdapter
        }
    }
}

package com.mercadolibre.pipsearch.android.app.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.mercadolibre.pipsearch.android.R
import com.mercadolibre.pipsearch.android.databinding.PipSearchAppCartActivityBinding

class CartActivity : AppCompatActivity() {

    private var binding: PipSearchAppCartActivityBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = PipSearchAppCartActivityBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        setTitleHeader()
        setBaseScreen()
        linkedWithMainActivity()
    }

    /**
     * Set base screen with initial title before any search.
     */
    private fun setTitleHeader() {
        binding!!.pipCartHeaderText.apply {
            this.text = getString(R.string.pip_search_app_cart_header_title_text)
        }
    }

    /**
     * Set base screen with initial title and subtitle.
     */
    private fun setBaseScreen() {
        showBaseScreenHideRecyclerView()
        setBaseTitle(R.string.pip_search_app_cart_body_title_text)
        setBaseSubtitle(R.string.pip_search_app_cart_body_subtitle_text)
    }

    private fun linkedWithMainActivity() {
        binding!!.pipCartHeaderBack.setOnClickListener {
            setIntentAndStartMainActivity()
        }
    }

    private fun setIntentAndStartMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    private fun setBaseTitle(title: Int) {
        binding!!.pipCartBodyTitle.apply {
            this.text = getString(title)
        }
    }
    private fun setBaseSubtitle(subtitle: Int) {
        binding!!.pipCartBodySubtitle.apply {
            this.text = getString(subtitle)
        }
    }

    private fun showBaseScreenHideRecyclerView() {
        binding!!.pipCartBodyRecyclerContainer.visibility = View.GONE
        binding!!.pipCartBodyImageContainer.visibility = View.VISIBLE
    }

}

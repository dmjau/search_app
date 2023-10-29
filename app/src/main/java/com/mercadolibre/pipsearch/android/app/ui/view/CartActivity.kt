package com.mercadolibre.pipsearch.android.app.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.mercadolibre.pipsearch.android.databinding.PipSearchAppCartActivityBinding

class CartActivity : AppCompatActivity() {

    companion object {
        const val CART_HEADER_TITLE = "Carrito"
        const val CART_BODY_TITLE = "El carrito está vacío"
        const val CART_BODY_SUBTITLE = "Volvé a la pantalla de principal para buscar ítems."
    }

    private var binding: PipSearchAppCartActivityBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = PipSearchAppCartActivityBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        setTitleHeader()
        initBaseScreen()
    }

    /**
     * Set base screen with initial title before any search.
     */
    private fun setTitleHeader() {
        binding!!.pipCartHeaderText.text = CART_HEADER_TITLE
    }

    /**
     * Set base screen with initial title before any search.
     */
    private fun initBaseScreen() {
        showBaseScreenHideRecyclerView()
        setBaseTitle(CART_BODY_TITLE)
        setBaseSubtitle(CART_BODY_SUBTITLE)
    }

    private fun setBaseTitle(title: String) {
        binding!!.pipCartBodyTitle.append(title)
    }
    private fun setBaseSubtitle(subtitle: String) {
        binding!!.pipCartBodySubtitle.append(subtitle)
    }

    private fun showBaseScreenHideRecyclerView() {
        binding!!.pipCartBodyRecyclerContainer.visibility = View.GONE
        binding!!.pipCartBodyImageContainer.visibility = View.VISIBLE
    }

}

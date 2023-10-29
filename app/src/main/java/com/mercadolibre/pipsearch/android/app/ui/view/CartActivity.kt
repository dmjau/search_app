package com.mercadolibre.pipsearch.android.app.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mercadolibre.pipsearch.android.app.data.model.ItemDto
import com.mercadolibre.pipsearch.android.app.ui.view.adapters.CartAdapter
import com.mercadolibre.pipsearch.android.app.ui.view.viewmodels.CartViewModel
import com.mercadolibre.pipsearch.android.databinding.PipSearchAppCartActivityBinding

class CartActivity : AppCompatActivity() {

    companion object {
        const val CART_HEADER_TITLE = "Carrito"
        const val CART_BODY_TITLE = "El carrito está vacío"
        const val CART_BODY_SUBTITLE = "Volvé a la pantalla de principal para buscar ítems."
    }

    private var binding: PipSearchAppCartActivityBinding? = null
    private var cartAdapter: CartAdapter = CartAdapter()
    private val cartViewModel: CartViewModel by viewModels()
    private var intent: Intent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = PipSearchAppCartActivityBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        setTitleHeader()
        setBaseScreen()
        linkedWithMainActivity()
        initRecyclerViewAndAdapter()
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
    private fun setBaseScreen() {
        showBaseScreenHideRecyclerView()
        setBaseTitle(CART_BODY_TITLE)
        setBaseSubtitle(CART_BODY_SUBTITLE)
    }

    private fun linkedWithMainActivity() {
        binding!!.pipCartHeaderBack.setOnClickListener {
            setIntentAndStartMainActivity()
        }
    }

    private fun setIntentAndStartMainActivity() {
        if (intent == null) {
            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        } else {
            startActivity(intent)
        }
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

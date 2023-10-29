package com.mercadolibre.pipsearch.android.app.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mercadolibre.pipsearch.android.R
import com.mercadolibre.pipsearch.android.databinding.PipSearchAppCartActivityBinding

class CartActivity : AppCompatActivity() {

    companion object {
        const val TITLE_HEADER_CART = "Carrito"
    }

    private var binding: PipSearchAppCartActivityBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = PipSearchAppCartActivityBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
    }

}

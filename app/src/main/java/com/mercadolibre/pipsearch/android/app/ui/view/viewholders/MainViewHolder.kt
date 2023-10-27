package com.mercadolibre.pipsearch.android.app.ui.view.viewholders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mercadolibre.android.andesui.color.AndesColor
import com.mercadolibre.android.andesui.textview.AndesTextView
import com.mercadolibre.pipsearch.android.R
import com.mercadolibre.pipsearch.android.app.data.model.ItemDto
import com.mercadolibre.pipsearch.android.databinding.PipSearchAppMainListItemBinding

class MainViewHolder(private val binding: PipSearchAppMainListItemBinding) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        private const val TAG_MARKET = "supermarket_eligible"
        private const val TEXT_MARKET = "SUPERMERCADO"
        private const val AMOUNT_SYMBOL = "$"

        fun instance(layoutInflater: LayoutInflater, parent: ViewGroup) : MainViewHolder {
            val binding = PipSearchAppMainListItemBinding.inflate(layoutInflater, parent, false)
            return MainViewHolder(binding)
        }
    }

    fun bind(itemData: ItemDto) {
        binding.apply {
            image.setImageURI(itemData.thumbnail)
            iconPrice.text = AMOUNT_SYMBOL
            price.text = itemData.price.toString()
            title.text = itemData.title
            showMarketText(market, itemData.tags)
        }
    }

    private fun showMarketText(view: AndesTextView, tags: List<String>) {
        if (verifyMarketTag(tags)) {
            clearTextView(view)
            setMarketText(view)
            showButtonAddToCart()
            view.visibility = View.VISIBLE
        } else {
            view.visibility = View.GONE
        }
    }

    private fun setMarketText(view: AndesTextView) {
        view.append(TEXT_MARKET, AndesColor(R.color.andes_red_500))
    }

    private fun clearTextView(view: AndesTextView) {
        view.clear()
    }

    private fun verifyMarketTag(tags: List<String>) = tags.any { it == TAG_MARKET }

    private fun showButtonAddToCart() {
        binding.buttomAddToCart.visibility = View.VISIBLE
    }
}
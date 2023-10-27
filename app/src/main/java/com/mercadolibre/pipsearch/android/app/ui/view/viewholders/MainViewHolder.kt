package com.mercadolibre.pipsearch.android.app.ui.view.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mercadolibre.android.andesui.color.AndesColor
import com.mercadolibre.android.andesui.textview.AndesTextView
import com.mercadolibre.pipsearch.android.R
import com.mercadolibre.pipsearch.android.app.data.model.ItemDto
import com.mercadolibre.pipsearch.android.databinding.PipSearchAppMainListItemBinding

class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = PipSearchAppMainListItemBinding.bind(view)

    companion object {
        private const val TAG_MARKET = "supermarket_eligible"
        private const val TEXT_MARKET = "SUPERMERCADO"
        private const val AMOUNT_SYMBOL = "$"
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
            clearView(view)
            setMarketText(view)
            view.visibility = View.VISIBLE
        } else {
            view.visibility = View.GONE
        }
    }

    private fun setMarketText(view: AndesTextView) {
        view.append(TEXT_MARKET, AndesColor(R.color.andes_red_500))
    }

    private fun clearView(view: AndesTextView) {
        view.clear()
    }

    private fun verifyMarketTag(tags: List<String>) = tags.any { it == TAG_MARKET }
}
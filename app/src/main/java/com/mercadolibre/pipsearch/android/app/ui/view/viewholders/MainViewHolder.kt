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

class MainViewHolder(private val binding: PipSearchAppMainListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun instance(parent: ViewGroup): MainViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
                .inflate(R.layout.pip_search_app_main_list_item, parent, false)

            val binding = PipSearchAppMainListItemBinding.bind(layoutInflater)

            return MainViewHolder(binding)
        }

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
            view.append(TEXT_MARKET, AndesColor(R.color.andes_red_500))
            view.visibility = View.VISIBLE
        } else {
            view.visibility = View.GONE
        }
    }

    private fun clearView(view: AndesTextView) {
        view.clear()
    }

    private fun verifyMarketTag(tags: List<String>) = tags.any { it == TAG_MARKET }
}
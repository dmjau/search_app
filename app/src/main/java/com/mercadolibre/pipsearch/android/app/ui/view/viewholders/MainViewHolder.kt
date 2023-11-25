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

        fun instance(layoutInflater: LayoutInflater, parent: ViewGroup) : MainViewHolder {
            val binding = PipSearchAppMainListItemBinding.inflate(layoutInflater, parent, false)
            return MainViewHolder(binding)
        }
    }

    fun bind(
        itemData: ItemDto,
        onItemDataClickListener: (ItemDto) -> Unit
    ) {
        binding.apply {
            image.setImageURI(itemData.thumbnail)
            price.text = itemData.price.toString()
            title.text = itemData.title
            showMarketFields(itemData.tags)
            buttomAddToCart.setOnClickListener {
                onItemDataClickListener(itemData)
            }
        }
    }

    private fun showMarketFields(tags: List<String>) {
        if (verifyMarketTag(tags)) {
            showButtonAddToCart()
            showMarketText()
        } else {
            hideButtonAddToCart()
            hideMarketText()
        }
    }

    private fun verifyMarketTag(tags: List<String>) = tags.any { it == TAG_MARKET }

    private fun showButtonAddToCart() {
        binding.buttomAddToCart.visibility = View.VISIBLE
    }

    private fun hideButtonAddToCart() {
        binding.buttomAddToCart.visibility = View.GONE
    }

    private fun showMarketText() {
        binding.market.visibility = View.VISIBLE
    }

    private fun hideMarketText() {
        binding.market.visibility = View.GONE
    }
}

package com.mercadolibre.pipsearch.android.app.ui.view.viewholders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
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
        onItemDataClickListener: ((ItemDto) -> Unit)?
    ) {
        binding.apply {
            image.setImageURI(itemData.thumbnail)
            price.text = itemData.price.toString()
            title.text = itemData.title

            validateTagList(itemData.tags)

            buttomAddToCart.setOnClickListener {
                onItemDataClickListener?.let { listener ->
                    listener(itemData)
                }
            }
        }
    }

    private fun validateTagList(tags: List<String>?) {
        if (tags == null) {
            showButtonAddToCart()
        } else {
            showMarketFields(tags)
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

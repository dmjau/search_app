package com.mercadolibre.pipsearch.android.app.ui.view.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mercadolibre.pipsearch.android.app.data.model.ItemDto
import com.mercadolibre.pipsearch.android.databinding.PipSearchAppCartListItemBinding

class CartViewHolder(private val binding: PipSearchAppCartListItemBinding) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun instance(layoutInflater: LayoutInflater, parent: ViewGroup) : CartViewHolder {
            val binding = PipSearchAppCartListItemBinding.inflate(layoutInflater, parent, false)
            return CartViewHolder(binding)
        }
    }

    fun bind(itemData: ItemDto) {
        binding.apply {
            image.setImageURI(itemData.thumbnail)
            price.text = itemData.price.toString()
            title.text = itemData.title
        }
    }
}
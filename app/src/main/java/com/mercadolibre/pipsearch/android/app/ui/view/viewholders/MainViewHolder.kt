package com.mercadolibre.pipsearch.android.app.ui.view.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
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
    }

    fun bind(itemData: ItemDto) {
        binding.apply {
            image.setImageURI(itemData.thumbnail)
            price.text = itemData.price.toString()
            title.text = itemData.title
        }
    }
}
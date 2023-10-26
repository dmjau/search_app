package com.mercadolibre.pipsearch.android.app.ui.view.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mercadolibre.pipsearch.android.app.data.model.ItemDto
import com.mercadolibre.pipsearch.android.app.ui.view.viewholders.MainViewHolder

class MainAdapter(
    private var listOfItems: List<ItemDto>
) : RecyclerView.Adapter<MainViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder.instance(parent)
    }

    override fun getItemCount() = listOfItems.size

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val itemData = listOfItems[position]
        holder.bind(itemData)
    }
}
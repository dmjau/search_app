package com.mercadolibre.pipsearch.android.app.ui.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mercadolibre.pipsearch.android.app.data.model.ItemDto
import com.mercadolibre.pipsearch.android.app.ui.view.viewholders.MainViewHolder

class MainAdapter(private val onItemDataClickListener: (ItemDto) -> Unit) : RecyclerView.Adapter<MainViewHolder>() {

    private var listOfItems: List<ItemDto> = emptyList()

    fun setItems(items: List<ItemDto>) {
        this.listOfItems = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MainViewHolder.instance(layoutInflater, parent)
    }

    override fun getItemCount() = listOfItems.size

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val itemData = listOfItems[position]
        holder.bind(itemData, onItemDataClickListener)
    }
}

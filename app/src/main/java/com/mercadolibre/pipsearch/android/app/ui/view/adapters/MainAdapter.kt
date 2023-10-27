package com.mercadolibre.pipsearch.android.app.ui.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mercadolibre.pipsearch.android.R
import com.mercadolibre.pipsearch.android.app.data.model.ItemDto
import com.mercadolibre.pipsearch.android.app.ui.view.viewholders.MainViewHolder

class MainAdapter : RecyclerView.Adapter<MainViewHolder>() {

    private var listOfItems: List<ItemDto> = emptyList()

    fun setItems(items: List<ItemDto>) {
        this.listOfItems = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val layoutIngflater = LayoutInflater.from(parent.context).inflate(R.layout.pip_search_app_main_list_item, parent, false)
        return MainViewHolder(layoutIngflater)
    }

    override fun getItemCount() = listOfItems.size

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val itemData = listOfItems[position]
        holder.bind(itemData)
    }
}
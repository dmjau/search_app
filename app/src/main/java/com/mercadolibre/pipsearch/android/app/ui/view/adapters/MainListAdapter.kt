package com.mercadolibre.pipsearch.android.app.ui.view.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mercadolibre.pipsearch.android.app.data.model.ItemDto
import com.mercadolibre.pipsearch.android.app.ui.view.viewholders.MainViewHolder

class MainListAdapter(private val listItems: List<ItemDto>) : RecyclerView.Adapter<MainViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainViewHolder {
        return MainViewHolder.instance(parent)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val data = listItems[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int = listItems.size
}
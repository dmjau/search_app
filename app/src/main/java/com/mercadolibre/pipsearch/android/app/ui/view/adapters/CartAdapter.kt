package com.mercadolibre.pipsearch.android.app.ui.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mercadolibre.pipsearch.android.app.data.model.ItemDto
import com.mercadolibre.pipsearch.android.app.ui.view.viewholders.CartViewHolder

class CartAdapter(private val onItemDataClickListener: (ItemDto) -> Unit) : RecyclerView.Adapter<CartViewHolder>() {

    private var listOfItems: List<ItemDto> = emptyList()

    fun setItems(items: List<ItemDto>) {
        this.listOfItems = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CartViewHolder.instance(layoutInflater, parent)
    }

    override fun getItemCount() = listOfItems.size

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val itemData = listOfItems[position]
        holder.bind(itemData, onItemDataClickListener)
    }
}

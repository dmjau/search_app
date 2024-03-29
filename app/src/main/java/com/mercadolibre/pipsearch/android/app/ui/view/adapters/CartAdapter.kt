package com.mercadolibre.pipsearch.android.app.ui.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mercadolibre.pipsearch.android.app.data.model.ItemDto
import com.mercadolibre.pipsearch.android.app.ui.view.viewholders.CartViewHolder

class CartAdapter : RecyclerView.Adapter<CartViewHolder>() {

    private var listOfItems: List<ItemDto> = emptyList()
    private var onItemDataClickListener: ((ItemDto) -> Unit)? = null

    fun setItems(items: List<ItemDto>) {
        this.listOfItems = items
        notifyDataSetChanged()
    }

    fun setOnItemDataClickListener(listener: (ItemDto) -> Unit) {
        this.onItemDataClickListener = listener
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

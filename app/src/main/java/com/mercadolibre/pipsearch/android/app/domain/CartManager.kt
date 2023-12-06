package com.mercadolibre.pipsearch.android.app.domain

import com.mercadolibre.pipsearch.android.app.data.model.ItemDto

object CartManager : ManagerInterface {

    var itemsOnCart: MutableList<ItemDto> = mutableListOf()
        private set

    override fun addItemToCart(item: ItemDto) {
        itemsOnCart.add(item)
    }

    override fun removeItemFromCart(item: ItemDto) {
        itemsOnCart.remove(item)
    }

    override fun resetState() {
        itemsOnCart = mutableListOf()
    }
}

package com.mercadolibre.pipsearch.android.app.domain

import com.mercadolibre.pipsearch.android.app.data.model.ItemDto

interface ManagerInterface {

    /**
     * Method to add items on the cart.
     */
    fun addItemToCart(item: ItemDto)

    /**
     * Method to remove items on the cart.
     */
    fun removeItemFromCart(item: ItemDto)

    /**
     * Method to reset cart state, clean items list.
     */
    fun resetState()
}
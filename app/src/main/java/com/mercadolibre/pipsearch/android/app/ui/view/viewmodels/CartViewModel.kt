package com.mercadolibre.pipsearch.android.app.ui.view.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.mercadolibre.pipsearch.android.app.data.model.ItemDto
import com.mercadolibre.pipsearch.android.app.domain.CartManager

class CartViewModel : ViewModel() {

    private val cartManager = CartManager

    var selectedItems: MutableLiveData<MutableList<ItemDto>> = MutableLiveData(mutableListOf())
        private set

    fun updateItemsOnCart() {
        selectedItems.postValue(cartManager.itemsOnCart)
    }

    fun removeItemFromCart(item: ItemDto) {
        cartManager.removeItemFromCart(item)
        updateItemsOnCart()
    }
}

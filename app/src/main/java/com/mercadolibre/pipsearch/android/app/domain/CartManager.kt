package com.mercadolibre.pipsearch.android.app.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mercadolibre.pipsearch.android.app.data.model.ItemDto

class CartManager {

    companion object {
        private var instance: CartManager? = null
        fun getInstance(): CartManager {
            if (instance == null) {
                instance = CartManager()
            }
            return instance!!
        }
    }

    private val _itemsOnCart: MutableLiveData<MutableList<ItemDto>> = MutableLiveData()
    val itemsOnCart: LiveData<MutableList<ItemDto>> = _itemsOnCart

    fun updateItemList(itemList: MutableList<ItemDto>? = mutableListOf()) {
        _itemsOnCart.value = itemList
    }
}

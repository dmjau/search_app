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

    private val _itemsOnCart: MutableLiveData<MutableList<ItemDto>> = MutableLiveData(mutableListOf())
    val itemsOnCart: LiveData<MutableList<ItemDto>> = _itemsOnCart

    fun addItemToCart(item: ItemDto) {
        val currentList = _itemsOnCart.value.orEmpty().toMutableList()
        currentList.add(item)
        _itemsOnCart.postValue(currentList)
    }

    fun removeItemFromCart(item: ItemDto) {
        val currentList = _itemsOnCart.value.orEmpty().toMutableList()
        currentList.remove(item)
        _itemsOnCart.postValue(currentList)
    }

    fun resetState() {
        val currentList = mutableListOf<ItemDto>()
        _itemsOnCart.postValue(currentList)
    }
}

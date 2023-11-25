package com.mercadolibre.pipsearch.android.app.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mercadolibre.pipsearch.android.app.data.model.ItemDto

object CartManager {

    private var _itemsOnCart: MutableLiveData<MutableList<ItemDto>> = MutableLiveData(mutableListOf())
    val itemsOnCart: LiveData<MutableList<ItemDto>> = _itemsOnCart

    fun addItemToCart(item: ItemDto) {
        _itemsOnCart.value?.let {
            it.add(item)
            _itemsOnCart.postValue(it)
        }
    }

    fun removeItemFromCart(item: ItemDto) {
        _itemsOnCart.value?.let {
            it.remove(item)
            _itemsOnCart.postValue(it)
        }
    }

    fun resetState() {
        val currentList = mutableListOf<ItemDto>()
        _itemsOnCart.postValue(currentList)
    }
}

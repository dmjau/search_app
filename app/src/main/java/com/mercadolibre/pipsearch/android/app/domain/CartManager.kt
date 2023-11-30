package com.mercadolibre.pipsearch.android.app.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mercadolibre.pipsearch.android.app.data.model.ItemDto

object CartManager : ManagerInterface {

    private val _itemsOnCart: MutableLiveData<MutableList<ItemDto>> = MutableLiveData(mutableListOf())
    val itemsOnCart: LiveData<MutableList<ItemDto>> = _itemsOnCart

    override fun addItemToCart(item: ItemDto) {
        val currentList = _itemsOnCart.value!!
        currentList.add(item)
        _itemsOnCart.postValue(currentList)
    }

    override fun removeItemFromCart(item: ItemDto) {
        val currentList = _itemsOnCart.value!!
        currentList.remove(item)
        _itemsOnCart.postValue(currentList)
    }

    override fun resetState() {
        val currentList = mutableListOf<ItemDto>()
        _itemsOnCart.postValue(currentList)
    }
}

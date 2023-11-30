package com.mercadolibre.pipsearch.android.app.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mercadolibre.pipsearch.android.app.data.model.ItemDto

object CartManager : ManagerInterface {

    var itemsOnCart: MutableLiveData<MutableList<ItemDto>> = MutableLiveData(mutableListOf())
        private set

    override fun addItemToCart(item: ItemDto) {
        val currentList = itemsOnCart.value!!
        currentList.add(item)
        itemsOnCart.postValue(currentList)
    }

    override fun removeItemFromCart(item: ItemDto) {
        val currentList = itemsOnCart.value!!
        currentList.remove(item)
        itemsOnCart.postValue(currentList)
    }

    override fun resetState() {
        val currentList = mutableListOf<ItemDto>()
        itemsOnCart.postValue(currentList)
    }
}

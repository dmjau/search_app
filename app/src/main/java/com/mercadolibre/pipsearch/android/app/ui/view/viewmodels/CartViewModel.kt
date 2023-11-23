package com.mercadolibre.pipsearch.android.app.ui.view.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mercadolibre.pipsearch.android.app.data.model.ItemDto

class CartViewModel : ViewModel() {

    private val _itemsOnCart: MutableLiveData<List<ItemDto>> = MutableLiveData()

    val itemsOnCart: LiveData<List<ItemDto>> = _itemsOnCart

    fun updateItemList(itemList: List<ItemDto>) {
        _itemsOnCart.value = itemList
    }
}

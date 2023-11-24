package com.mercadolibre.pipsearch.android.app.ui.view.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mercadolibre.pipsearch.android.app.data.model.ItemDto

class CartViewModel : ViewModel() {

    private val _selectedItems: MutableLiveData<MutableList<ItemDto>> = MutableLiveData(mutableListOf())
    val selectedItems: LiveData<MutableList<ItemDto>> = _selectedItems

    fun removeItemFromCart(item: ItemDto) {
        val currentList = _selectedItems.value.orEmpty().toMutableList()
        currentList.remove(item)
        _selectedItems.postValue(currentList)
    }
}

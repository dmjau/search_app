package com.mercadolibre.pipsearch.android.app.ui.view.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mercadolibre.pipsearch.android.app.data.model.ItemDto

class CartViewModel : ViewModel() {

    private val _selectedItems: MutableLiveData<MutableList<ItemDto>> = MutableLiveData(mutableListOf())
    val selectedItems: LiveData<MutableList<ItemDto>> = _selectedItems

    fun removeItemFromCart(item: ItemDto) {
        _selectedItems.value?.let {
            it.remove(item)
            _selectedItems.postValue(it)
        }
    }

    fun setInitialSelectedItems(itemsList: MutableList<ItemDto>) {
        _selectedItems.postValue(itemsList)
    }
}

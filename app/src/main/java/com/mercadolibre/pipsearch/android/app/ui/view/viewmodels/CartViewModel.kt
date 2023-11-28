package com.mercadolibre.pipsearch.android.app.ui.view.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mercadolibre.pipsearch.android.app.data.model.ItemDto

class CartViewModel : ViewModel() {
    var selectedItems: MutableLiveData<MutableList<ItemDto>> = MutableLiveData(mutableListOf())
        private set

    fun removeItemFromCart(item: ItemDto) {
        selectedItems.value?.let {
            it.remove(item)
            selectedItems.postValue(it)
        }
    }
}

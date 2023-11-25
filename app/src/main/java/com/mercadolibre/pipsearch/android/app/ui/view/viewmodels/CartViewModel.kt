package com.mercadolibre.pipsearch.android.app.ui.view.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.mercadolibre.pipsearch.android.app.data.model.ItemDto
import com.mercadolibre.pipsearch.android.app.domain.CartManager

class CartViewModel : ViewModel() {

    private val cartManager = CartManager

    private val _selectedItems: MutableLiveData<MutableList<ItemDto>> = MutableLiveData(mutableListOf())
    val selectedItems: LiveData<MutableList<ItemDto>> = _selectedItems

    private val itemsObserver: Observer<MutableList<ItemDto>> = Observer { itemsOnCart ->
        _selectedItems.postValue(itemsOnCart)
    }

    init {
        cartManager.itemsOnCart.observeForever(itemsObserver)
    }

    fun removeItemFromCart(item: ItemDto) {
        _selectedItems.value?.let {
            cartManager.removeItemFromCart(item)
        }
    }

    override fun onCleared() {
        cartManager.itemsOnCart.removeObserver(itemsObserver)
        super.onCleared()
    }
}

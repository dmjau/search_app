package com.mercadolibre.pipsearch.android.app.ui.view.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.mercadolibre.pipsearch.android.app.data.model.ItemDto
import com.mercadolibre.pipsearch.android.app.domain.CartManager

class CartViewModel(private val cartManager: CartManager = CartManager) : ViewModel() {

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

    public override fun onCleared() {
        cartManager.itemsOnCart.removeObserver(itemsObserver)
        super.onCleared()
    }
}

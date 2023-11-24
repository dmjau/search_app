package com.mercadolibre.pipsearch.android.app.ui.view.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mercadolibre.android.restclient.extension.onError
import com.mercadolibre.android.restclient.extension.onSuccess
import com.mercadolibre.pipsearch.android.app.data.model.ItemDto
import com.mercadolibre.pipsearch.android.app.data.model.ScreenItemsDto
import com.mercadolibre.pipsearch.android.app.data.repository.SearchItemsRepository
import com.mercadolibre.pipsearch.android.app.domain.CartManager
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val repository = SearchItemsRepository()
    private val cartManager = CartManager.getInstance()

    private val _searchResults: MutableLiveData<ScreenItemsDto> = MutableLiveData()
    private val _exceptionOrErrorResult: MutableLiveData<String> = MutableLiveData()
    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _exceptionOrErrorResult.postValue(throwable.message)
    }

    private val _selectedItems: MutableLiveData<MutableList<ItemDto>> = MutableLiveData(mutableListOf())
    private val itemsObserver: Observer<MutableList<ItemDto>> = Observer { itemsOnCart ->
        _selectedItems.postValue(itemsOnCart)
    }


    val searchResults: LiveData<ScreenItemsDto> = _searchResults
    val exceptionOrErrorResult: LiveData<String> = _exceptionOrErrorResult
    val selectedItems: LiveData<MutableList<ItemDto>> = _selectedItems

    init {
        cartManager.itemsOnCart.observeForever(itemsObserver)
    }

    fun fetchResults(textToSearch: String) {
        viewModelScope.launch(coroutineExceptionHandler) {
            repository.getAll(textToSearch.lowercase())
                .onSuccess { screenData ->
                    _searchResults.postValue(screenData)
                }
                .onError { _, message ->
                    _exceptionOrErrorResult.postValue(message)
                }
        }
    }

    fun addItemToCart(item: ItemDto) {
        cartManager.addItemToCart(item)
    }

    override fun onCleared() {
        cartManager.itemsOnCart.removeObserver(itemsObserver)
        super.onCleared()
    }
}

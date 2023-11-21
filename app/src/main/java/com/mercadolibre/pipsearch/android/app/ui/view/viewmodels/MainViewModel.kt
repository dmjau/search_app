package com.mercadolibre.pipsearch.android.app.ui.view.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mercadolibre.android.restclient.extension.onError
import com.mercadolibre.android.restclient.extension.onSuccess
import com.mercadolibre.pipsearch.android.app.data.model.ItemDto
import com.mercadolibre.pipsearch.android.app.data.model.ScreenItemsDto
import com.mercadolibre.pipsearch.android.app.data.repository.SearchItemsRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val repository = SearchItemsRepository()
    private val _searchResults: MutableLiveData<ScreenItemsDto> = MutableLiveData()
    private val _exceptionOrErrorResult: MutableLiveData<String> = MutableLiveData()
    private val _itemsOnCart: MutableLiveData<MutableList<ItemDto>> = MutableLiveData()
    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _exceptionOrErrorResult.postValue(throwable.message)
    }

    val searchResults: LiveData<ScreenItemsDto> = _searchResults
    val exceptionOrErrorResult: LiveData<String> = _exceptionOrErrorResult
    val itemsOnCart: LiveData<MutableList<ItemDto>> = _itemsOnCart

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

    fun addItemsOnCart(item: ItemDto) {
        val currentList = _itemsOnCart.value ?: mutableListOf()
        currentList.add(item)
        _itemsOnCart.value = currentList
    }
}

package com.mercadolibre.pipsearch.android.app.ui.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mercadolibre.android.restclient.extension.onError
import com.mercadolibre.android.restclient.extension.onSuccess
import com.mercadolibre.pipsearch.android.app.data.model.ScreenItemsDto
import com.mercadolibre.pipsearch.android.app.data.repository.SearchItemsRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val repository = SearchItemsRepository()
    private val searchResults: MutableLiveData<ScreenItemsDto> = MutableLiveData()
    private val exceptionOrErrorResult: MutableLiveData<String> = MutableLiveData()

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        exceptionOrErrorResult.postValue(throwable.message)
    }

    fun getSearchResults(): LiveData<ScreenItemsDto> {
        return searchResults
    }

    fun getExceptionOrErrorResult(): LiveData<String> {
        return exceptionOrErrorResult
    }

    fun fetchResults() {
        viewModelScope.launch(coroutineExceptionHandler) {
            repository.getAll()
                .onSuccess {
                    searchResults.postValue(it)
                }
                .onError { _, message ->
                    exceptionOrErrorResult.postValue(message)
                }
        }
    }
}
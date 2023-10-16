package com.mercadolibre.pipsearch.android.app.ui.view

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mercadolibre.android.restclient.extension.onError
import com.mercadolibre.android.restclient.extension.onSuccess
import com.mercadolibre.pipsearch.android.app.data.model.ScreenItemsDto
import com.mercadolibre.pipsearch.android.app.data.repository.SearchItemsRepository
import com.mercadolibre.pipsearch.android.app.data.service.SearchItemsApiService
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class MainViewModel(
    searchItemsService: SearchItemsApiService
) : ViewModel() {

    private val repository = SearchItemsRepository(searchItemsService)
    private val searchResults: MutableLiveData<ScreenItemsDto> = MutableLiveData()
    private val errorResult: MutableLiveData<String> = MutableLiveData()
    private val exceptionResult: MutableLiveData<String> = MutableLiveData()

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.d("exception", throwable.message!!)
        exceptionResult.postValue(throwable.message)
    }

    fun getSearchResults(): LiveData<ScreenItemsDto> {
        return searchResults
    }

    fun getErrorResult(): LiveData<String> {
        return errorResult
    }

    fun getExceptionResult(): LiveData<String> {
        return exceptionResult
    }

    fun fetchResults() {
        viewModelScope.launch(coroutineExceptionHandler) {
            repository.getAll()
                .onSuccess {
                    searchResults.postValue(it)
                }
                .onError { _, message ->
                    errorResult.postValue(message)
                }
        }
    }
}
package com.mercadolibre.pipsearch.android.app.ui.view

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mercadolibre.android.restclient.extension.onError
import com.mercadolibre.android.restclient.extension.onException
import com.mercadolibre.android.restclient.extension.onSuccess
import com.mercadolibre.pipsearch.android.app.data.model.ItemDto
import com.mercadolibre.pipsearch.android.app.data.repository.SearchItemsRepository
import com.mercadolibre.pipsearch.android.app.data.service.RestClientApiHelper
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val searchItemsService = RestClientApiHelper.getRestClient()
    private val repository = SearchItemsRepository(searchItemsService)
    private val searchResults: MutableLiveData<List<ItemDto>> = MutableLiveData()

    private val coroutineExceptionHandler = CoroutineExceptionHandler { context, throwable ->
        Log.d("exception", throwable.message!!)
    }

    fun getSearchResults() : LiveData<List<ItemDto>> {
        return searchResults
    }

    fun fetchResults() {
        viewModelScope.launch(coroutineExceptionHandler) {
            repository.getAll()
                .onSuccess {

                }
                .onError { code, message ->

                }
                .onException {

                }
        }
    }
}
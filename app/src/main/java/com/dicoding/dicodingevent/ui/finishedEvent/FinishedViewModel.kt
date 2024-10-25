package com.dicoding.dicodingevent.ui.finishedEvent

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.dicodingevent.services.response.ListEventsItem
import com.dicoding.dicodingevent.services.retrofit.ApiConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FinishedViewModel : ViewModel() {

    private val _events = MutableLiveData<List<ListEventsItem>>()
    val events: LiveData<List<ListEventsItem>> get() = _events

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun loadEvents() {
        _isLoading.value = true

        viewModelScope.launch {
            try {
                // Memanggil API di IO thread (background) untuk performa lebih baik
                val response = withContext(Dispatchers.IO) {
                    ApiConfig.getApiService().getAvailableEvent(active = 0)
                }

                // Check response dan update live data di main thread
                if (response.isSuccessful) {
                    _events.value = response.body()?.listEvents ?: emptyList()
                } else {
                    _errorMessage.value = "Failed to load events"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun searchEvents(query: String) {
        _isLoading.value = true

        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    ApiConfig.getApiService().getSearchEvent(active = -1, q = query)
                }

                if (response.isSuccessful) {
                    _events.value = response.body()?.listEvents ?: emptyList()
                } else {
                    _errorMessage.value = "Failed to search events"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}

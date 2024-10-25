package com.dicoding.dicodingevent.ui.availableEvent

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.dicodingevent.services.response.AvailableResponse
import com.dicoding.dicodingevent.services.response.ListEventsItem
import com.dicoding.dicodingevent.services.retrofit.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AvailableViewModel : ViewModel() {

    private val _events = MutableLiveData<List<ListEventsItem>>()
    val events: LiveData<List<ListEventsItem>> get() = _events

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    init {
        loadEvents()
    }

    private fun loadEvents() {
        _isLoading.value = true

        // Menggunakan viewModelScope untuk coroutine
        viewModelScope.launch {
            try {
                val response = ApiConfig.getApiService().getAvailableEvent(active = 1)

                // Cek apakah response sukses
                if (response.isSuccessful) {
                    // Dapatkan daftar event dari body
                    val events = response.body()?.listEvents ?: emptyList()
                    _events.value = events
                } else {
                    _errorMessage.value = "Failed to load events"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error: ${e.message ?: "Unknown Error"}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}

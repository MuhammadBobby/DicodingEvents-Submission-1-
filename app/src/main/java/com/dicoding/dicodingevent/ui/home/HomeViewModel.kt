package com.dicoding.dicodingevent.ui.home

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

class HomeViewModel : ViewModel() {

    private val _eventSlider = MutableLiveData<List<ListEventsItem>>()
    val eventSlider: LiveData<List<ListEventsItem>> get() = _eventSlider

    private val _eventFinished = MutableLiveData<List<ListEventsItem>>()
    val eventFinished: LiveData<List<ListEventsItem>> get() = _eventFinished

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    init {
        finishedHome()
    }

    // Fungsi untuk memuat event slider
    fun loadEventSlider() {
        _isLoading.value = true

        viewModelScope.launch {
            try {
                val response = ApiConfig.getApiService().getAvailableEvent(active = 1)

                // Cek apakah response sukses
                if (response.isSuccessful) {
                    val events = response.body()?.listEvents?.take(5) ?: emptyList()
                    _eventSlider.value = events // Set the data to LiveData
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

    // Fungsi untuk memuat event finished
    private fun finishedHome() {
        _isLoading.value = true

        viewModelScope.launch {
            try {
                val response = ApiConfig.getApiService().getAvailableEvent(active = 0)

                // Cek apakah response sukses
                if (response.isSuccessful) {
                    val events = response.body()?.listEvents?.take(5) ?: emptyList()
                    _eventFinished.value = events // Set the data to LiveData
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


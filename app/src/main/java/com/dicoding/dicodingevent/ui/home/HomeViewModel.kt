package com.dicoding.dicodingevent.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.dicodingevent.services.response.AvailableResponse
import com.dicoding.dicodingevent.services.response.ListEventsItem
import com.dicoding.dicodingevent.services.retrofit.ApiConfig
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

    //fun slider
    fun loadEventSlider() {
        _isLoading.value = true

        val apiService = ApiConfig.getApiService().getAvailableEvent(active = 1)
        apiService.enqueue(object : Callback<AvailableResponse> {
            override fun onResponse(call: Call<AvailableResponse>, response: Response<AvailableResponse>) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    val events = response.body()?.listEvents?.take(5) ?: emptyList()
                    _eventSlider.value = events // Set the data to LiveData
                } else {
                    _isLoading.value = false
                    _errorMessage.value = "Failed to load events"
                }
            }

            override fun onFailure(call: Call<AvailableResponse>, t: Throwable) {
                _errorMessage.value = "Error: No Internet Connection"
            }
        })
    }

    //fun finished ryclcler
    private fun finishedHome() {
        _isLoading.value = true

        val apiService = ApiConfig.getApiService().getAvailableEvent(active = 0)
        apiService.enqueue(object : Callback<AvailableResponse> {
            override fun onResponse(call: Call<AvailableResponse>, response: Response<AvailableResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val events = response.body()?.listEvents?.take(5) ?: emptyList()
                    _eventFinished.value = events
                } else {
                    _errorMessage.value = "Failed to load events"
                }
            }

            override fun onFailure(call: Call<AvailableResponse>, t: Throwable) {
                _errorMessage.value = "Error: ${t.message}"
            }
        })
    }
}


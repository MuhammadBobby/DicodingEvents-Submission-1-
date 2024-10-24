package com.dicoding.dicodingevent.ui.detailEvent

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.dicodingevent.services.response.DetailResponse
import com.dicoding.dicodingevent.services.response.EventDetail
import com.dicoding.dicodingevent.services.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {
    private val _eventData = MutableLiveData<EventDetail?>()
    val eventData: LiveData<EventDetail?> get() = _eventData

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun loadDetailEvent(eventId: Int) {
        _isLoading.value = true

        val apiService = ApiConfig.getApiService().getDetailEvent(eventId)
        apiService.enqueue(object : Callback<DetailResponse> {
            override fun onResponse(call: Call<DetailResponse>, response: Response<DetailResponse>) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    val eventDetail = response.body()?.event
                    if (eventDetail != null) {
                        _eventData.value = eventDetail
                    } else {
                        _errorMessage.value = "Event detail is empty or null"
                    }
                } else {
                    _isLoading.value = false
                    _errorMessage.value = "Failed to load Detail Event"
                }
            }

            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                _errorMessage.value = "Error: No Internet Connection"
            }
        })
    }
}


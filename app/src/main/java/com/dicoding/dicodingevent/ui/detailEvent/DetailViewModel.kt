package com.dicoding.dicodingevent.ui.detailEvent

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.dicodingevent.services.response.DetailResponse
import com.dicoding.dicodingevent.services.response.EventDetail
import com.dicoding.dicodingevent.services.retrofit.ApiConfig
import kotlinx.coroutines.launch
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

    // Fungsi untuk memuat detail event
    fun loadDetailEvent(eventId: Int) {
        _isLoading.value = true

        viewModelScope.launch {
            try {
                val response = ApiConfig.getApiService().getDetailEvent(eventId)

                // Cek apakah response sukses
                if (response.isSuccessful) {
                    // Dapatkan detail event dari body
                    val eventDetail = response.body()?.event
                    if (eventDetail != null) {
                        _eventData.value = eventDetail
                    } else {
                        _errorMessage.value = "Event detail is empty or null"
                    }
                } else {
                    _errorMessage.value = "Failed to load Detail Event"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error: ${e.message ?: "Unknown Error"}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}


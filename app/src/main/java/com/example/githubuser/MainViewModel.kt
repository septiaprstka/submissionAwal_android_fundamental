package com.example.githubuser

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubuser.data.model.ResponseUserGithub
import com.example.githubuser.data.remote.ApiClient
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    val resultSuccess = MutableLiveData<MutableList<ResponseUserGithub.Item>>()
    val resultError = MutableLiveData<String>()
    val resultLoading = MutableLiveData<Boolean>()

    fun getUser() {
        viewModelScope.launch {
            flow {
                val response = ApiClient
                    .githubService
                    .getUserGithub()

                emit(response)
            }.onStart {
                resultLoading.value = true
            }.onCompletion {
                resultLoading.value = false
            }.catch {
                Log.e("Error", it.message.toString())
                it.printStackTrace()
                resultError.value = it.message.toString()
            }.collect {
                resultSuccess.value = it
            }
        }
    }
}
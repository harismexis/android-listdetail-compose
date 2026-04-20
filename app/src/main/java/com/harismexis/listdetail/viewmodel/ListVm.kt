package com.harismexis.listdetail.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

import com.harismexis.listdetail.api.Character
import com.harismexis.listdetail.repository.RemoteRepository
import com.harismexis.listdetail.repository.RemoteRepositoryImpl
import com.harismexis.listdetail.repository.Result

class ListVm(private val repo: RemoteRepository = RemoteRepositoryImpl()) : ViewModel() {

    private val _models = MutableStateFlow<List<Character>>(emptyList())
    val models: StateFlow<List<Character>> = _models.asStateFlow()

    private val _errors = MutableSharedFlow<String>(
        replay = 0,
        extraBufferCapacity = 1
    )
    val error = _errors.asSharedFlow()

    private fun emitError(error: String) {
        _errors.tryEmit(error)
    }

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun updateData() {
        _isLoading.value = true
        viewModelScope.launch {
            runCatching {
                val result: Result? = repo.getRemoteData()
                when (result) {
                    is Result.Success -> {
                        Log.d("ListVm", "success: ${result.response}")
                        _models.value = result.response.results ?: emptyList()
                    }

                    is Result.Failure -> {
                        Log.e("ListVm", "failure: ${result.error}")
                        emitError(result.error.message ?: "Unknown error")
                    }

                    else -> {
                        Log.e("ListVm", "unexpected result: $result")
                        emitError("Unexpected result")
                    }
                }
                _isLoading.value = false
            }.onFailure { error ->
                Log.e("ListVm", "failure: $error")
                emitError("Unexpected result")
                _isLoading.value = false
            }
        }
    }
}
package com.harismexis.listdetail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.harismexis.listdetail.data.api.Character
import com.harismexis.listdetail.data.repository.RemoteRepository
import com.harismexis.listdetail.data.repository.RemoteRepositoryImpl
import kotlinx.coroutines.flow.MutableSharedFlow

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// This ViewModel will override the actual one according to this post:
// https://stackoverflow.com/questions/77626980/mocking-viewmodels-for-ui-testing-in-android

class ListVm(private val repo: RemoteRepository = RemoteRepositoryImpl()) : ViewModel() {

    private val tag = "ListVm"

    private val _models = MutableStateFlow<List<Character>?>(emptyList())
    val models: StateFlow<List<Character>?> = _models.asStateFlow()

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errors = MutableSharedFlow<String>(
        replay = 0,
        extraBufferCapacity = 1
    )
    val error = _errors.asSharedFlow()

    fun updateData() {
        _isLoading.value = true
        viewModelScope.launch {
            _isLoading.value = false
        }
    }
}
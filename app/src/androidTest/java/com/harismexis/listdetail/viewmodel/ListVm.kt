package com.harismexis.listdetail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.harismexis.listdetail.api.Character
import com.harismexis.listdetail.repository.RemoteRepository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// This ViewModel will override the actual one according to this post:
// https://stackoverflow.com/questions/77626980/mocking-viewmodels-for-ui-testing-in-android

class ListVm(private val repo: RemoteRepository = RemoteRepository()) : ViewModel() {

    private val tag = "ListVm"

    private val _models = MutableStateFlow<List<Character>?>(null)
    val models: StateFlow<List<Character>?> = _models.asStateFlow()

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun updateData() {
        _isLoading.value = true
        viewModelScope.launch {
            _isLoading.value = false
        }
    }
}
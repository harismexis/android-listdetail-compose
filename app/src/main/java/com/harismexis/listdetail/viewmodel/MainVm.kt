package com.harismexis.listdetail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harismexis.listdetail.model.Model
import com.harismexis.listdetail.repository.RemoteRepository
import com.harismexis.listdetail.util.convertMillisToFormattedDate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainVm(private val repo: RemoteRepository = RemoteRepository()) : ViewModel() {

    private val _model = MutableStateFlow<Model?>(null)
    val model: StateFlow<Model?> = _model.asStateFlow()

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun updateData(date: Long? = null) {
        _isLoading.value = true
        val dateString: String? = if (date == null) null else convertMillisToFormattedDate(date)
        viewModelScope.launch {
            val response = repo.getRemoteData(dateString)
            _model.value = response
            _isLoading.value = false
        }
    }
}
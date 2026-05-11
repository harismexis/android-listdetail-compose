package com.harismexis.listdetail.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.harismexis.listdetail.api.Character

class DetailVm : ViewModel() {

    private var _item by mutableStateOf<Character?>(null)

    val item: Character?
        get() = _item

    fun setItem(character: Character?) {
        _item = character
    }
}
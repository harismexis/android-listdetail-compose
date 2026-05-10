package com.harismexis.listdetail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.harismexis.listdetail.paging.CharactersPagingSource

class ListVm(source: CharactersPagingSource = CharactersPagingSource()) : ViewModel() {

    val characters = source.getCharactersPager().flow.cachedIn(viewModelScope)
}
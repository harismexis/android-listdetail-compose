package com.harismexis.listdetail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.harismexis.listdetail.api.Character
import com.harismexis.listdetail.paging.CharactersPagingSource
import kotlinx.coroutines.flow.Flow

class ListVm(source: CharactersPagingSource = CharactersPagingSource()) : ViewModel() {

    val characters: Flow<PagingData<Character>> =
        source.getCharactersPager().flow.cachedIn(viewModelScope)
}
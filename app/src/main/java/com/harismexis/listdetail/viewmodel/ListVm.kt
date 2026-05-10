package com.harismexis.listdetail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.harismexis.listdetail.api.Character
import com.harismexis.listdetail.paging.CharactersPagingSource
import com.harismexis.listdetail.repository.RemoteRepository
import com.harismexis.listdetail.repository.RemoteRepositoryImpl

class ListVm(private val repo: RemoteRepository = RemoteRepositoryImpl()) : ViewModel() {

    val characters = getCharactersPager().flow.cachedIn(viewModelScope)

    fun getCharactersPager(): Pager<Int, Character> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 5,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                CharactersPagingSource(repo)
            }
        )
    }
}
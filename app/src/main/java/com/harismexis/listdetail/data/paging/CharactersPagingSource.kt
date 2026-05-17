package com.harismexis.listdetail.data.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.harismexis.listdetail.data.api.Character
import com.harismexis.listdetail.data.repository.RemoteRepository
import com.harismexis.listdetail.data.repository.RemoteRepositoryImpl
import com.harismexis.listdetail.data.repository.RemoteRepository.Result
import kotlin.coroutines.cancellation.CancellationException

class CharactersPagingSource(
    private val repo: RemoteRepository = RemoteRepositoryImpl()
) : PagingSource<Int, Character>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        return try {
            val page = params.key ?: 1
            when (val result = repo.getRemoteData(page)) {
                null -> LoadResult.Error(Throwable("Unknown error"))
                is Result.Failure -> LoadResult.Error(result.error)
                is Result.Success -> {
                    val response = result.response
                    LoadResult.Page(
                        data = response.results ?: emptyList(),
                        prevKey = if (page == 1) null else page - 1,
                        nextKey = if (response.info?.next == null) null else page + 1
                    )
                }
            }
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
//        return state.anchorPosition?.let { position ->
//            state.closestPageToPosition(position)?.prevKey?.plus(1)
//                ?: state.closestPageToPosition(position)?.nextKey?.minus(1)
//        }
        return null
    }

    fun getCharactersPager(): Pager<Int, Character> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 5,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { this }
        )
    }
}
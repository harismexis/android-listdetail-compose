package com.harismexis.listdetail.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.harismexis.listdetail.api.Character
import com.harismexis.listdetail.repository.RemoteRepository
import com.harismexis.listdetail.repository.Result

class CharactersPagingSource(
    private val api: RemoteRepository
) : PagingSource<Int, Character>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        return try {
            val page = params.key ?: 1
            val result = api.getRemoteData(page)
            if (result is Result.Failure) {
                return LoadResult.Error(result.error)
            }
            val response = (result as Result.Success).response
            LoadResult.Page(
                data = response.results ?: emptyList(),
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.info?.next == null) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(position)?.nextKey?.minus(1)
        }
    }
}
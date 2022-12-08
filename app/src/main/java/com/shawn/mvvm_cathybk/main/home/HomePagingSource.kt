package com.shawn.mvvm_cathybk.main.home

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.shawn.network.model.Attraction
import com.shawn.network.repository.AttractionRepository
import com.shawn.network.repository.BaseRepository

class HomePagingSource(val backend: AttractionRepository,
                       val query: String) : PagingSource<Int, Attraction>() {
    val mockName = listOf("aa", "bb", "cc")
    override fun getRefreshKey(state: PagingState<Int, Attraction>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Attraction> {
        return try {
            val nextPageNumber = params.key ?: 1
            val response = backend.service.getAttractions(query, nextPageNumber)
            LoadResult.Page(
                data = response.data,
                prevKey = null,
                nextKey = if (response.data.isEmpty()) null else nextPageNumber + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
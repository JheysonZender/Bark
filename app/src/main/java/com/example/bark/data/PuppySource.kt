package com.example.bark.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.bark.model.Puppy
import java.io.IOException

private const val STARTING_KEY = 0

class PuppySource: PagingSource<Int, Puppy>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Puppy> {
        val start = params.key ?: STARTING_KEY
        val range = start.until(start+params.loadSize)
        val PuppyList = DataProvider.puppyList

        return LoadResult.Page(
            data = PuppyList.map { puppy ->
                Puppy(
                    id = puppy.id,
                    title = puppy.title,
                    age = puppy.age,
                    sex = puppy.sex,
                    description = puppy.description,
                    puppyImageId = puppy.puppyImageId
                )
            },
            prevKey = when(start) {
                STARTING_KEY -> null
                else -> ensureValidKey(key = range.first - params.loadSize)
            },
            nextKey = range.last + 1
        )
    }

    override fun getRefreshKey(state: PagingState<Int, Puppy>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val article = state.closestItemToPosition(anchorPosition) ?: return null
        return ensureValidKey(key = article.id - (state.config.pageSize / 2))
    }

    private fun ensureValidKey(key: Int) = Math.max(STARTING_KEY, key)
}
package com.example.bark.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.bark.model.Puppy
import java.io.IOException

private const val STARTING_KEY = 0

class PuppySource: PagingSource<Int, Puppy>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Puppy> {
        val nextPage = params.key ?: 1
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
            prevKey = if (nextPage == 1) null else nextPage - 1,
            nextKey = if (PuppyList.isNotEmpty()) null else nextPage + 1
        )
    }

    override fun getRefreshKey(state: PagingState<Int, Puppy>): Int? {
        return state.anchorPosition
    }

}



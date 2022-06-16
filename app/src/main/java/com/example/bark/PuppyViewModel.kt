package com.example.bark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.bark.data.PuppySource
import com.example.bark.model.Puppy
import kotlinx.coroutines.flow.Flow

class PuppyViewModel : ViewModel() {
    val puppy: Flow<PagingData<Puppy>> = Pager(PagingConfig(pageSize = 6)) {
        PuppySource()
    }.flow.cachedIn(viewModelScope)
}
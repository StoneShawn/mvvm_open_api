package com.shawn.mvvm_cathybk.main.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.shawn.network.model.Attraction
import com.shawn.network.repository.AttractionRepository
import kotlinx.coroutines.flow.Flow
import org.intellij.lang.annotations.Language

class HomeViewModel(private val repository: AttractionRepository) : ViewModel() {

    fun getAttractions(language: String): Flow<PagingData<Attraction>> {
        return Pager(
            PagingConfig(pageSize = 27)
        ) { HomePagingSource(repository, language) }
            .flow
            .cachedIn(viewModelScope)
    }


}
package com.dicoding.animelist.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.animelist.data.local.AnimeFavorite
import com.dicoding.animelist.data.repository.AnimeRepository
import com.dicoding.animelist.ui.utils.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: AnimeRepository) : ViewModel() {
    private val _result: MutableStateFlow<Result<AnimeFavorite>> = MutableStateFlow(Result.Loading)
    val result = _result.asStateFlow()

    fun getFavoriteAnimeById(id: Int) {
        viewModelScope.launch {
            _result.value = Result.Loading
            _result.value = Result.Success(repository.getAnimeFavoriteById(id))
        }
    }

    fun updateHighlight(id: Int) {
        viewModelScope.launch {
            repository.updateAnimeFavorite(id)
        }
    }
}
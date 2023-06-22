package com.dicoding.animelist.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.animelist.ui.utils.Result
import com.dicoding.animelist.data.local.AnimeFavorite
import com.dicoding.animelist.data.repository.AnimeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: AnimeRepository): ViewModel() {
    private val _result: MutableStateFlow<Result<List<AnimeFavorite>>> =
        MutableStateFlow(Result.Loading)
    val result = _result.asStateFlow()

    private val _query = MutableStateFlow("")
    val query = _query.asStateFlow()

    private val _noMatch = MutableStateFlow(false)
    val noMatch = _noMatch.asStateFlow()

    fun getAllAnimeFavorites() {
        viewModelScope.launch {
            repository.getAllAnimeFavorite()
                .catch { exception ->
                    _result.value = Result.Error(exception.message.toString())
                }
                .collect { animeFavorite ->
                    _result.value = Result.Success(animeFavorite)
                }
        }
    }

    fun searchFavoriteAnime(query: String) {
        _query.value = query
        viewModelScope.launch {
            repository.searchAnime(query)
                .catch { exception ->
                    _result.value = Result.Error(exception.message.toString())
                }
                .collect { animeFavorite ->
                    if (animeFavorite.isEmpty()) {
                        _noMatch.value = true
                        _result.value = Result.Success(emptyList())
                    } else {
                        _noMatch.value = false
                        _result.value = Result.Success(animeFavorite)
                    }
                }
        }
    }
}
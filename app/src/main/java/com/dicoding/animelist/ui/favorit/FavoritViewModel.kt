package com.dicoding.animelist.ui.favorit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.animelist.data.local.AnimeFavorite
import com.dicoding.animelist.data.repository.AnimeRepository
import com.dicoding.animelist.ui.utils.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class FavoritViewModel(
    private val repository: AnimeRepository
) : ViewModel() {
    private val _result: MutableStateFlow<Result<List<AnimeFavorite>>> = MutableStateFlow(Result.Loading)
    val result = _result.asStateFlow()

    private val _noMatch = MutableStateFlow(false)
    val noMatch = _noMatch.asStateFlow()

    fun getHighlightPlayer() {
        viewModelScope.launch {
            repository.getFavoriteAnime()
                .catch {
                    _result.value = Result.Error(it.message.toString())
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
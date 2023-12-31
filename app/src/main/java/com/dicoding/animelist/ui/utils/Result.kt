package com.dicoding.animelist.ui.utils

sealed class Result<out T: Any?> {
    object Loading : Result<Nothing>()
    data class Success<out T: Any>(val data: T) : Result<T>()
    data class Error(val error: String) : Result<Nothing>()
}
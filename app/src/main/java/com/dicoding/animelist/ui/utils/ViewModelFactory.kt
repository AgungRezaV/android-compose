package com.dicoding.animelist.ui.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.animelist.ui.detail.DetailViewModel
import com.dicoding.animelist.ui.favorit.FavoritViewModel
import com.dicoding.animelist.ui.home.HomeViewModel
import com.dicoding.animelist.ui.utils.Injection.provideRepository

class ViewModelFactory : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when (modelClass) {
            HomeViewModel::class.java -> HomeViewModel(provideRepository())
            DetailViewModel::class.java -> DetailViewModel(provideRepository())
            FavoritViewModel::class.java -> FavoritViewModel(provideRepository())
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        } as T
}
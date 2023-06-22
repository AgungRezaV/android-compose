package com.dicoding.animelist.ui.utils

import com.dicoding.animelist.data.repository.AnimeRepository

object Injection {
    fun provideRepository(): AnimeRepository {
        return AnimeRepository.getInstance()
    }
}
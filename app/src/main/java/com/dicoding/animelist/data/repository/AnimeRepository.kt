package com.dicoding.animelist.data.repository

import com.dicoding.animelist.data.local.AnimeData
import com.dicoding.animelist.data.local.AnimeFavorite
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class AnimeRepository {
    private val animeFavorite = mutableListOf<AnimeFavorite>()

    init {
        if (animeFavorite.isEmpty()) {
            AnimeData.dummyData.forEach {
                animeFavorite.add(AnimeFavorite(it))
            }
        }
    }

    fun getAllAnimeFavorite(): Flow<List<AnimeFavorite>> {
        return flowOf(animeFavorite)
    }

    fun getAnimeFavoriteById(id: Int): AnimeFavorite {
        return animeFavorite.first {
            it.anime.id == id
        }
    }

    fun updateAnimeFavorite(id: Int): Flow<Boolean> {
        val index = animeFavorite.indexOfFirst { it.anime.id == id }

        val result = if (index >= 0) {
            val anime = animeFavorite[index]
            anime.favorite = !anime.favorite
            true
        } else {
            false
        }
        return flowOf(result)
    }

    fun getFavoriteAnime(): Flow<List<AnimeFavorite>> {
        return getAllAnimeFavorite().map {
            it.filter { anime ->
                anime.favorite
            }
        }
    }

    fun searchAnime(query: String): Flow<List<AnimeFavorite>> {
        return getAllAnimeFavorite().map {
            it.filter { anime ->
                anime.anime.title.contains(query, true) || anime.anime.subTitle.contains(query, true)
            }
        }
    }

    companion object {
        @Volatile
        private var instance: AnimeRepository? = null

        fun getInstance(): AnimeRepository =
            instance ?: synchronized(this) {
                AnimeRepository().apply {
                    instance = this
                }
            }
    }
}
package com.dicoding.animelist.data.local

data class Anime(
    val id: Int,
    val title: String,
    val subTitle: String,
    val type: String,
    val aired: String,
    val studio: String,
    val genre: String,
    val imageUrl: String
)
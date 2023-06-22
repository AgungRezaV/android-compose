package com.dicoding.animelist.ui.detail

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dicoding.animelist.ui.component.DetailItem
import com.dicoding.animelist.ui.utils.ViewModelFactory
import com.dicoding.animelist.ui.utils.Result.*
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun Detail(
    modifier: Modifier = Modifier,
    id: Int,
    viewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory()
    ),
    navigateBack: () -> Unit,
    navigateToHighlight: () -> Unit
) {
    val systemUiController = rememberSystemUiController()
    val statusBarColor = MaterialTheme.colorScheme.background
    val isDark = isSystemInDarkTheme()
    SideEffect {
        systemUiController.setSystemBarsColor(
            color = statusBarColor,
            darkIcons = !isDark
        )
    }

    viewModel.result.collectAsState(initial = Loading).value.let { result ->
        when (result) {
            is Loading -> {
                viewModel.getFavoriteAnimeById(id)
            }
            is Success -> {
                val favoriteAnime = result.data
                val anime = favoriteAnime.anime
                DetailContent(
                    modifier = modifier,
                    id = anime.id,
                    title = anime.title,
                    subTitle = anime.subTitle,
                    aired = anime.aired,
                    studio = anime.studio,
                    genre = anime.genre,
                    imageUrl = anime.imageUrl,
                    favorite = favoriteAnime.favorite,
                    onBackClick = navigateBack,
                    onUpdateHighlight = {
                        viewModel.updateHighlight(id)
                        navigateToHighlight()
                    }
                )
            }
            is Error -> {}
        }
    }
}

@Composable
fun DetailContent(
    modifier: Modifier = Modifier,
    id: Int,
    title: String,
    subTitle: String,
    aired: String,
    studio: String,
    genre: String,
    imageUrl: String,
    favorite: Boolean,
    onBackClick: () -> Unit,
    onUpdateHighlight: (Int) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        DetailItem(
            id = id,
            title = title,
            subTitle = subTitle,
            aired = aired,
            studio = studio,
            genre = genre,
            imageUrl = imageUrl,
            favorite = favorite,
            onBackClick = onBackClick,
            onUpdateHighlight = onUpdateHighlight
        )
    }
}
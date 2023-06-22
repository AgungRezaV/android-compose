package com.dicoding.animelist.ui.favorit

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dicoding.animelist.R
import com.dicoding.animelist.data.local.AnimeFavorite
import com.dicoding.animelist.ui.component.AnimeCardList
import com.dicoding.animelist.ui.utils.Result
import com.dicoding.animelist.ui.utils.ViewModelFactory
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun HighlightScreen(
    modifier: Modifier = Modifier,
    viewModel: FavoritViewModel = viewModel(
        factory = ViewModelFactory()
    ),
    navigateToDetail: (Int) -> Unit,
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

    viewModel.result.collectAsState(initial = Result.Loading).value.let { resultData ->
        when (resultData) {
            is Result.Loading -> {
                viewModel.getHighlightPlayer()
            }

            is Result.Success-> {
                AnimeFavoriteList(
                    modifier = modifier,
                    animeFavorite = resultData.data,
                    noData = viewModel.noMatch.value,
                    navigateToDetail = navigateToDetail,
                )
            }

            is Result.Error -> {}
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AnimeFavoriteList(
    modifier: Modifier = Modifier,
    animeFavorite: List<AnimeFavorite>,
    noData: Boolean,
    navigateToDetail: (Int) -> Unit,
) {
    if (noData) {
        Box(
            modifier = modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Text(text = stringResource(R.string.no_data))
        }
    } else {
        Column {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = modifier,
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(
                    items = animeFavorite,
                    key = { it.anime.id }
                ) { data ->
                    AnimeCardList(
                        imageUrl = data.anime.imageUrl,
                        title = data.anime.title,
                        studio = data.anime.studio,
                        genre = data.anime.genre,
                        modifier = Modifier
                            .clickable {
                                navigateToDetail(data.anime.id)
                            }
                            .animateItemPlacement(tween(durationMillis = 100))
                    )
                }
            }
        }
    }
}
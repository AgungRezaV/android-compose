package com.dicoding.animelist.ui.home

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import com.dicoding.animelist.ui.component.AnimeFavoriteItem
import com.dicoding.animelist.ui.utils.ViewModelFactory
import com.dicoding.animelist.ui.utils.Result.*
import com.dicoding.animelist.ui.component.SearchBar
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory()
    ),
    navigateToDetail: (Int) -> Unit,
) {
    val systemUiController = rememberSystemUiController()
    val statusBarColor = MaterialTheme.colorScheme.primary
    SideEffect {
        systemUiController.setSystemBarsColor(
            color = statusBarColor,
            darkIcons = false
        )
    }

    viewModel.result.collectAsState(initial = Loading).value.let { result ->
        when (result) {
            is Loading -> {
                viewModel.getAllAnimeFavorites()
            }

            is Success -> {
                AnimeList(
                    modifier = modifier,
                    animeFavorite = result.data,
                    query = viewModel.query.value,
                    noMatch = viewModel.noMatch.value,
                    detail = navigateToDetail,
                    searchAnime = viewModel::searchFavoriteAnime,
                )
            }

            is Error -> {}
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AnimeList(
    modifier: Modifier = Modifier,
    animeFavorite: List<AnimeFavorite>,
    query: String,
    noMatch: Boolean,
    detail: (Int) -> Unit,
    searchAnime: (String) -> Unit,
) {
    Column {
        SearchBar(
            query = query,
            onQueryChange = {
                searchAnime(it)
            },
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.primary,
                )
        )
        if (noMatch) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text(text = stringResource(R.string.no_data))
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = modifier
            ) {
                items(
                    items = animeFavorite,
                    key = { it.anime.id }
                ) { data ->
                    AnimeFavoriteItem(
                        imageUrl = data.anime.imageUrl,
                        title = data.anime.title,
                        studio = data.anime.studio,
                        genre = data.anime.genre,
                        modifier = Modifier
                            .clickable {
                                detail(data.anime.id)
                            }
                            .animateItemPlacement(tween(durationMillis = 100))
                    )
                }
            }
        }
    }
}
package com.sano.ideallist.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sano.ideallist.viewModel.MonstersListViewModel

@Composable
fun FavouritesScreen(viewModel: MonstersListViewModel, navController: NavController) {
    val listState by viewModel.favouriteMonsters.observeAsState()

    LazyColumn(contentPadding = PaddingValues(8.dp)) {
        items(listState ?: emptyList())
        {
            MonsterCard(
                data = it,
                viewModel = viewModel,
                navController = navController,
                isFavourite = true
            )
        }
    }
}
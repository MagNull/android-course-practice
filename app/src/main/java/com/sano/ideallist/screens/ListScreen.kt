package com.sano.ideallist.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.sano.ideallist.datapersistance.FilterSettings
import com.sano.ideallist.datapersistance.filterWithSettings
import com.sano.ideallist.repository.MonsterData
import com.sano.ideallist.viewModel.MonstersListViewModel

@Composable
fun ListScreen(viewModel: MonstersListViewModel, navController: NavController) {
    val listState by viewModel.monstersList.collectAsStateWithLifecycle()
    val filterSettings by viewModel.filterSettings.observeAsState()
    val favouriteMonsters by viewModel.favouriteMonsters.observeAsState()

    LazyColumn(contentPadding = PaddingValues(8.dp)) {
        items(listState.filterWithSettings(filterSettings ?: FilterSettings()))
        {
            MonsterCard(
                data = it,
                viewModel = viewModel,
                navController = navController,
                isFavourite = favouriteMonsters?.contains(it) ?: false
            )
        }
    }
}

@Composable
fun MonsterCard(
    data: MonsterData,
    isFavourite: Boolean,
    viewModel: MonstersListViewModel,
    navController: NavController
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(128.dp)
            .padding(bottom = 8.dp)
            .clickable { navController.navigate(Screen.DetailsScreen.route + "/${data.name}") }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            val defaultMonsterIcon = rememberVectorPainter(Icons.Filled.AccountBox)
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://www.dnd5eapi.co${data.imageSrc}")
                    .crossfade(true)
                    .build(),
                placeholder = defaultMonsterIcon,
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            )
            Box(
                modifier = Modifier
                    .weight(2f)
                    .fillMaxHeight()
            ) {
                val starOutlinedIcon = rememberVectorPainter(Icons.Outlined.StarOutline)
                val starFilledIcon = rememberVectorPainter(Icons.Outlined.Star)
                Text(
                    text = data.name,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(alignment = Alignment.Center)
                )
                Image(
                    painter = if (isFavourite) starFilledIcon else starOutlinedIcon,
                    contentDescription = "",
                    Modifier
                        .align(alignment = Alignment.TopEnd)
                        .clickable {
                            if (isFavourite)
                                viewModel.deleteFavouriteMonster(data)
                            else
                                viewModel.insertFavouriteMonster(data)
                        }
                )

            }
        }
    }
}

@Preview
@Composable
fun ListScreenPreview() {
}
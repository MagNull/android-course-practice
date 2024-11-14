package com.sano.ideallist.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
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
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.sano.ideallist.viewModel.MonstersListViewModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun ListScreen(viewModel: MonstersListViewModel, navController: NavController) {
    val listState by viewModel.viewState.collectAsStateWithLifecycle()
    LazyColumn(contentPadding = PaddingValues(8.dp)) {
        items(listState)
        {
            MonsterCard(it.imageSrc, it.name, navController)
        }
    }
}

@Composable
fun MonsterCard(imageSrc: String, name: String, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(128.dp)
            .padding(bottom = 8.dp)
            .clickable { navController.navigate(Screen.DetailsScreen.route + "/$name") }
    ) {
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start) {
            val defaultMonsterIcon = rememberVectorPainter(Icons.Filled.AccountBox)
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://www.dnd5eapi.co$imageSrc")
                    .crossfade(true)
                    .build(),
                placeholder = defaultMonsterIcon,
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            )
            Text(
                text = name,
                fontSize = 24.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2f),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
fun ListScreenPreview() {
}
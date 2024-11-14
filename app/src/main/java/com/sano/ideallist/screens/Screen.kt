package com.sano.ideallist.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val title : String,
    val route : String,
    val icon : ImageVector? = null
) {
    data object HomeScreen : Screen(
        title = "Home",
        route = "home_screen",
        icon = Icons.Outlined.Home
    )

    data object ListScreen : Screen(
        title = "List",
        route = "list_screen",
        icon = Icons.AutoMirrored.Outlined.List
    )

    data object FavoritesScreen : Screen(
        title = "Favorites",
        route = "favorites_screen",
        icon = Icons.Outlined.Favorite
    )

    data object DetailsScreen : Screen(
        title = "Details",
        route = "detail_screen"
    )
}
package com.sano.ideallist.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sano.ideallist.ui.theme.IDealListTheme
import com.sano.ideallist.viewModel.MonstersListViewModel
import org.koin.androidx.compose.koinViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Main() {
    IDealListTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            val monstersListViewModel = koinViewModel<MonstersListViewModel>()

            val bottomItems = listOf(Screen.HomeScreen, Screen.ListScreen, Screen.FavoritesScreen)
            val navController = rememberNavController()

            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            val showBottomBar = when {
                currentRoute == null -> true
                currentRoute.startsWith(Screen.DetailsScreen.route) -> false
                else -> true
            }

            val screens = listOf(
                Screen.HomeScreen,
                Screen.ListScreen,
                Screen.FavoritesScreen,
                Screen.DetailsScreen
            )

            val currentScreen = screens.find {
                navBackStackEntry?.destination?.route?.startsWith(it.route) ?: false
            }
                ?: Screen.HomeScreen

            Scaffold(modifier = Modifier.fillMaxSize(),
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                text = currentScreen.title,
                                fontSize = 24.sp
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = { navController.navigateUp() }) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                                    contentDescription = "Back"
                                )
                            }
                        }
                    )
                },
                bottomBar = {
                    if (showBottomBar) {
                        NavigationBar {
                            bottomItems.forEach { screen ->
                                NavigationBarItem(
                                    selected = false,
                                    onClick = {
                                        navController.navigate(screen.route)
                                    },
                                    label = { Text(screen.title) },
                                    icon = {
                                        Icon(
                                            imageVector = screen.icon ?: Icons.Outlined.Warning,
                                            contentDescription = screen.title
                                        )
                                    }
                                )
                            }
                        }

                    }
                }) { innerPadding ->
                NavHost(
                    navController = navController,
                    startDestination = Screen.HomeScreen.route,
                    modifier =
                    Modifier.padding(innerPadding)
                ) {
                    composable(Screen.HomeScreen.route) { HomeScreen() }
                    composable(Screen.ListScreen.route) {
                        ListScreen(
                            monstersListViewModel,
                            navController
                        )
                    }
                    composable(route = Screen.DetailsScreen.route + "/{name}",
                        arguments = listOf(
                            navArgument("name")
                            {
                                type = NavType.StringType
                                defaultValue = "None"
                                nullable = false
                            }
                        )) { entry ->
                        val monstersList by monstersListViewModel.viewState.collectAsStateWithLifecycle()
                        DetailScreen(monstersList.first {
                            it.name == entry.arguments?.getString(
                                "name"
                            )
                        })
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Main()
}
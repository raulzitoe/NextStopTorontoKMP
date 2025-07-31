package com.raulvieira.nextstoptoronto.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.NearMe
import androidx.compose.material.icons.filled.Public
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.raulvieira.nextstoptoronto.ui.home.HomeScreen
import nextstoptoronto.composeapp.generated.resources.Res
import nextstoptoronto.composeapp.generated.resources.favorites
import nextstoptoronto.composeapp.generated.resources.home
import nextstoptoronto.composeapp.generated.resources.map_name
import nextstoptoronto.composeapp.generated.resources.near_me
import org.jetbrains.compose.resources.stringResource

@Composable
fun NextStopTorontoNavHost(
    navController: NavHostController = rememberNavController()
) {
    val bottomBarItems = listOf(
        listOf(HomeScreen, Icons.Filled.Home, stringResource(Res.string.home)),
        listOf(MapScreen, Icons.Filled.Public, stringResource(Res.string.map_name)),
        listOf(NearMeScreen, Icons.Filled.NearMe, stringResource(Res.string.near_me)),
        listOf(
            FavoritesScreen,
            Icons.Filled.Favorite,
            stringResource(Res.string.favorites)
        )
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                bottomBarItems.forEach { item ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                (item.component2() as ImageVector),
                                contentDescription = null
                            )
                        },
                        label = { Text(item.component3() as String) },
                        selected = currentDestination?.hierarchy?.any { it.route == item.component1() } == true,
                        onClick = {
                            navController.navigate(item.component1()) {
                                popUpTo(navController.graph.findStartDestination().route.orEmpty()) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = HomeScreen,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<HomeScreen> {
                HomeScreen { navController.navigate(MapScreen) }
            }

            composable<MapScreen> {
                Text("Map Screen")
            }

            composable<NearMeScreen> {
                Text("Near Me Screen")
            }

            composable<FavoritesScreen> {
                Text("Favorites Screen")
            }
        }
    }
}
package com.github.max_person.hammersystems.test.composables

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun NavigationScreens(navController: NavHostController, modifier: Modifier){
    NavHost(navController = navController, startDestination = "mainMenu", modifier = modifier){
        composable("mainMenu"){

        }
        composable("profile"){
            Text("PROFILE SCREEN NOT IMPLEMENTED")
        }
        composable("cart"){
            Text("CART SCREEN NOT IMPLEMENTED")
        }
    }
}

sealed class NavigationScreen(
    val id: String,
    val name: String,
    val icon: ImageVector,
){
    object MainMenu: NavigationScreen(
        "mainMenu",
        "Menu",
        Icons.Default.Fastfood,
    )
    object Profile: NavigationScreen(
        "profile",
        "Profile",
        Icons.Default.Person,
    )
    object Cart: NavigationScreen(
        "cart",
        "Cart",
        Icons.Default.ShoppingCart,
    )
}

@Composable
fun FoodNavigationBar(navController: NavHostController){
    val screens = listOf(NavigationScreen.MainMenu, NavigationScreen.Profile, NavigationScreen.Cart)
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar {
        screens.forEach {screen ->
            NavigationBarItem(
                icon = { Icon(screen.icon, "Navigation Icon") },
                label = { Text(screen.name)},
                selected = currentDestination?.hierarchy?.any{it.route == screen.id} == true,
                onClick = { navController.navigate(screen.id)}
            )
        }
    }
}
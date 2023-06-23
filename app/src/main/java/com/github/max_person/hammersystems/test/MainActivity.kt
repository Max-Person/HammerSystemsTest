package com.github.max_person.hammersystems.test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.github.max_person.hammersystems.test.composables.FoodNavigationBar
import com.github.max_person.hammersystems.test.composables.NavigationScreens
import com.github.max_person.hammersystems.test.ui.theme.HammerSystemsTestTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HammerSystemsTestTheme {
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = { FoodNavigationBar(navController = navController) },
                ){ padding ->
                    NavigationScreens(navController = navController, modifier = Modifier.padding(padding))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    HammerSystemsTestTheme {
        val navController = rememberNavController()
        Scaffold(
            bottomBar = { FoodNavigationBar(navController = navController) },
        ){ padding ->
            NavigationScreens(navController = navController, modifier = Modifier.padding(padding))
        }
    }
}
package com.example.eighteighttwod

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.eighteighttwod.ui.navigation.BottomNavItems
import com.example.eighteighttwod.ui.navigation.NavGraph
import com.example.eighteighttwod.ui.theme.EighteighttwodTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        enableEdgeToEdge()
        setContent {
            EighteighttwodTheme {
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = {
                        BottomBar(navController)
                    },
                    modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val padding = innerPadding
                    Box(modifier = Modifier.padding(innerPadding)){
                        NavGraph(
                            navController = navController,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BottomBar(navController: NavHostController){
    val items = listOf(
        BottomNavItems.Home,
        BottomNavItems.Omen,
        BottomNavItems.Dream,
        BottomNavItems.Lucky,
        BottomNavItems.Other
    )

    NavigationBar {
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
        items.forEach { items ->
            NavigationBarItem(
                selected = currentRoute == items.route,
                onClick = {navController.navigate(items.route)},
                icon = {Icon(
                    imageVector = if (currentRoute == items.route) items.selectedIcon else items.unselectedIcon,
                    contentDescription = null,
                )},
                label = {Text(text = items.title,
                    textAlign = TextAlign.Center)},
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color(0xFF6585D1),
                    selectedIconColor = Color.White,
                    selectedTextColor = Color(0xFF6585D1),
                    unselectedIconColor =Color.Gray,
                    unselectedTextColor =Color.Gray
                )
            )
        }
    }
}
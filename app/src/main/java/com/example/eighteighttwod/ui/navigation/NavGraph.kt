package com.example.eighteighttwod.ui.navigation

import androidx.compose.runtime.Composable

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.eighteighttwod.ui.screen.dream.DreamScreen
import com.example.eighteighttwod.ui.screen.twoDhistory.HistoryScreen
import com.example.eighteighttwod.ui.screen.home.HomeScreen
import com.example.eighteighttwod.ui.screen.lucky.LuckyScreen
import com.example.eighteighttwod.ui.screen.omen.OmenScreen
import com.example.eighteighttwod.ui.screen.other.OtherScreen

@Composable
fun NavGraph(navController: NavHostController){
    NavHost (
        navController = navController,
        startDestination = BottomNavItems.Home.route
    ){
        composable(route = BottomNavItems.Home.route){ HomeScreen() }
        composable(route = BottomNavItems.Omen.route){ OmenScreen() }
        composable(route = BottomNavItems.History.route){ HistoryScreen() }
        composable(route = BottomNavItems.Dream.route){ DreamScreen() }
        composable(route = BottomNavItems.Lucky.route){ LuckyScreen() }
        composable(route = BottomNavItems.Other.route){ OtherScreen() }
    }
}
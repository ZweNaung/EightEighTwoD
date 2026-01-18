package com.example.eighteighttwod.ui.screen.other

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.eighteighttwod.ui.navigation.BottomNavItems
import com.example.eighteighttwod.ui.screen.twoDhistory.HistoryScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun OtherScreen(
    navController: NavController
){
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row() {
            Button(
                onClick = {
                    navController.navigate(BottomNavItems.History.route)
                }
            ) {
                Text("History")
            }
        }
    }
}
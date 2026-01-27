package com.example.eighteighttwod.ui.screen.other

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
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
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {
                    navController.navigate(BottomNavItems.History.route)
                }
            ) {
                Text("၂လုံး မှတ်တမ်း")
            }

            Button(
                onClick = {
                    navController.navigate("threeD_screen")
                }
            ) {
                Text("၃လုံး မှတ်တမ်း")
            }
        }
        Spacer(modifier = Modifier.padding(10.dp))
        HorizontalDivider(modifier = Modifier, thickness = 1.dp, color = Color.DarkGray.copy(0.5f))
        Spacer(modifier = Modifier.padding(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {
                    navController.navigate("myanmar_lot_screen")
                }
            ) {
                Text("မြန်မာထီ ")
            }

            Button(
                onClick = {
                    navController.navigate("thai_lot_screen")
                }
            ) {
                Text("ထိုင်းထီ")
            }
        }
    }
}
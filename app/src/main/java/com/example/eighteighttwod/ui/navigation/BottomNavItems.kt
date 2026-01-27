package com.example.eighteighttwod.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.DynamicFeed
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.LiveTv
import androidx.compose.material.icons.filled.NightShelter
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.DynamicFeed
import androidx.compose.material.icons.outlined.LiveTv
import androidx.compose.material.icons.outlined.NightShelter
import androidx.compose.material.icons.outlined.SelfImprovement
import androidx.compose.material.icons.rounded.CardGiftcard
import androidx.compose.material.icons.rounded.Star
import androidx.compose.ui.graphics.vector.ImageVector


sealed class BottomNavItems(
    val title: String,
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
) {
    object Home: BottomNavItems(
        title = "မာတိကာ",
        route = "HomeScreen",
        selectedIcon = Icons.Filled.LiveTv,
        unselectedIcon = Icons.Outlined.LiveTv
    )
    object History: BottomNavItems(
        title = "History data",
        route = "historyScreen",
        selectedIcon = Icons.Filled.Description,
        unselectedIcon = Icons.Outlined.Description
    )
    object Lucky: BottomNavItems(
        title = "လက်ဆောင်\nဂဏန်း",
        route = "luckyScreen",
        selectedIcon = Icons.Filled.CardGiftcard,
        unselectedIcon = Icons.Rounded.CardGiftcard
    )
    object Omen: BottomNavItems(
        title = "အတိတ်\nစာရွက်",
        route = "omenScreen",
        selectedIcon = Icons.Filled.SelfImprovement,
        unselectedIcon = Icons.Outlined.SelfImprovement
    )
    object Dream: BottomNavItems(
        title = "အိမ်မက်\nကျမ်း",
        route = "dreamScreen",
        selectedIcon = Icons.Filled.NightShelter,
        unselectedIcon = Icons.Outlined.NightShelter
    )
    object Other: BottomNavItems(
        title = "အခြား",
        route = "otherScreen",
        selectedIcon = Icons.Filled.DynamicFeed,
        unselectedIcon = Icons.Outlined.DynamicFeed
    )

}
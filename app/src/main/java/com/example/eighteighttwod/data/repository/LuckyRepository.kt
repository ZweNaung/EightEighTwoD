package com.example.eighteighttwod.data.repository

import com.example.eighteighttwod.data.remote.dto.LuckyResponseDto
import com.example.eighteighttwod.ui.navigation.BottomNavItems
import com.example.eighteighttwod.utils.Resource

interface LuckyRepository {
    suspend fun getLucky(): Resource<List<LuckyResponseDto>>
}
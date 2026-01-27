package com.example.eighteighttwod.ui.screen.home

import com.example.eighteighttwod.data.remote.dto.LiveDataDto
import com.example.eighteighttwod.data.remote.dto.ModernResponseDto

data class ModernState(
    val isLoading: Boolean = false,
    val morningData: ModernResponseDto? = null, // 9:30 အတွက်
    val eveningData: ModernResponseDto? = null, // 2:00 အတွက်
    val error: String? = null,
)

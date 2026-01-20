package com.example.eighteighttwod.ui.screen.lucky

import com.example.eighteighttwod.data.remote.dto.LuckyResponseDto

data class LuckyState(
    val isLoading: Boolean = false,
    val liveData : List<LuckyResponseDto?> = emptyList(),
    val error: String? = null,
)

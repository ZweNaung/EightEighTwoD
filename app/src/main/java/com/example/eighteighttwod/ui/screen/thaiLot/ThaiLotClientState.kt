package com.example.eighteighttwod.ui.screen.thaiLot

import com.example.eighteighttwod.data.remote.dto.ThaiLotResponseDto

data class ThaiLotClientState(
    val isLoading: Boolean = false,
    val lotteryList: List<ThaiLotResponseDto> = emptyList(),
    val error: String? = null
)
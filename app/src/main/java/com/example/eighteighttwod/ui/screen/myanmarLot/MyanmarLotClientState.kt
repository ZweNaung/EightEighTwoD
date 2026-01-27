package com.example.eighteighttwod.ui.screen.myanmarLot

import com.example.eighteighttwod.data.remote.dto.MMLotResponseDto

data class MyanmarLotClientState(
    val isLoading: Boolean = false,
    val lotteryList: List<MMLotResponseDto> = emptyList(),
    val error: String? = null
)

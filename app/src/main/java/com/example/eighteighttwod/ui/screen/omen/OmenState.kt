package com.example.eighteighttwod.ui.screen.omen

import com.example.eighteighttwod.data.remote.dto.OmenResponseDto

data class OmenState(
    val isLoading: Boolean = false,
    val liveData : List<OmenResponseDto?> = emptyList(),
    val error: String? = null,
)

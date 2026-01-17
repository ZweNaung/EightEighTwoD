package com.example.eighteighttwod.ui.screen.home

import com.example.eighteighttwod.data.remote.dto.LiveDataDto

data class LiveState (
        val isLoading: Boolean = false,
        val liveData : LiveDataDto? = null,
        val error: String? = null,
)
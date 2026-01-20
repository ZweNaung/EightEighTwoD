package com.example.eighteighttwod.ui.screen.home

import com.example.eighteighttwod.data.remote.dto.UpdateResultDto

data class UpdateResultState(
    val isLoading: Boolean = false,

    val updateResult: List<UpdateResultDto> = emptyList(),

    val error: String? = null
)


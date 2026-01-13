package com.example.eighteighttwod.ui.screen.twoDhistory

import com.example.eighteighttwod.data.remote.dto.TwoDHistoryResponseDto

data class TwoDHistoryState(
val isLoading: Boolean = false,
val twoDList: List<TwoDHistoryResponseDto> =emptyList(),
val error: String? = null,
val isEntrySuccess : Boolean = false,
val isDeleteSuccess : Boolean = false
)


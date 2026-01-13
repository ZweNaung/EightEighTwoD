package com.example.eighteighttwod.data.repository

import com.example.eighteighttwod.data.remote.dto.TwoDHistoryResponseDto
import com.example.eighteighttwod.utils.Resource

interface TwoDHistoryRepository {
    suspend fun getAllTwoDHistory(): Resource<List<TwoDHistoryResponseDto>>
}
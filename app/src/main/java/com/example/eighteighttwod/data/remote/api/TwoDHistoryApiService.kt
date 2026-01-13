package com.example.eighteighttwod.data.remote.api

import com.example.eighteighttwod.data.remote.dto.TwoDHistoryResponseDto
import retrofit2.http.GET

interface TwoDHistoryApiService {
    @GET("allHistory/")
    suspend fun getAllHistory():List<TwoDHistoryResponseDto>
}
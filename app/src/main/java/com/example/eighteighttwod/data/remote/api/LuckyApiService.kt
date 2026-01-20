package com.example.eighteighttwod.data.remote.api

import com.example.eighteighttwod.data.remote.dto.LuckyResponseDto
import com.example.eighteighttwod.data.remote.response.BaseResponse
import retrofit2.http.GET

interface LuckyApiService {
        @GET("lucky")
        suspend fun getAllLucky(): BaseResponse<List<LuckyResponseDto>>
}
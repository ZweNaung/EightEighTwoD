package com.example.eighteighttwod.data.remote.api

import com.example.eighteighttwod.data.remote.dto.ModernResponseDto
import com.example.eighteighttwod.data.remote.response.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ModernApiService {
    @GET("modern/{title}")
    suspend fun getModern(@Path("title") title: String): BaseResponse<ModernResponseDto>
}
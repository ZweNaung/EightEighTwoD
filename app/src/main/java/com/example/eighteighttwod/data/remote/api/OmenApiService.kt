package com.example.eighteighttwod.data.remote.api

import com.example.eighteighttwod.data.remote.dto.OmenResponseDto
import com.example.eighteighttwod.data.remote.response.BaseResponse
import retrofit2.http.GET

interface OmenApiService {
    @GET("omen")
    suspend fun getOmen(): BaseResponse<List<OmenResponseDto>>
}
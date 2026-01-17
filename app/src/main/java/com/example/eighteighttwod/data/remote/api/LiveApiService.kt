package com.example.eighteighttwod.data.remote.api

import com.example.eighteighttwod.data.remote.dto.LiveDataDto
import com.example.eighteighttwod.data.remote.response.BaseResponse
import retrofit2.http.GET

interface LiveApiService {
    @GET("live")
    suspend fun getLiveData(): BaseResponse<LiveDataDto>
}
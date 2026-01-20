package com.example.eighteighttwod.data.remote.api

import com.example.eighteighttwod.data.remote.dto.UpdateResultDto
import com.example.eighteighttwod.data.remote.response.BaseResponse
import retrofit2.http.GET

interface UpdateResultApiService {
    @GET("live-result")
    suspend fun getTodayResults(): BaseResponse<List<UpdateResultDto>>
}
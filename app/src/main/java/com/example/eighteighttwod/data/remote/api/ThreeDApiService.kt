package com.example.eighteighttwod.data.remote.api

import com.example.eighteighttwod.data.remote.dto.ThreeDDto
import com.example.eighteighttwod.data.remote.response.BaseResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ThreeDApiService {
    @GET("threeD/all")
    suspend fun getAllThreeD(): BaseResponse<List<ThreeDDto>>


}
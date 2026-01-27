package com.example.eighteighttwod.data.remote.api

import com.example.eighteighttwod.data.remote.dto.ThaiLotResponseDto
import com.example.eighteighttwod.data.remote.response.BaseResponse
import retrofit2.http.GET

interface ThaiLotClientApiService {
    @GET("thaiLottery/all") // Backend Router: router.get('/thaiLottery/all', ...)
    suspend fun getThaiLots(): BaseResponse<List<ThaiLotResponseDto>>
}
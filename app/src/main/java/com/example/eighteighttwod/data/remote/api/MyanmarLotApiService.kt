package com.example.eighteighttwod.data.remote.api

import com.example.eighteighttwod.data.remote.dto.MMLotResponseDto
import com.example.eighteighttwod.data.remote.response.BaseResponse
import retrofit2.http.GET

interface MyanmarLotApiService {
    @GET("myanmarLot/all") // Backend Router နဲ့ ကိုက်ညီရပါမယ်
    suspend fun getMyanmarLots(): BaseResponse<List<MMLotResponseDto>>
}
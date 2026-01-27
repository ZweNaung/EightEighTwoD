package com.example.eighteighttwod.data.repository

import com.example.eighteighttwod.data.remote.dto.ThaiLotResponseDto
import com.example.eighteighttwod.utils.Resource

interface ThaiLotClientRepository {
    suspend fun getThaiLots(): Resource<List<ThaiLotResponseDto>>
}
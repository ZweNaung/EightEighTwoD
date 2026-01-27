package com.example.eighteighttwod.data.repository

import com.example.eighteighttwod.data.remote.dto.MMLotResponseDto
import com.example.eighteighttwod.utils.Resource

interface MyanmarLotRepository {
    suspend fun getMyanmarLots(): Resource<List<MMLotResponseDto>>
}
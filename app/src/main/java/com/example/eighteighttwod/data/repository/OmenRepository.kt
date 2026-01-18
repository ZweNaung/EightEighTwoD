package com.example.eighteighttwod.data.repository

import com.example.eighteighttwod.data.remote.dto.OmenResponseDto
import com.example.eighteighttwod.utils.Resource

interface OmenRepository {
    suspend fun getOmen(): Resource<List<OmenResponseDto>>
}
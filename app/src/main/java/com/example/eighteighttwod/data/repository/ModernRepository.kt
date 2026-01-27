package com.example.eighteighttwod.data.repository

import com.example.eighteighttwod.data.remote.dto.ModernResponseDto
import com.example.eighteighttwod.utils.Resource

interface ModernRepository {
    suspend fun getModern(title: String): Resource<ModernResponseDto>
}
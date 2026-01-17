package com.example.eighteighttwod.data.repository

import com.example.eighteighttwod.data.remote.dto.LiveDataDto
import com.example.eighteighttwod.utils.Resource

interface LiveRepository {
    suspend fun getLiveData(): Resource<LiveDataDto>
}
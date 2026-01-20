package com.example.eighteighttwod.data.repository

import com.example.eighteighttwod.data.remote.dto.LiveDataDto
import com.example.eighteighttwod.utils.Resource
import kotlinx.coroutines.flow.Flow

interface LiveRepository {
    fun getRealTimeLiveData(): Flow<Resource<LiveDataDto>>
}
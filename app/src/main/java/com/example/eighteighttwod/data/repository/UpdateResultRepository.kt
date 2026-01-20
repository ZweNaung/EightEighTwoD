package com.example.eighteighttwod.data.repository

import com.example.eighteighttwod.data.remote.dto.UpdateResultDto
import com.example.eighteighttwod.utils.Resource
import kotlinx.coroutines.flow.Flow

interface UpdateResultRepository {
    fun getTodayResults(): Flow<Resource<List<UpdateResultDto>>>
}
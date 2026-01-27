package com.example.eighteighttwod.data.repository

import com.example.eighteighttwod.data.remote.dto.ThreeDDto
import com.example.eighteighttwod.utils.Resource

interface ThreeDRepository {
    suspend fun getAllThreeD(): Resource<List<ThreeDDto>>


}
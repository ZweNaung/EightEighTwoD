package com.example.eighteighttwod.data.repository

import com.example.eighteighttwod.data.remote.api.ThaiLotClientApiService
import com.example.eighteighttwod.data.remote.dto.ThaiLotResponseDto
import com.example.eighteighttwod.utils.Resource
import javax.inject.Inject

class ThaiLotClientRepositoryImpl @Inject constructor(
    private val apiService: ThaiLotClientApiService
) : ThaiLotClientRepository {

    override suspend fun getThaiLots(): Resource<List<ThaiLotResponseDto>> {
        return try {
            val response = apiService.getThaiLots()
            if (response.success && response.data != null) {
                Resource.Success(response.data)
            } else {
                Resource.Error(message = response.message)
            }
        } catch (e: Exception) {
            Resource.Error(message = e.localizedMessage ?: "Unknown connection error")
        }
    }
}
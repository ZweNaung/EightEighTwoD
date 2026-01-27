package com.example.eighteighttwod.data.repository

import com.example.eighteighttwod.data.remote.api.MyanmarLotApiService
import com.example.eighteighttwod.data.remote.dto.MMLotResponseDto
import com.example.eighteighttwod.utils.Resource
import javax.inject.Inject

class MyanmarLotRepositoryImpl @Inject constructor(
    private val apiService: MyanmarLotApiService
) : MyanmarLotRepository {

    override suspend fun getMyanmarLots(): Resource<List<MMLotResponseDto>> {
        return try {
            val response = apiService.getMyanmarLots()
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
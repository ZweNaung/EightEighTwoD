package com.example.eighteighttwod.data.repository

import com.example.eighteighttwod.data.remote.api.TwoDHistoryApiService
import com.example.eighteighttwod.data.remote.dto.TwoDHistoryResponseDto
import com.example.eighteighttwod.utils.Resource

class TwoDHistoryRepositoryImp(
    private val api: TwoDHistoryApiService
) : TwoDHistoryRepository {
     override suspend fun getAllTwoDHistory(): Resource<List<TwoDHistoryResponseDto>> {
        return try {
            val data = api.getAllHistory()
             Resource.Success(data)
            } catch (e: Exception) {
            return Resource.Error(message = e.localizedMessage ?: "Uknown Error")
            }
        }
}
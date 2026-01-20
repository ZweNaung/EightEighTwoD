package com.example.eighteighttwod.data.repository

import com.example.eighteighttwod.data.remote.api.LuckyApiService
import com.example.eighteighttwod.data.remote.dto.LuckyResponseDto
import com.example.eighteighttwod.utils.Resource
import javax.inject.Inject

class LuckyRepositoryImp @Inject constructor(
    private val api: LuckyApiService
): LuckyRepository {
    override suspend fun getLucky(): Resource<List<LuckyResponseDto>> {
        return try {
            val response = api.getAllLucky()
            if(response.success && response.data != null){
                Resource.Success(response.data)
            }else{
                Resource.Error(message = response.message)
            }
        } catch (e: Exception){
            Resource.Error(message = e.message ?: "Unknown Error")
        }
    }

}
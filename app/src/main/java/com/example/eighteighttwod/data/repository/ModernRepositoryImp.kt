package com.example.eighteighttwod.data.repository

import com.example.eighteighttwod.data.remote.api.ModernApiService
import com.example.eighteighttwod.data.remote.dto.ModernResponseDto
import com.example.eighteighttwod.utils.Resource
import javax.inject.Inject

class ModernRepositoryImp @Inject constructor(
    private val api: ModernApiService
) : ModernRepository{
    override suspend fun getModern(title: String): Resource<ModernResponseDto> {
        return try {
            val response = api.getModern(title)
            if(response.success && response.data != null){
                Resource.Success(data = response.data)
            }else{
                Resource.Error(message = response.message)
            }
        }catch (e: Exception){
            Resource.Error(message = e.message ?: "Unknown Error")
        }
    }
}
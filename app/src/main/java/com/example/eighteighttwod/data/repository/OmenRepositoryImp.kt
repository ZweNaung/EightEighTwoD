package com.example.eighteighttwod.data.repository

import com.example.eighteighttwod.data.remote.api.OmenApiService
import com.example.eighteighttwod.data.remote.dto.OmenResponseDto
import com.example.eighteighttwod.utils.Resource
import javax.inject.Inject

class OmenRepositoryImp @Inject constructor(
    private val api: OmenApiService
) : OmenRepository{
    override suspend fun getOmen(): Resource<List<OmenResponseDto>> {
        return try {
            val response = api.getOmen()
            if(response.success && response.data !=null){
                Resource.Success(data = response.data)
            }else{
                Resource.Error(message = response.message)
            }

        }catch (e: Exception){
            Resource.Error(message = e.message ?: "Unknown Error")
        }
    }

}
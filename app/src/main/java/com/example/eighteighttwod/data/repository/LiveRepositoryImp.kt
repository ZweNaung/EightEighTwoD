package com.example.eighteighttwod.data.repository
import com.example.eighteighttwod.data.remote.api.LiveApiService
import com.example.eighteighttwod.data.remote.dto.LiveDataDto
import com.example.eighteighttwod.utils.Resource
import javax.inject.Inject

class LiveRepositoryImp @Inject constructor(
    private val api: LiveApiService
): LiveRepository {
    override suspend fun getLiveData(): Resource<LiveDataDto> {
        return try {
            val response = api.getLiveData()

            if(response.success && response.data != null){
                Resource.Success(data = response.data)
            }else{
                Resource.Error(message = response.message)
            }
        }catch (e: Exception){
            e.printStackTrace()
            Resource.Error(message = e.message ?: "An unknown error occurred")
        }
    }
}
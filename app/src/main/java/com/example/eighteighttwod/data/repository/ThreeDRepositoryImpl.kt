package com.example.eighteighttwod.data.repository

import android.util.Log
import com.example.eighteighttwod.data.remote.api.ThreeDApiService
import com.example.eighteighttwod.data.remote.dto.ThreeDDto
import com.example.eighteighttwod.utils.Resource

class ThreeDRepositoryImpl (private val api: ThreeDApiService): ThreeDRepository{

    override suspend fun getAllThreeD(): Resource<List<ThreeDDto>> {
        return try {
            val response = api.getAllThreeD()
            if(response.success && response.data !=null){
                Resource.Success(response.data)
            }else{
                Resource.Error(message = response.message)
            }
        }catch (e: Exception){
            e.printStackTrace()
            Resource.Error(message = e.message ?: "An unkow error occurred")
        }
    }



}
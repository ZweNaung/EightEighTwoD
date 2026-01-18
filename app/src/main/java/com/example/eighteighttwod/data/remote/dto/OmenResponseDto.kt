package com.example.eighteighttwod.data.remote.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


    @Serializable
    data class OmenResponseDto(
        @SerialName("_id")
        val id: String,
        @SerialName("imgUrl")
        val imgUrl: String,
        @SerialName("name")
        val name: String,
        @SerialName("__v")
        val v: Int
    )

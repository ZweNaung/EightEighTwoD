package com.example.eighteighttwod.data.remote.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class LiveDataDto(
    @SerialName("set")
    val set: String,

    @SerialName("twoD")
    val twoD: String,

    @SerialName("updatedAt")
    val updatedAt: Long,   // ⭐ String → Long

    @SerialName("value")
    val value: String
)

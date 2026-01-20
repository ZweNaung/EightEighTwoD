package com.example.eighteighttwod.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LuckyResponseDto(
    @SerialName("_id")
    val id: String,

    @SerialName("name")
    val name: String,

    @SerialName("imgUrl")
    val imgUrl: String,

    @SerialName("section")
    val section: String // "week" or "day"
)
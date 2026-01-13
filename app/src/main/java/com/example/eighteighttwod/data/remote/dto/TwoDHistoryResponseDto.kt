package com.example.eighteighttwod.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
    data class TwoDHistoryResponseDto(
    @SerialName("_id")
    val id: String,
    val date: String,
    val child: List<TwoDChildDto>,
    @SerialName("__v")
    val version: Int? = null
)
@Serializable
    data class TwoDChildDto(
    @SerialName("_id")
     val id: String,
    val twoD: String,
        val time:String,
        val set: String,
        val value: String
)

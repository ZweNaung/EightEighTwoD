package com.example.eighteighttwod.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
    data class ModernResponseDto(
@SerialName("title")
val title: String,

@SerialName("modern")
val modern: String,

@SerialName("internet")
val internet: String,

@SerialName("_id")
val id: String? = null
)


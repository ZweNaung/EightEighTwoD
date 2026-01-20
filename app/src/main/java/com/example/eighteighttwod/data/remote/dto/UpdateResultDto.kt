package com.example.eighteighttwod.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
    data class UpdateResultDto(
        @SerialName("_id")
        val id: String? = null, // Backend မှ auto ထွက်လာမယ့် ID (POST တွင်မလို၊ GET တွင်ပါမည်)

        @SerialName("twoD")
        val twoD: String,

        @SerialName("set")
        val set: String,

        @SerialName("value")
        val value: String,

        @SerialName("session")
        val session: String // "12:01 PM" or "4:30 PM"
    )


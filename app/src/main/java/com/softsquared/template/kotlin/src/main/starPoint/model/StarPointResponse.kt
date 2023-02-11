package com.softsquared.template.kotlin.src.main.search.model

import com.google.gson.annotations.SerializedName

data class StarPointResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: ArrayList<StarPointDTO>
)

package com.softsquared.template.kotlin.src.main.review.model

import com.google.gson.annotations.SerializedName

data class ReviewResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: ArrayList<ReviewDTO>
)

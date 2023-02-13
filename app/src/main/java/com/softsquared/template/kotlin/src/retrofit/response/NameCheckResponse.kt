package com.softsquared.template.kotlin.src.retrofit.response

import com.google.gson.annotations.SerializedName
import com.softsquared.template.kotlin.config.BaseResponse
import com.softsquared.template.kotlin.src.retrofit.model.GetPointDTO

data class NameCheckResponse(
    @SerializedName("result") val result: Boolean
):BaseResponse()

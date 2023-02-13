package com.softsquared.template.kotlin.src.retrofit.response

import com.google.gson.annotations.SerializedName
import com.softsquared.template.kotlin.config.BaseResponse
import com.softsquared.template.kotlin.src.retrofit.model.PutUserDTO

data class PutUserResponse(
    @SerializedName("result") val result: PutUserDTO
):BaseResponse()

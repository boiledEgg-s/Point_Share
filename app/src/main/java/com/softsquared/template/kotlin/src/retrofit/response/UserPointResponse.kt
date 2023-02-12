package com.softsquared.template.kotlin.src.retrofit.response

import com.google.gson.annotations.SerializedName
import com.softsquared.template.kotlin.config.BaseResponse
import com.softsquared.template.kotlin.src.retrofit.model.GetUserPointDTO

data class UserPointResponse(
    @SerializedName("result") val result: ArrayList<GetUserPointDTO>
):BaseResponse()

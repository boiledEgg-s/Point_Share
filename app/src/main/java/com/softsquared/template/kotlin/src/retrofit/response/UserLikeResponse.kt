package com.softsquared.template.kotlin.src.retrofit.response

import com.google.gson.annotations.SerializedName
import com.softsquared.template.kotlin.config.BaseResponse
import com.softsquared.template.kotlin.src.retrofit.model.GetUserLikeDTO
import com.softsquared.template.kotlin.src.retrofit.model.GetUserPointDTO

data class UserLikeResponse(
    @SerializedName("result") val result: ArrayList<GetUserLikeDTO>
):BaseResponse()

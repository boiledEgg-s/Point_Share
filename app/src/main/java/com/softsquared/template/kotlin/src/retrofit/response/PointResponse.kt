package com.softsquared.template.kotlin.src.retrofit.response

import com.google.gson.annotations.SerializedName
import com.softsquared.template.kotlin.config.BaseResponse
import com.softsquared.template.kotlin.src.retrofit.model.GetPointDTO

data class PointResponse(
    @SerializedName("result") val result: ArrayList<GetPointDTO>
):BaseResponse()

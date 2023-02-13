package com.softsquared.template.kotlin.src.retrofit.response

import com.google.gson.annotations.SerializedName
import com.softsquared.template.kotlin.config.BaseResponse
import com.softsquared.template.kotlin.src.retrofit.model.GetMarkDTO
import com.softsquared.template.kotlin.src.retrofit.model.PutUserDTO

data class GetMarkResponse(
    @SerializedName("result") val result:ArrayList<GetMarkDTO>
):BaseResponse()

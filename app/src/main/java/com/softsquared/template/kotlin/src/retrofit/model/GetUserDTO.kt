package com.softsquared.template.kotlin.src.retrofit.model

import java.io.Serializable

data class GetUserDTO(
    var userId:String,
    var nickname:String,
    var image_url: String
) : Serializable {

}

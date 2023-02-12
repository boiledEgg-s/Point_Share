package com.softsquared.template.kotlin.src.retrofit.model

import java.io.Serializable

data class PutUserDTO(
    var userId:String,
    var filePath: String
) : Serializable {

}

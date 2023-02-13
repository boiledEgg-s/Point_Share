package com.softsquared.template.kotlin.src.retrofit.model

import java.io.Serializable

data class GetUserLikeDTO(
    var point_id:String?=null,
    var title:String?=null,
    var point_date:String?=null,
    var point_img_list:String?=null
): Serializable

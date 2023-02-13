package com.softsquared.template.kotlin.src.retrofit.model

import java.io.Serializable

data class GetUserPointDTO(
    var point_id:String?=null,
    var title:String?=null,
    var content:String?=null,
    var point_type:String?=null,
    var creature:String?=null,
    var point_date:String?=null,
    var location:String?=null,
    var point_img_list:String?=null
): Serializable

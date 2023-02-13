package com.softsquared.template.kotlin.src.retrofit.model

import java.io.Serializable

data class GetPointDTO(
    var user_id:String?=null,
    var point_id:String?=null,
    var title:String?=null,
    var nickname:String?=null,
    var point_type:String?=null,
    var creature:String?=null,
    var point_date:String?=null,
    var content:String?=null,
    var likes:Int?=null,
    var location:String?=null,
    var user_img_url:String?=null,
    var point_image_list:String?=null,
    var latitude:Double?=null,
    var longitude:Double?=null
): Serializable

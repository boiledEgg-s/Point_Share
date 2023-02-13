package com.softsquared.template.kotlin.src.main.search.model

import java.io.Serializable

data class StarPointItem(
    var user_id:String?=null,
    var point_id:String?=null,
    var title:String?=null,
    var point_image:String?=null,
    var nickname:String?=null,
    var profile_image:String?=null,
    var point_date:String?=null,
    var content:String?=null,
    var likes:Int?=null,
    var location:String?=null,
): Serializable

package com.softsquared.template.kotlin.src.main.review.model

import java.io.Serializable

data class ReviewDTO(
    var point_id:String,
    var title:String,
    var point_image_list:String,
    var nickname:String,
    var point_type:Int,
    var creature:String,
    var point_date:String,
    var content:String,
    var likes:Int,
    var location:String,
    var latitude:Double,
    var longitude:Double
): Serializable

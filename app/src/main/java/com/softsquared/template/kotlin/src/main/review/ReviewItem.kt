package com.softsquared.template.kotlin.src.main.review

import java.io.Serializable

data class ReviewItem(
    var profileImg:String,
    var name:String,
    var content:String,
    var image:String,
    var likes:Int,
    var loc:String,
    var regDate:String
): Serializable

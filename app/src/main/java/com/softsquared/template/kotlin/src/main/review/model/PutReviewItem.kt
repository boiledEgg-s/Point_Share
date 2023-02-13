package com.softsquared.template.kotlin.src.main.review.model

import java.io.File
import java.io.Serializable

data class PutReviewItem(
    var userId:String?=null,
    var pointId:String?=null,
    var title:String,
    var content:String,
    var point_Type:String,
    var location:String,
    var creature:String,
    var point_date:String,
    var images:ArrayList<String>?=null
): Serializable {

}

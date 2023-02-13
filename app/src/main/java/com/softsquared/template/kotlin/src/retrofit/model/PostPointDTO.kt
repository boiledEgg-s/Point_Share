package com.softsquared.template.kotlin.src.retrofit.model

import java.io.File
import java.io.Serializable


data class PostPointDTO(
    var userId:String?=null,
    var title:String,
    var content:String,
    var point_Type:String,
    var location:String,
    var creature:String,
    var point_date:String,
    var images:ArrayList<File>
): Serializable {}

package com.softsquared.template.kotlin.src.main.review

import com.softsquared.template.kotlin.src.main.review.model.PostReviewRequest
import com.softsquared.template.kotlin.src.main.search.model.ReviewResponse
import com.softsquared.template.kotlin.src.main.search.model.StarPointResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ReviewRetrofitInterface {

    @GET("app/points/{pointId}")
    fun getReviews(
        @Path("pointId") pointId: String
    ) : Call<ReviewResponse>

    @Multipart
    @POST("app/points")
    fun postReview(
        @Part("userId") userId:String ?= "2646841646",
        @Part("title") title:String,
        @Part("content") content:String,
        @Part("point_type") point_type:String,
        @Part("location") location:String,
        @Part("creature") creature:String,
        @Part("point_date") point_date:String,
        @Part images: List<MultipartBody.Part>?
    ) : Call<ReviewResponse>
}
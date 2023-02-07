package com.softsquared.template.kotlin.src.main.review

import com.softsquared.template.kotlin.src.main.review.model.ReviewResponse
import retrofit2.Call
import retrofit2.http.GET

interface ReviewRetrofitInterface {

    //ReviewResponse.result로 배열에 접근
    @GET("/points")
    fun getReviews() : Call<ReviewResponse>

//    @POST("/template/users")
//    fun postSignUp(@Body params: PostSignUpRequest): Call<SignUpResponse>
}
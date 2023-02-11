package com.softsquared.template.kotlin.src.main.starPoint

import com.softsquared.template.kotlin.src.main.search.model.StarPointResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface StarPointRetrofitInterface {

    //ReviewResponse.result로 배열에 접근
    @GET("app/points")
    fun getReviews(
        @Query("keyword") keyword: String? = null,
        @Query("order") order: String? = null,
        @Query("pageId") pageId: String? = null
    ) : Call<StarPointResponse>

//    @POST("/template/users")
//    fun postSignUp(@Body params: PostSignUpRequest): Call<SignUpResponse>
}
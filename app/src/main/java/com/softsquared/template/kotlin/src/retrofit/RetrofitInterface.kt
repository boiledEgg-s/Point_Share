package com.softsquared.template.kotlin.src.retrofit
/*
7. 유저 조회
8. 유저 정보 조회
11. 특정 포인트에 좋아요 표시
12. 특정 포인트에 좋아요 취소
14. 모든 포인트 조회
15. 특정 포인트 조회
16. 포인트 등록


19. 유저의 포인트 조회
20. 유저가 좋아요한 포인트 조회
 */


import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.src.retrofit.model.*
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface RetrofitInterface {

    //7. 유저 정보 조회
    @GET("app/users/{userId}")
    fun getUserInfo(
        @Path("userId") userId: String
    ) : Call<UserResponse>

    //8. 유저 정보 조회
    @PUT("app/users/{userId}")
    fun putUserInfo(
        @Path("userId") userId: String?=ApplicationClass.user_id,
        @Part images: MultipartBody.Part
    ) : Call<UserResponse>

    //11. 특정 포인트에 좋아요 표시
    @POST("app/users/{userId}/likes/{pointId}")
    fun postLike(
        @Path("userId") userId: String?=ApplicationClass.user_id,
        @Path("pointId") pointId: String
    )
    //12. 특정 포인트에 좋아요 취소
    @DELETE("app/users/{userId}/likes/{pointId}")
    fun deleteLike(
        @Path("userId") userId: String?=ApplicationClass.user_id,
        @Path("pointId") pointId: String
    )

    //14. 모든 포인트 조회 + 페이징 처리
    @GET("app/points")
    fun getReviews(
        @Query("keyword") keyword: String? = null,
        @Query("order") order: String? = null,
        @Query("pageId") pageId: String? ="0"
    ) : Call<PointResponse>

    //15. 포인트 아이디로 조회
    @GET("app/points/{pointId}")
    fun getSpecificPoint(
        @Path("pointId") pointId: String
    ) : Call<PointResponse>

    //16. 포인트 등록
    @Multipart
    @POST("app/points")
    fun postPoint(
        @Part("userId") userId:String ?= ApplicationClass.user_id,
        @Part("title") title:String,
        @Part("content") content:String,
        @Part("point_type") point_type:String,
        @Part("location") location:String,
        @Part("creature") creature:String,
        @Part("point_date") point_date:String,
        @Part images: List<MultipartBody.Part>?
    ) : Call<PointResponse>

    //19. 유저의 포인트 조회
    @GET("users/{userId}/points")
    fun getUserPoints(
        @Path("userId") userId:String ?= ApplicationClass.user_id
    )

    //20. 유저가 좋아요한 포인트 조회
    @GET("users/{userId}/likes")
    fun getUserLikes(
        @Path("userId") userId:String ?= ApplicationClass.user_id,
        @Query("pageId") pageId: String? = "0"
    )
}
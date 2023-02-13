package com.softsquared.template.kotlin.src.retrofit
/*
2. 유저 생성 후 유저 정보 포인트쉐어 서버로 이동

4. 유저 로그아웃

6. 회원가입 시 닉네임 중복여부 확인
9. 회원가입 시 사용자 프로필 생성
10. 회원 탈퇴





7. 유저 조회
8. 유저 정보 수정
11. 특정 포인트에 좋아요 표시
12. 특정 포인트에 좋아요 취소
14. 모든 포인트 조회
15. 특정 포인트 조회
16. 포인트 등록
17. 특정 포인트 수정
18. 특정 포인트 삭제
19. 유저의 포인트 조회
20. 유저가 좋아요한 포인트 조회
21. 특정 위치의 포인트 정보 조회 : 제목, 어종, 해루질 종류, 좋아요 수
 */


import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.config.BaseResponse
import com.softsquared.template.kotlin.src.retrofit.response.*
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface RetrofitInterface {

    //6.유저 이름 중복 확인
    @GET("app/users")
    fun getNameCheck(
        @Query("nickname") nickname: String
    ) : Call<NameCheckResponse>

    //7. 유저 정보 조회
    @GET("app/users/{userId}")
    fun getUserInfo(
        @Path("userId") userId: String
    ) : Call<UserResponse>

    //8. 유저 정보 수정
    @Multipart
    @PUT("app/users/{userId}")
    fun putUserInfo(
        @Path("userId") userId: String?=ApplicationClass.user_id,
        @Part image: MultipartBody.Part
    ) : Call<PutUserResponse>

    //11. 특정 포인트에 좋아요 표시
    @POST("app/users/{userId}/likes/{pointId}")
    fun postLike(
        @Path("userId") userId: String?=ApplicationClass.user_id,
        @Path("pointId") pointId: String
    ):Call<BaseResponse>
    //12. 특정 포인트에 좋아요 취소
    @DELETE("app/users/{userId}/likes/{pointId}")
    fun deleteLike(
        @Path("userId") userId: String?=ApplicationClass.user_id,
        @Path("pointId") pointId: String
    ):Call<BaseResponse>

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

    //17. 특정 포인트 수정
    @Multipart
    @PUT("app/points/{pointId}")
    fun updatePoint(
        @Path("pointId") pointId: String,
        @Part("title") title:String,
        @Part("content") content:String,
        @Part("point_type") point_type:String,
        @Part("location") location:String,
        @Part("creature") creature:String,
        @Part("point_date") point_date:String,
        @Part images: List<MultipartBody.Part>?
    ) : Call<UpdatePointResponse>

    //18. 특정 포인트 삭제
    @DELETE("app/points/{pointId}")
    fun deletePoint(
        @Path("pointId") pointId: String
    ) : Call<UpdatePointResponse>

    //19. 유저의 포인트 조회
    @GET("app/users/{userId}/points")
    fun getUserPoints(
        @Path("userId") userId:String ?= ApplicationClass.user_id
    ): Call<UserPointResponse>

    //20. 유저가 좋아요한 포인트 조회
    @GET("app/users/{userId}/likes")
    fun getUserLikes(
        @Path("userId") userId:String ?= ApplicationClass.user_id,
        @Query("pageId") pageId: String?="1"
    ): Call<UserLikeResponse>

    //21. 특정 위치의 포인트 정보 조회 : 제목, 어종, 해루질 종류, 좋아요 수
    @GET("app/points/maps/mark")
    fun getMapMark(
        @Query("latitude") latitude:Double,
        @Query("longitude") longitude:Double
    ): Call<GetMarkResponse>
}
package com.softsquared.template.kotlin.src.retrofit
/*
7. 유저 조회
8. 유저 정보 수정
11. 특정 포인트에 좋아요 표시
12. 특정 포인트에 좋아요 취소
14. 모든 포인트 조회
15. 특정 포인트 조회
16. 포인트 등록


19. 유저의 포인트 조회
20. 유저가 좋아요한 포인트 조회
 */


import android.util.Log
import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.config.BaseResponse
import com.softsquared.template.kotlin.src.retrofit.model.*
import com.softsquared.template.kotlin.src.retrofit.response.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class RetrofitService(val retrofitClass: RetrofitClassInterface) {
    val service = ApplicationClass.sRetrofit.create(RetrofitInterface::class.java)

    //7. 유저 조회
    fun tryGetUser(userId: String) {
        service.getUserInfo(userId).enqueue(object : Callback<UserResponse> {
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                retrofitClass.onGetUserSuccess(response.body() as UserResponse)
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                retrofitClass.onGetUserFailure(t.message ?: "통신 오류")
            }
        })
    }

    //8. 유저 정보 수정
    fun tryPutUser(userId: String, img: File) {
        var fileToUpload = arrayListOf<MultipartBody.Part>()
        val fileName = "profile_" + userId + ".png"
        var requestBody = img.asRequestBody("image/jpeg".toMediaTypeOrNull())
        fileToUpload.add(MultipartBody.Part.createFormData("images", fileName, requestBody))
        //val fileToUpload = MultipartBody.Part.createFormData("images", fileName, requestBody)

        service.putUserInfo(userId, fileToUpload[0]).enqueue(object : Callback<PutUserResponse> {
            override fun onResponse(
                call: Call<PutUserResponse>,
                response: Response<PutUserResponse>
            ) {
                Log.d("Retrofit Service_putinfo", response.toString())
                retrofitClass.onPutUserSuccess(response.body() as PutUserResponse)
            }

            override fun onFailure(call: Call<PutUserResponse>, t: Throwable) {
                retrofitClass.onPutUserFailure(t.message ?: "통신 오류")
            }
        })
    }

    //11. 특정 포인트에 좋아요 표시
    fun tryPostLike(userId: String, pointId:String){
        service.postLike(userId, pointId).enqueue(object : Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                retrofitClass.onPostLikeSuccess(response.body() as BaseResponse)
            }
            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                retrofitClass.onPostLikeFailure(t.message ?: "통신 오류")
            }
        })
    }

    //12. 특정 포인트에 좋아요 해제
    fun tryDeleteLike(userId: String, pointId:String){
        service.deleteLike(userId, pointId).enqueue(object : Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                retrofitClass.onDeleteLikeSuccess(response.body() as BaseResponse)
            }
            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                retrofitClass.onDeleteLikeFailure(t.message ?: "통신 오류")
            }
        })
    }

    //14. 모든 포인트 조회
    fun tryGetPoints(keyword: String? = null, order: String? = null, pageId: String) {
        service.getReviews(keyword, order, pageId).enqueue(object : Callback<PointResponse> {
            override fun onResponse(call: Call<PointResponse>, response: Response<PointResponse>) {
                retrofitClass.onGetPointsSuccess(response.body() as PointResponse)
            }

            override fun onFailure(call: Call<PointResponse>, t: Throwable) {
                retrofitClass.onGetPointsFailure(t.message ?: "통신 오류")
            }
        })
    }

    //15. 특정 포인트 조회
    fun tryGetSpecificPoint(pointId: String) {
        service.getSpecificPoint(pointId).enqueue(object : Callback<PointResponse> {
            override fun onResponse(
                call: Call<PointResponse>,
                response: Response<PointResponse>
            ) {
                retrofitClass.onGetSpecificPointSuccess(response.body() as PointResponse)
            }

            override fun onFailure(call: Call<PointResponse>, t: Throwable) {
                retrofitClass.onGetSpecificPointFailure(t.message ?: "통신 오류")
            }
        })
    }

    //16. 포인트 등록
    fun tryPostPoint(request: PostPointDTO) {
        var fileToUpload = arrayListOf<MultipartBody.Part>()
        for (img in request.images) {
            var fileName =
                "testingFromAndroid" + System.nanoTime().toString().subSequence(0, 5) + ".png"
            var requestBody = img.asRequestBody("image/jpeg".toMediaTypeOrNull())
            fileToUpload.add(MultipartBody.Part.createFormData("images", fileName, requestBody))
        }

        Log.d(
            "REVIEW_SERVICE > postReview",
            "${request.title}, ${request.content}, ${request.point_date}"
        )

        service.postPoint(
            request.userId, request.title, request.content, request.point_Type,
            request.location, request.creature, request.point_date.toString(), fileToUpload
        ).enqueue(object : Callback<PointResponse> {
            override fun onResponse(
                call: Call<PointResponse>,
                response: Response<PointResponse>
            ) {
                retrofitClass.onPostPointSuccess(response.message())
            }

            override fun onFailure(call: Call<PointResponse>, t: Throwable) {
                retrofitClass.onPostPointFailure(t.message ?: "통신 오류")
            }
        })
    }

    //19. 유저의 포인트 조회
    fun tryGetUserPoints(userId: String) {
        service.getUserPoints(userId).enqueue(object : Callback<UserPointResponse> {
            override fun onResponse(call: Call<UserPointResponse>, response: Response<UserPointResponse>) {
                retrofitClass.onGetUserPointSuccess(response.body() as UserPointResponse)
            }

            override fun onFailure(call: Call<UserPointResponse>, t: Throwable) {
                retrofitClass.onGetUserPointFailure(t.message ?: "통신 오류")
            }
        })
    }

    //20. 유저가 좋아요한 포인트 조회
    fun tryGetUserLikes(userId:String, pageId: String) {
        service.getUserLikes(userId, pageId).enqueue(object : Callback<UserLikeResponse> {
            override fun onResponse(call: Call<UserLikeResponse>, response: Response<UserLikeResponse>) {
                retrofitClass.onGetUserLikeSuccess(response.body() as UserLikeResponse)
            }

            override fun onFailure(call: Call<UserLikeResponse>, t: Throwable) {
                retrofitClass.onGetUserLikeFailure(t.message ?: "통신 오류")
            }
        })
    }
}
package com.softsquared.template.kotlin.src.retrofit
/*
7. 유저 조회 (완)
8. 유저 정보 조회
14. 모든 포인트 조회 (완)
15. 특정 포인트 조회 (완)
16. 포인트 등록 (완)
19. 유저의 포인트 조회
20. 유저가 좋아요한 포인트 조회
 */


import android.util.Log
import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.src.retrofit.model.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

    //14. 모든 포인트 조회
    fun tryGetPoints(keyword:String?=null, order:String?=null, pageId:String){
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
    fun tryPostPoint(request:PostPointDTO) {
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
            request.location, request.creature, request.point_date, fileToUpload
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

}
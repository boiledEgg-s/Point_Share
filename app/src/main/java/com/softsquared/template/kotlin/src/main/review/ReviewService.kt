package com.softsquared.template.kotlin.src.main.review

import android.util.Log
import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.src.main.review.model.PostReviewRequest
import com.softsquared.template.kotlin.src.main.search.model.ReviewDTO
import com.softsquared.template.kotlin.src.main.search.model.ReviewResponse
import com.softsquared.template.kotlin.src.main.search.model.StarPointResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.sql.Timestamp

class ReviewService (val reviewActivityInterface: ReviewActivityInterface){
    fun tryGetPoints(pointId:String){
        val service = ApplicationClass.sRetrofit.create(ReviewRetrofitInterface::class.java)
        service.getReviews(pointId).enqueue(object : Callback<ReviewResponse> {
            override fun onResponse(call: Call<ReviewResponse>, response: Response<ReviewResponse>) {
                reviewActivityInterface.onGetUserSuccess(response.body() as ReviewResponse)
            }

            override fun onFailure(call: Call<ReviewResponse>, t: Throwable) {
                reviewActivityInterface.onGetUserFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun postReview(request: PostReviewRequest){
        var fileToUpload = arrayListOf<MultipartBody.Part>()
        for(img in request.images){
            var fileName = "testingFromAndroid"+System.nanoTime().toString().subSequence(0, 5)+".png"
            var requestBody = img.asRequestBody("image/jpeg".toMediaTypeOrNull())
            fileToUpload.add(MultipartBody.Part.createFormData("images", fileName, requestBody))
        }

        Log.d("REVIEW_SERVICE > postReview", "${request.title}, ${request.content}, ${request.point_date}")

        val service = ApplicationClass.sRetrofit.create(ReviewRetrofitInterface::class.java)
        service.postReview(request.userId, request.title, request.content, request.point_Type,
            request.location, request.creature, request.point_date, fileToUpload).enqueue(object : Callback<ReviewResponse> {
            override fun onResponse(call: Call<ReviewResponse>, response: Response<ReviewResponse>) {
                reviewActivityInterface.onGetUserSuccess(response.body() as ReviewResponse)
            }

            override fun onFailure(call: Call<ReviewResponse>, t: Throwable) {
                reviewActivityInterface.onGetUserFailure(t.message ?: "통신 오류")
            }
        })
    }

}
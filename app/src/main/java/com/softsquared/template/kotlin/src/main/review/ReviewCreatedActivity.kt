package com.softsquared.template.kotlin.src.main.review

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import com.softsquared.template.kotlin.config.BaseActivity
import com.softsquared.template.kotlin.databinding.ActivityReviewCreatedBinding
import com.softsquared.template.kotlin.src.main.review.model.PostReviewRequest
import com.softsquared.template.kotlin.src.main.search.model.ReviewResponse

class ReviewCreatedActivity: BaseActivity<ActivityReviewCreatedBinding>(
    ActivityReviewCreatedBinding::inflate), ReviewActivityInterface{
    private val service = ReviewService(this)

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val data = intent.getSerializableExtra("data", PostReviewRequest::class.java) as PostReviewRequest
        service.postReview(data)

        Log.d("REVIEW_CREATED_ACTIVITY", "${data.title}, ${data.content}, ${data.point_date}, ${data.point_Type} ")

        binding.reviewCreatedBtnFinish.setOnClickListener{
            finish()
        }
    }

    override fun onGetUserSuccess(response: ReviewResponse) {}

    override fun onGetUserFailure(message: String) {}

    override fun onPostUserSuccess(response: ReviewResponse) {

    }

    override fun onPostUserFailure(message: String) {
        Log.e("Retrofit2", "$message")
    }
}
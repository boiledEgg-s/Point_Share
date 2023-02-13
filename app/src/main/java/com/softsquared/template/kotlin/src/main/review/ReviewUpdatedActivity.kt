package com.softsquared.template.kotlin.src.main.review

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import com.softsquared.template.kotlin.config.BaseActivity
import com.softsquared.template.kotlin.databinding.ActivityReviewCreatedBinding
import com.softsquared.template.kotlin.databinding.ActivityReviewUpdatedBinding
import com.softsquared.template.kotlin.src.retrofit.RetrofitClassInterface
import com.softsquared.template.kotlin.src.retrofit.RetrofitService
import com.softsquared.template.kotlin.src.retrofit.model.*

class ReviewUpdatedActivity: BaseActivity<ActivityReviewUpdatedBinding>(
    ActivityReviewUpdatedBinding::inflate), RetrofitClassInterface{
    private val service = RetrofitService(this)

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val data = intent.getSerializableExtra("data", PostPointDTO::class.java) as PostPointDTO
        val pointId = intent.getStringExtra("pointId")!!.toString()
        Log.d("Review Created", data.point_date)
        service.tryPutPoints(pointId, data)

        Log.d("REVIEW_UPDATED_ACTIVITY", "${data.title}, ${data.content}, ${data.point_date}, ${data.point_Type} ")



        binding.reviewCreatedBtnFinish.setOnClickListener{
            finish()
        }
    }

    override fun onPostPointSuccess(message: String) {
        super.onPostPointSuccess(message)
        Log.d("REVIEW CREATED", "POST SUCCESSFUL")
    }

    override fun onPostPointFailure(message: String) {
        super.onPostPointFailure(message)
        Log.d("REVIEW CREATED", "POST FAILED")
    }
}
package com.softsquared.template.kotlin.src.main.review

import android.os.Bundle
import com.softsquared.template.kotlin.config.BaseActivity
import com.softsquared.template.kotlin.databinding.ActivityReviewCreatedBinding

class ReviewCreatedActivity: BaseActivity<ActivityReviewCreatedBinding>(
    ActivityReviewCreatedBinding::inflate){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.reviewCreatedBtnFinish.setOnClickListener{
            finish()
        }
    }
}
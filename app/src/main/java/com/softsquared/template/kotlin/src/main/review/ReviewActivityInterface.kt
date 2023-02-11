package com.softsquared.template.kotlin.src.main.review

import com.softsquared.template.kotlin.src.main.search.model.ReviewResponse
import com.softsquared.template.kotlin.src.main.search.model.StarPointResponse

interface ReviewActivityInterface {

    fun onGetUserSuccess(response: ReviewResponse)

    fun onGetUserFailure(message: String)

    fun onPostUserSuccess(response: ReviewResponse)

    fun onPostUserFailure(message: String)
}
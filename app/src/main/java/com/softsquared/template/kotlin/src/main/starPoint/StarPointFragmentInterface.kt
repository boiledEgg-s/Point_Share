package com.softsquared.template.kotlin.src.main.starPoint

import com.softsquared.template.kotlin.src.main.search.model.StarPointResponse

interface StarPointFragmentInterface {

    fun onGetUserSuccess(response: StarPointResponse)

    fun onGetUserFailure(message: String)

}
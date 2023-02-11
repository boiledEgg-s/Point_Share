package com.softsquared.template.kotlin.src.main.search

import com.softsquared.template.kotlin.src.main.search.model.StarPointResponse

interface SearchResultFragmentInterface {

    fun onGetUserSuccess(response: StarPointResponse)

    fun onGetUserFailure(message: String)

}
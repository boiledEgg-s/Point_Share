package com.softsquared.template.kotlin.src.main.starPoint

import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.src.main.search.model.StarPointResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StarPointService (val starPointFragmentInterface: StarPointFragmentInterface){
    fun tryGetPoints(keyword:String?=null, order:String?=null, pageId:String){
        val service = ApplicationClass.sRetrofit.create(StarPointRetrofitInterface::class.java)
        service.getReviews(keyword, order, pageId).enqueue(object : Callback<StarPointResponse> {
            override fun onResponse(call: Call<StarPointResponse>, response: Response<StarPointResponse>) {
                starPointFragmentInterface.onGetUserSuccess(response.body() as StarPointResponse)
            }

            override fun onFailure(call: Call<StarPointResponse>, t: Throwable) {
                starPointFragmentInterface.onGetUserFailure(t.message ?: "통신 오류")
            }
        })
    }

}
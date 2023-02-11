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
import com.softsquared.template.kotlin.src.retrofit.model.*

interface RetrofitClassInterface{

    //7. 유저 조회
    fun onGetUserSuccess(response: UserResponse){
        val message = response.message
        Log.w("RETROFIT SUCCESS","onGetUserSuccess, $message")
    }
    fun onGetUserFailure(message: String){
        Log.w("RETROFIT FAILURE","onGetUserFailure, $message")
    }

    //14. 모든 포인트 조회
    fun onGetPointsSuccess(response: PointResponse){
        val message = response.message
        Log.w("RETROFIT SUCCESS","onGetPointsSuccess, $message")
    }
    fun onGetPointsFailure(message: String){
        Log.w("RETROFIT FAILURE","onGetPointsFailure, $message")
    }

    //15.특정 포인트 조회
    fun onGetSpecificPointSuccess(response: PointResponse){
        val message = response.message
        Log.w("RETROFIT SUCCESS","onGetSpecificPointSuccess, $message")
    }
    fun onGetSpecificPointFailure(message: String){
        Log.w("RETROFIT FAILURE","onGetSpecificPointFailure, $message")
    }

    //16. 포인트 등록
    fun onPostPointSuccess(message: String){
        Log.w("RETROFIT SUCCESS","onPostPointSuccess, $message")
    }

    fun onPostPointFailure(message: String){
        Log.w("RETROFIT FAILURE","onPostPointFailure, $message")
    }
}
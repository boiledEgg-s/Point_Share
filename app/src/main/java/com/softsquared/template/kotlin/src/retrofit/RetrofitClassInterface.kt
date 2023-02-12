package com.softsquared.template.kotlin.src.retrofit
/*
7. 유저 조회
8. 유저 정보 수정
11. 특정 포인트에 좋아요 표시
12. 특정 포인트에 좋아요 취소
14. 모든 포인트 조회
15. 특정 포인트 조회
16. 포인트 등록


19. 유저의 포인트 조회
20. 유저가 좋아요한 포인트 조회
 */

import android.util.Log
import com.softsquared.template.kotlin.config.BaseResponse
import com.softsquared.template.kotlin.src.retrofit.response.*

interface RetrofitClassInterface{

    //7. 유저 조회
    fun onGetUserSuccess(response: UserResponse){
        val message = response.message
        Log.w("RETROFIT SUCCESS","onGetUserSuccess, $message")
    }
    fun onGetUserFailure(message: String){
        Log.w("RETROFIT FAILURE","onGetUserFailure, $message")
    }

    //8. 유저 정보 수정
    fun onPutUserSuccess(response: PutUserResponse){
        val message = response.message
        Log.w("RETROFIT SUCCESS","onPutUserSuccess, $message")
    }
    fun onPutUserFailure(message: String){
        Log.w("RETROFIT FAILURE","onPutUserFailure, $message")
    }

    //11. 특정 포인트에 좋아요 표시
    fun onPostLikeSuccess(response: BaseResponse){
        val message = response.message
        Log.w("RETROFIT SUCCESS","onPostLikeSuccess, $message")
    }
    fun onPostLikeFailure(message: String){
        Log.w("RETROFIT FAILURE","onPostLikeFailure, $message")
    }

    //12. 특정 포인트에 좋아요 취소
    fun onDeleteLikeSuccess(response: BaseResponse){
        val message = response.message
        Log.w("RETROFIT SUCCESS","onDeleteLikeSuccess, $message")
    }
    fun onDeleteLikeFailure(message: String){
        Log.w("RETROFIT FAILURE","onDeleteLikeFailure, $message")
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

    //19. 유저 작성 포인트 조회
    fun onGetUserPointSuccess(response: UserPointResponse){
        val message = response.message
        Log.w("RETROFIT SUCCESS","onGetUserPointSuccess, $message")
    }
    fun onGetUserPointFailure(message: String){
        Log.w("RETROFIT FAILURE","onGetUserPointFailure, $message")
    }

    //20. 유저가 좋아요한 포인트 조회
    fun onGetUserLikeSuccess(response: UserLikeResponse){
        val message = response.message
        Log.w("RETROFIT SUCCESS","onGetUserLikeSuccess, $message")
    }
    fun onGetUserLikeFailure(message: String){
        Log.w("RETROFIT FAILURE","onGetUserLikeFailure, $message")
    }
}
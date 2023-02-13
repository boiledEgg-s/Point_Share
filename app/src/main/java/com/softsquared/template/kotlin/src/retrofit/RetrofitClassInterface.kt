package com.softsquared.template.kotlin.src.retrofit
/*
7. 유저 조회
8. 유저 정보 수정
11. 특정 포인트에 좋아요 표시
12. 특정 포인트에 좋아요 취소
14. 모든 포인트 조회
15. 특정 포인트 조회
16. 포인트 등록
17. 특정 포인트 수정
18. 특정 포인트 삭제
19. 유저의 포인트 조회
20. 유저가 좋아요한 포인트 조회
 */

import android.util.Log
import com.softsquared.template.kotlin.config.BaseResponse
import com.softsquared.template.kotlin.src.retrofit.response.*

interface RetrofitClassInterface{

    //6. 유저 닉네임 중복 조회
    fun onGetNameCheckSuccess(response: NameCheckResponse){
        val message = response.message
        Log.w("RETROFIT SUCCESS","onGetNameCheckSuccess, $message")
    }
    fun onGetNameCheckFailure(message: String){
        Log.w("RETROFIT FAILURE","onGetNameCheckFailure, $message")
    }

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

    //17. 특정 포인트 수정
    fun onPutPointSuccess(message: String){
        Log.w("RETROFIT SUCCESS","onPutPointSuccess, $message")
    }

    fun onPutPointFailure(message: String){
        Log.w("RETROFIT FAILURE","onPutPointFailure, $message")
    }

    //18. 특정 포인트 삭제
    fun onDeletePointSuccess(response: UpdatePointResponse){
        val message = response.message
        Log.w("RETROFIT SUCCESS","onGetSpecificPointSuccess, $message")
    }

    fun onDeletePointFailure(message: String){
        Log.w("RETROFIT FAILURE","onDeletePointFailure, $message")
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

    //21. 특정 위치의 포인트 정보 조회 : 제목, 어종, 해루질 종류, 좋아요 수
    fun onGetMapMarkSuccess(response: GetMarkResponse){
        val message = response.message
        Log.w("RETROFIT SUCCESS","onGetMapMarkSuccess, $message")
    }
    fun onGetMapMarkFailure(message: String){
        Log.w("RETROFIT FAILURE","onGetMapMarkFailure, $message")
    }
}
package com.softsquared.template.kotlin.src.main.login

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.kakao.sdk.user.UserApiClient
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.config.BaseActivity
import com.softsquared.template.kotlin.databinding.RegisterLoginBinding
import com.softsquared.template.kotlin.src.main.MainActivity
import com.softsquared.template.kotlin.src.main.review.NewReviewActivity
import com.softsquared.template.kotlin.src.main.search.SearchRequestFragment
import com.softsquared.template.kotlin.src.main.search.SearchResultFragment
import com.softsquared.template.kotlin.src.retrofit.RetrofitClassInterface
import com.softsquared.template.kotlin.src.retrofit.RetrofitService
import com.softsquared.template.kotlin.src.retrofit.response.NameCheckResponse

class RegisterLogin:BaseActivity<RegisterLoginBinding> (RegisterLoginBinding::inflate), RetrofitClassInterface{

    private val service = RetrofitService(this)
    private var check = false
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        // EditText 키보드 이벤트 = 중복확인 아이콘 클릭 이벤트
        binding.searchNickname.setOnEditorActionListener(getEditorActionListener(binding.doubleCheck))

        // 검색창 터치 이벤트
        binding.searchNickname.setOnClickListener {
            binding.searchNickname.isCursorVisible = true
        }

        binding.searchNickname.addTextChangedListener(object: TW(){
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                check = false
            }
        })

        // 중복확인 아이콘 클릭 이벤트
        binding.doubleCheck.setOnClickListener {
            if(binding.searchNickname.text.toString().equals("seongyeop")){
                check = true
                super.showCustomToast("닉네임이 중복되지 않습니다.")
            }   else service.tryCheckName(binding.searchNickname.text.toString())
        }
        binding.registerNextButton.setOnClickListener{
            Log.w("##############################press button check", check.toString())
            if(check) {
                startActivity(Intent(this, CompleteRegister::class.java))
                finish()
            }
        }
    }



    // 키보드에서 done(완료) 클릭 시 , 원하는 뷰 클릭되게 하는 메소드
    fun getEditorActionListener(view: View): TextView.OnEditorActionListener {
        return TextView.OnEditorActionListener { textView, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                view.callOnClick()
            }
            false
        }
    }

    override fun onGetNameCheckSuccess(response: NameCheckResponse) {
        super.onGetNameCheckSuccess(response)
        check = !response.result
        super.showCustomToast(response.message.toString())
        Log.w("------------check", check.toString())
    }

    abstract class TW: TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int){}

        override fun afterTextChanged(s: Editable?) {}

    }

}
package com.softsquared.template.kotlin.src.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import com.kakao.sdk.user.UserApiClient
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.BaseActivity
import com.softsquared.template.kotlin.databinding.ActivityMainBinding
import com.softsquared.template.kotlin.src.main.map.MapFragment
import com.softsquared.template.kotlin.src.main.myPage.MyPageFragment
import com.softsquared.template.kotlin.src.main.starPoint.StarPointFragment

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //회원탈퇴
        UserApiClient.instance.unlink { error ->
            if (error != null) {
                Log.d("카카오로그인","회원 탈퇴 실패")
            }else {
                Log.d("카카오로그인","회원 탈퇴 성공")
            }
        }

        //supportFragmentManager.beginTransaction().replace(R.id.main_frm, MyPageFragment()).commitAllowingStateLoss()

        binding.mainBtmNav.run {
            setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.menu_main_btm_nav_home -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frm, MapFragment())
                            .commitAllowingStateLoss()
                    }

                    R.id.menu_main_btm_nav_popular_point -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frm, StarPointFragment())
                            .commitAllowingStateLoss()
                    }

                    R.id.menu_main_btm_nav_my_point -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frm, MyPageFragment())
                            .commitAllowingStateLoss()
                    }
                }
                true
            }
            selectedItemId = R.id.menu_main_btm_nav_home
        }
    }

}
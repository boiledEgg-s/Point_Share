package com.softsquared.template.kotlin.src.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.kakao.sdk.user.UserApiClient
import com.softsquared.template.kotlin.config.BaseActivity
import com.softsquared.template.kotlin.databinding.ActivitySplashBinding
import com.softsquared.template.kotlin.src.main.MainActivity
import com.softsquared.template.kotlin.src.main.login.LoginActivity
import com.softsquared.template.kotlin.src.permission.PermissionActivity
import java.security.Permission

class SplashActivity : BaseActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        Handler(Looper.getMainLooper()).postDelayed({
            //startActivity(Intent(this, LoginActivity::class.java))
            UserApiClient.instance.accessTokenInfo { token, error ->
                if (error != null) {
                    Log.e("TAG", "로그인 실패", error)
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                } else if (token != null) {
                    Log.i("TAG", "로그인 성공 $token")
                    startActivity(Intent(this, PermissionActivity::class.java))
                    finish()
                }
            }
        }, 1500)
    }
}
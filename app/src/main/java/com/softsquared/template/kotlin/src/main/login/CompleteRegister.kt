package com.softsquared.template.kotlin.src.main.login


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.softsquared.template.kotlin.config.BaseActivity
import com.softsquared.template.kotlin.databinding.CompleteRegisterBinding
import com.softsquared.template.kotlin.databinding.TermsOfUseBinding


class CompleteRegister: BaseActivity<CompleteRegisterBinding>(CompleteRegisterBinding::inflate) {

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.buttonLogin.setOnClickListener{
            val intent = Intent(this, CompleteLogin::class.java)
            startActivity(intent)
            finish()
        }
    }
}

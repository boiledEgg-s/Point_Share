package com.softsquared.template.kotlin.src.main.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.softsquared.template.kotlin.config.BaseActivity
import com.softsquared.template.kotlin.databinding.CompleteLoginBinding
import com.softsquared.template.kotlin.databinding.TermsOfUseBinding

class CompleteLogin: BaseActivity<CompleteLoginBinding>(CompleteLoginBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.nextButton.setOnClickListener{
            val intent = Intent(this, TermOfUse::class.java)
            startActivity(intent)
            finish()
        }
    }
}


package com.softsquared.template.kotlin.src.main.login

import android.content.Intent
import android.os.Bundle
import com.softsquared.template.kotlin.config.BaseActivity
import com.softsquared.template.kotlin.databinding.TermsOfUseBinding
import com.softsquared.template.kotlin.src.permission.PermissionActivity

class TermOfUse : BaseActivity<TermsOfUseBinding>(TermsOfUseBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.button.setOnClickListener {
            val intent = Intent(this, PermissionActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
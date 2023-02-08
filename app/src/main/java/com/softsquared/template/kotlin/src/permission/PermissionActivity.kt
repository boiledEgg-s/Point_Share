package com.softsquared.template.kotlin.src.permission

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.softsquared.template.kotlin.config.BaseActivity
import com.softsquared.template.kotlin.databinding.ActivityPermissionBinding
import com.softsquared.template.kotlin.src.main.MainActivity
import com.softsquared.template.kotlin.src.splash.SplashActivity

class PermissionActivity : BaseActivity<ActivityPermissionBinding>(ActivityPermissionBinding::inflate) {

    private var PERMISSIONS_REQUEST_CODE = 100

    private var REQUIRED_PERMISSIONS = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION
    )



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkPermission()
    }

    private fun checkPermission(){
        val permissionCheck_Location = ContextCompat.checkSelfPermission(this,
            Manifest.permission.ACCESS_FINE_LOCATION
        )


        if (permissionCheck_Location != PackageManager.PERMISSION_GRANTED) {
            if(permissionCheck_Location != PackageManager.PERMISSION_GRANTED)
                ActivityCompat.requestPermissions(
                    this,
                    REQUIRED_PERMISSIONS,
                    PERMISSIONS_REQUEST_CODE
                )

        } else {
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent, null)
            finish()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            for (i in grantResults.indices) {
                //허용됬다면
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    var intent = Intent(this, MainActivity::class.java)
                    startActivity(intent, null)
                    finish()
                } else {
                    //권한을 하나라도 허용하지 않는다면 앱 종료
                    Toast.makeText(applicationContext, "앱권한설정하세요", Toast.LENGTH_LONG).show()
                    finish()
                }
            }
        }
    }
}
package com.softsquared.template.kotlin.src.main.review

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.CompoundButton
import android.widget.ImageView
import androidx.viewpager2.widget.ViewPager2
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.config.BaseActivity
import com.softsquared.template.kotlin.databinding.ActivityReviewBinding
import com.softsquared.template.kotlin.src.retrofit.RetrofitClassInterface
import com.softsquared.template.kotlin.src.retrofit.RetrofitService
import com.softsquared.template.kotlin.src.retrofit.response.PointResponse
import com.softsquared.template.kotlin.src.retrofit.response.UserResponse
import java.net.URL

class ReviewActivity : BaseActivity<ActivityReviewBinding>(ActivityReviewBinding::inflate), RetrofitClassInterface {

    private val service = RetrofitService(this)
    private var imageFiles = arrayListOf<String>()
    lateinit var sharedPref: SharedPreferences
    lateinit var editPref: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //객체 인텐트로 받기
        //val data = intent.getSerializableExtra("data", ReviewDTO::class.java) as ReviewDTO

        val pointId = intent.getStringExtra("pointId") as String
        val userId = intent.getStringExtra("userId") as String
        Log.d("ReviewActivity", "pointId:$pointId, userId:$userId")
        initialization(pointId)



        service.tryGetSpecificPoint(pointId)
        service.tryGetUser(userId)    //userId 넣기!!


        binding.reviewBtnLike.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) {
                service.tryPostLike(userId, pointId)
                editPref.putBoolean(pointId, true)
                editPref.apply() // SharedPreferences 적용
            } else {
                service.tryDeleteLike(userId, pointId)
                editPref.remove(pointId)
                editPref.apply() // SharedPreferences 적용
            }

        }

        // RecyclerView.Adapter<ViewHolder>()
        binding.viewPager.adapter = VPRecyclerAdapter(imageFiles)
        // ViewPager의 Paging 방향은 Horizontal
        binding.viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            // Paging 완료되면 호출
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                Log.d("ViewPagerFragment", "Page ${position+1}")
            }
        })

    }

    private fun initialization(pointId:String) {
        sharedPref = ApplicationClass.sSharedPreferences
        editPref = sharedPref.edit()
        val stringPref = sharedPref.getBoolean(pointId, false)

        // SharedPreferences 데이터가 있으면 String을 ArrayList로 변환
        // fromJson → json 형태의 문자열을 명시한 객체로 변환(두번째 인자)
        if (stringPref) {
            binding.reviewBtnLike.isChecked = true
        }
    }

    /********************API RELATED CODES**********************/
    override fun onGetUserSuccess(response: UserResponse) {
        val data = response.result
        if(response.isSuccess) {
            Log.d("ReviewActivity API getUser success", "${data.userId}")
            Thread() {
                try {
                    val url = URL(data.image_url)
                    val stream = url.openStream()

                    val bmp = BitmapFactory.decodeStream(stream)

                    Log.d("INSIDE THREAD", "GETTING PHOTO ${data.image_url}")
                    Log.d("INSIDE THREAD", "GETTING URL $url")
                    Log.d("INSIDE THREAD", "GETTING BMP $bmp")
                    Log.d("INSIDE THREAD", "GETTING STREAM $stream")

                    Handler(Looper.getMainLooper()).post {
                        binding.reviewIvProfileImg.setImageBitmap(bmp)
                        binding.reviewIvProfileImg.scaleType = ImageView.ScaleType.CENTER_CROP
                    }
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }
                return@Thread
            }.start()
            binding.reviewTvUserName.text = data.nickname!!.replace("\"", "")
        } else{
            Log.e("ReviewActivity API getUser success", "${response.message}")
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onGetSpecificPointSuccess(response: PointResponse) {
        super.onGetSpecificPointSuccess(response)
        val data = response.result[0]
        // 데이터가 안 넘어올 수 있어서 체
        if (data != null) {
            Log.d("ReviewActivity API getReview success", "NOT NULL")
            binding.reviewTvLike.text = data.likes.toString()
            binding.reviewTvLocation.text = data.location!!.replace("\"", "")
            binding.reviewTvDate.text = data.point_date!!.replace("\"", "")
            binding.reviewTvTag1.text = data.point_type!!.replace("\"", "")
            binding.reviewTvTag2.text = data.creature!!.replace("\"", "")
            binding.reviewTvTitle.text = data.title!!.replace("\"", "")
            binding.reviewTvContent.text = data.content!!.replace("\"", "")

            if(data.point_image_list != null) {
                val img = data.point_image_list!!.split(",")
                for(element in img) {
                    imageFiles.add(
                        element
                    )
                }
                binding.viewPager.adapter!!.notifyDataSetChanged()
            }
        } else {
            if (data == null)
            //val list: List<>()
                Log.w("ReviewActivity API getReview success", "NULLPTR")
            else
                Log.w("ReviewActivity API getReview success", "NOT NULL, NOT SUCCESSFUL")
        }
    }

    override fun onGetSpecificPointFailure(message: String) {
        super.onGetSpecificPointFailure(message)
        binding.reviewIvProfileImg.setImageResource(R.drawable.user_icon_default)
    }
}
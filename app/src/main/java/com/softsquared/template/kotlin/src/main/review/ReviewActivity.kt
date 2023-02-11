package com.softsquared.template.kotlin.src.main.review

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.ImageView
import androidx.viewpager2.widget.ViewPager2
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.config.BaseActivity
import com.softsquared.template.kotlin.databinding.ActivityReviewBinding
import com.softsquared.template.kotlin.src.retrofit.RetrofitClassInterface
import com.softsquared.template.kotlin.src.retrofit.RetrofitService
import com.softsquared.template.kotlin.src.retrofit.model.*
import java.net.URL

class ReviewActivity : BaseActivity<ActivityReviewBinding>(ActivityReviewBinding::inflate), RetrofitClassInterface {
    private val service = RetrofitService(this)
    private var imageFiles = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //temporary



        binding.reviewIvProfileImg.setImageResource(R.drawable.user_icon_default)

        val str = ApplicationClass.sSharedPreferences.getString("profileImg_String", null)

        Log.d("REVIEW ACTIVITY PHOTO TYPE CHECKING", "str ${str.toString()}")

        //객체 인텐트로 받기
//        val data = intent.getSerializableExtra("data", ReviewDTO::class.java) as ReviewDTO


        val pointId = intent.getStringExtra("pointId") as String
        val userId = intent.getStringExtra("userId") as String
        Log.d("ReviewActivity", "pointId:$pointId, userId:$userId")
        service.tryGetSpecificPoint(pointId)
        service.tryGetUser("2646841646")    //userId 넣기!!


        binding.reviewBtnLike.setOnCheckedChangeListener { buttonView, isChecked ->
            when (isChecked) {
                true -> {
                }
                false -> {
                }
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
                        binding.reviewIvProfileImg.clipToOutline = true
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

    override fun onGetUserFailure(message: String) {
        Log.e("ReviewActivity API getUser failure", "$message")
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
            binding.reviewTvDate.text = data.point_date!!.replace("\"", "").subSequence(0,10)
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
}
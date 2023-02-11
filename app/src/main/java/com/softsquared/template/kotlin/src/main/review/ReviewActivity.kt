package com.softsquared.template.kotlin.src.main.review

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.viewpager2.widget.ViewPager2
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.config.BaseActivity
import com.softsquared.template.kotlin.databinding.ActivityReviewBinding
import com.softsquared.template.kotlin.src.main.search.model.ReviewDTO
import com.softsquared.template.kotlin.src.main.search.model.ReviewResponse
import com.softsquared.template.kotlin.src.main.search.model.SearchResultDTO
import com.softsquared.template.kotlin.src.main.search.model.StarPointResponse
import retrofit2.http.Url
import java.io.File

class ReviewActivity : BaseActivity<ActivityReviewBinding>(ActivityReviewBinding::inflate), ReviewActivityInterface {
    private val service = ReviewService(this)
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
        Log.d("ReviewActivity", "$pointId")
        service.tryGetPoints(pointId)


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

    @SuppressLint("NotifyDataSetChanged")
    override fun onGetUserSuccess(response: ReviewResponse) {
        val data = response.result[0]
        Log.d("Retrofit2 in SearchResultFragment", "onGetUser Success")
        // 데이터가 안 넘어올 수 있어서 체
        if (data != null) {
            Log.d("Retrofit2 in SearchResultFragment", "enter init")
            binding.reviewTvUserName.text = data.nickname!!.replace("\"", "")
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
                Log.w("Retrofit2", "NULLPTR Response Not Successful ${response.code}")
            else
                Log.w("Retrofit2", "NOTNULL Response Not Successful ${response.code}")
        }
    }

    override fun onGetUserFailure(message: String) {
        Log.e("Retrofit2", "$message")
    }

    override fun onPostUserSuccess(response: ReviewResponse) {}

    override fun onPostUserFailure(message: String) {}

}
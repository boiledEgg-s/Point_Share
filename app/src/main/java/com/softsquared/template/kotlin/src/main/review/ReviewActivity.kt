package com.softsquared.template.kotlin.src.main.review

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.viewpager2.widget.ViewPager2
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.BaseActivity
import com.softsquared.template.kotlin.databinding.ActivityReviewBinding

class ReviewActivity : BaseActivity<ActivityReviewBinding>(ActivityReviewBinding::inflate) {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //temporary
        var bgColors = arrayListOf<Int>(
            R.color.black,
            R.color.deep_grey,
            R.color.shadow,
            R.color.lightgrey,
            R.color.brightgrey
        )

        //객체 인텐트로 받기
        val data = intent.getSerializableExtra("data", ReviewItem::class.java) as ReviewItem

        binding.reviewIvProfileImg.setImageResource(R.drawable.user_icon_default)
        binding.reviewTvUserName.text = "사용자 이름"
        binding.reviewTvLike.text = data.likes.toString()
        binding.reviewTvLocation.text = data.loc
        binding.reviewTvDate.text = data.regDate
        binding.reviewTvTag1.text = "tag1"
        binding.reviewTvTag2.text = "tag2"
        binding.reviewTvTitle.text = data.name
        binding.reviewTvContent.text = data.content

        binding.reviewBtnLike.setOnCheckedChangeListener { buttonView, isChecked ->
            when (isChecked) {
                true -> {
                }
                false -> {
                }
            }
        }

        // RecyclerView.Adapter<ViewHolder>()
        binding.viewPager.adapter = VPRecyclerAdapter(bgColors)
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


}
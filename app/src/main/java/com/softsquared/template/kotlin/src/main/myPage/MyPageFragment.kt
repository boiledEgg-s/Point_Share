package com.softsquared.template.kotlin.src.main.myPage

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.BaseFragment
import com.softsquared.template.kotlin.databinding.FragmentMyPageBinding
import com.softsquared.template.kotlin.src.main.review.ReviewItem
import com.softsquared.template.kotlin.src.main.review.ReviewRVAdapter

class MyPageFragment :
    BaseFragment<FragmentMyPageBinding>(FragmentMyPageBinding::bind, R.layout.fragment_my_page) {
    var reviewItems:ArrayList<ReviewItem> = arrayListOf()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var i=0;
        while(i<20){
            reviewItems.apply {
                this.add(
                    ReviewItem(
                        "profileImg",
                        ('a'+i).toString()+i.toString(),
                        ('a'+i).toString()+('a'+i).toString()+('a'+i).toString(),
                        "image",
                        i,
                        ('a'+i).toString()+('a'+i).toString()+"_loc",
                        i.toString()+i.toString()+i.toString()+i.toString()+"/"+i.toString()+i.toString()+"/"+i.toString()+i.toString()
                    )
                )
            }
            i++
        }

        val reviewRVAdapter = MyPageRVAdapter(reviewItems)
        reviewRVAdapter.notifyItemInserted(reviewItems.size)
        binding.myPageRv.adapter = reviewRVAdapter
        binding.myPageRv.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.myPageNumLike.text = reviewRVAdapter.itemCount.toString()
        binding.myPageNumReview.text = reviewRVAdapter.itemCount.toString()
    }
}
package com.softsquared.template.kotlin.src.main.search

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.softsquared.template.kotlin.config.BaseActivity
import com.softsquared.template.kotlin.databinding.ActivitySearchResultBinding
import com.softsquared.template.kotlin.src.main.review.ReviewItem
import com.softsquared.template.kotlin.src.main.review.ReviewRVAdapter

class SearchResultActivity : BaseActivity<ActivitySearchResultBinding>(ActivitySearchResultBinding::inflate){

    var reviewItems:ArrayList<ReviewItem> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding.mapIvBack.setOnClickListener{
            finish()
        }

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

        val reviewRVAdapter = ReviewRVAdapter(reviewItems)
        reviewRVAdapter.notifyItemInserted(reviewItems.size)
        binding.searchResultRv.adapter = reviewRVAdapter
        binding.searchResultRv.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
    }
}
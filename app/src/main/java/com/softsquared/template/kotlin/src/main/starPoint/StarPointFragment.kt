package com.softsquared.template.kotlin.src.main.starPoint

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.BaseFragment
import com.softsquared.template.kotlin.databinding.FragmentStarPointBinding
import com.softsquared.template.kotlin.src.main.review.*

class StarPointFragment :
    BaseFragment<FragmentStarPointBinding>(FragmentStarPointBinding::bind, R.layout.fragment_star_point) {

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

        val reviewRVAdapter = ReviewRVAdapter(reviewItems)
        reviewRVAdapter.notifyItemInserted(reviewItems.size)
        binding.starReviewRv.adapter = reviewRVAdapter
        binding.starReviewRv.layoutManager =
            LinearLayoutManager(activity, RecyclerView.VERTICAL, false)

        binding.starReviewRv.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastVisibleItemPosition =
                    (binding.starReviewRv.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
                val itemTotalCount = binding.starReviewRv.adapter?.itemCount
                if (lastVisibleItemPosition + 1 >= reviewItems.size) {
                    //리스트 마지막(바닥) 도착!!!!! 다음 페이지 데이터 로드!!
                    Log.d("from recycler view", "reached end")
                }
            }
        })
    }


}
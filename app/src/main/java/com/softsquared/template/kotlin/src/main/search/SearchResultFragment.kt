package com.softsquared.template.kotlin.src.main.search

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.BaseFragment
import com.softsquared.template.kotlin.databinding.FragmentSearchResultBinding
import com.softsquared.template.kotlin.src.main.review.ReviewItem
import com.softsquared.template.kotlin.src.main.review.ReviewRVAdapter

class SearchResultFragment :
    BaseFragment<FragmentSearchResultBinding>(FragmentSearchResultBinding::bind, R.layout.fragment_search_result) {

    lateinit var searchStr:String
    lateinit var reviewRVAdapter:ReviewRVAdapter
    var reviewItems:ArrayList<ReviewItem> = arrayListOf()

    @SuppressLint("NotifyDataSetChanged", "ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchStr = (activity as SearchActivity).binding.mapEtSearch.text.toString()
        (activity as SearchActivity).binding.mapEtSearch.isCursorVisible = false


        //
        (activity as SearchActivity).binding.mapIvBack.setOnClickListener{
            (activity as SearchActivity).supportFragmentManager.popBackStackImmediate()
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

        setRecyclerView(reviewItems)
    }

    fun setRecyclerView(arr:ArrayList<ReviewItem>){
        reviewRVAdapter = ReviewRVAdapter(arr)
        reviewRVAdapter.notifyItemInserted(reviewItems.size)
        binding.searchResultRv.adapter = reviewRVAdapter
        binding.searchResultRv.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
    }

}
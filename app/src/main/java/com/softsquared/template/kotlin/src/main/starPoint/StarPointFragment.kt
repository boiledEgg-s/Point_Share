package com.softsquared.template.kotlin.src.main.starPoint

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.BaseFragment
import com.softsquared.template.kotlin.databinding.FragmentStarPointBinding
import com.softsquared.template.kotlin.src.main.search.model.StarPointDTO
import com.softsquared.template.kotlin.src.main.search.model.StarPointResponse

class StarPointFragment :
    BaseFragment<FragmentStarPointBinding>(FragmentStarPointBinding::bind, R.layout.fragment_star_point), StarPointFragmentInterface {

    private var reviewItems:ArrayList<StarPointDTO> = arrayListOf()
    private val service: StarPointService = StarPointService(this)
    private var pageId = 1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val reviewRVAdapter = StarPointRVAdapter(reviewItems)
        reviewRVAdapter.notifyItemInserted(reviewItems.size)
        binding.starReviewRv.adapter = reviewRVAdapter
        binding.starReviewRv.layoutManager =
            LinearLayoutManager(activity, RecyclerView.VERTICAL, false)

        service.tryGetPoints(null, null, pageId.toString())

        binding.starReviewRv.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastVisibleItemPosition =
                    (binding.starReviewRv.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
                val itemTotalCount = binding.starReviewRv.adapter?.itemCount
                if (lastVisibleItemPosition + 1 >= reviewItems.size) {
                    //리스트 마지막(바닥) 도착!!!!! 다음 페이지 데이터 로드!!
                    Log.d("from recycler view", "reached end")
                    service.tryGetPoints(null, null, (pageId++).toString())

                }
            }
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onGetUserSuccess(response: StarPointResponse) {
        val responseData = response.result
        // 데이터가 안 넘어올 수 있어서 체
        if (responseData != null) {
            Log.d("Retrofit2 in SearchResultFragment", "onGetUser Success")
            for(review in responseData)
            {
                Log.d("GetReview by Retrofit", "${review.point_id}")
            }
            for(review in responseData){
                reviewItems.apply{
                    this.add(
                        StarPointDTO(review.point_id, review.title, review.point_image_list, review.nickname,
                            review.point_type, review.creature, review.point_date, review.content, review.likes,
                            review.location, review.latitude, review.longitude)
                    )}
            }
            binding.starReviewRv.adapter?.notifyDataSetChanged()
            Log.d("Retrofit2 in SearchResultFragment", "entered, review size = ${reviewItems.size}")
        } else {
            if (responseData == null)
            //val list: List<>()
                Log.w("Retrofit2", "NULLPTR Response Not Successful ${response.code}")
            else
                Log.w("Retrofit2", "NOTNULL Response Not Successful ${response.code}")
        }
    }

    override fun onGetUserFailure(message: String) {
        Log.e("Retrofit2", "$message")
    }


}
package com.softsquared.template.kotlin.src.main.search

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.BaseFragment
import com.softsquared.template.kotlin.databinding.FragmentSearchResultBinding
import com.softsquared.template.kotlin.src.main.MainActivity
import com.softsquared.template.kotlin.src.retrofit.RetrofitClassInterface
import com.softsquared.template.kotlin.src.retrofit.RetrofitService
import com.softsquared.template.kotlin.src.retrofit.model.GetPointDTO
import com.softsquared.template.kotlin.src.retrofit.model.PointResponse

class SearchResultFragment :
    BaseFragment<FragmentSearchResultBinding>(FragmentSearchResultBinding::bind, R.layout.fragment_search_result), RetrofitClassInterface{

    private lateinit var searchStr:String
    private var order:String = "추천순"
    lateinit var reviewRVAdapter: SearchResultRVAdapter
    var reviewItems:ArrayList<GetPointDTO> = arrayListOf()
    private var service = RetrofitService(this)
    private var pageId = 1


    @SuppressLint("NotifyDataSetChanged", "ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //리사이클러뷰 초기화
        reviewRVAdapter = SearchResultRVAdapter(reviewItems)
        reviewRVAdapter.notifyItemInserted(reviewItems.size)
        binding.searchResultRv.adapter = reviewRVAdapter
        binding.searchResultRv.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

        searchStr = (activity as SearchActivity).binding.mapEtSearch.text.toString()
        (activity as SearchActivity).binding.mapEtSearch.isCursorVisible = false

        //레트로핏 관련 서비스 객체 생성
        service.tryGetPoints(searchStr, null, pageId.toString())



        //
        (activity as SearchActivity).binding.mapIvBack.setOnClickListener{
            (activity as SearchActivity).supportFragmentManager.popBackStackImmediate()
        }

        //페이지의 끝에 도달한 경우
        binding.searchResultRv.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastVisibleItemPosition =
                    (binding.searchResultRv.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
                val itemTotalCount = binding.searchResultRv.adapter?.itemCount
                if (lastVisibleItemPosition + 1 >= reviewItems.size) {
                    //리스트 마지막(바닥) 도착!!!!! 다음 페이지 데이터 로드!!
                    Log.d("from recycler view", "reached end")
                    service.tryGetPoints(searchStr, order, (pageId++).toString())
                }
            }
        })

    }


    @SuppressLint("NotifyDataSetChanged")
    override fun onGetPointsSuccess(response: PointResponse) {
        val responseData = response.result
        // 데이터가 안 넘어올 수 있어서 체
        if (response.isSuccess) {
            Log.d("Retrofit2 in SearchResultFragment", "onGetUser Success")
            for(review in responseData)
            {
                Log.d("GetReview by Retrofit", "${review.point_id}")
            }
            for(review in responseData){
                reviewItems.apply{
                    this.add(
                        GetPointDTO(review.user_id, review.point_id, review.title, review.point_image_list, review.nickname,
                        review.point_type, review.creature, review.point_date, review.content, review.likes,
                        review.location, review.latitude, review.longitude)
                )}
            }
            binding.searchResultRv.adapter?.notifyDataSetChanged()
            Log.d("Retrofit2 in SearchResultFragment", "entered, review size = ${reviewItems.size}")
        }
    }

}
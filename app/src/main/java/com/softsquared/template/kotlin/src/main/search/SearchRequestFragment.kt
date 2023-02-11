package com.softsquared.template.kotlin.src.main.search

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.BaseFragment
import com.softsquared.template.kotlin.databinding.FragmentSearchRequestBinding

class SearchRequestFragment :
    BaseFragment<FragmentSearchRequestBinding>(FragmentSearchRequestBinding::bind, R.layout.fragment_search_request) {
    lateinit var searchRVAdapter: SearchRequestRVAdapter

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchRVAdapter = SearchRequestRVAdapter((activity as SearchActivity).searchArr,
            onClickDelete = { deleteSearchString(it)},
            onClickBtn = { sendSearchString(it) })
        binding.searchRv.adapter = searchRVAdapter
        binding.searchRv.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)


        binding.searchBtnDeleteAll.setOnClickListener{
            (activity as SearchActivity).searchArr.clear()
            searchRVAdapter.notifyDataSetChanged()
            (activity as SearchActivity).saveSharedPreference()
        }

        (activity as SearchActivity).binding.mapIvBack.setOnClickListener{
            (activity as SearchActivity).finish()
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    fun deleteSearchString(str: String) {
        (activity as SearchActivity).searchArr.remove(str)
        searchRVAdapter.notifyDataSetChanged()
        (activity as SearchActivity).saveSharedPreference()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun sendSearchString(str: String) {
//        (activity as SearchActivity).searchArr.remove(str)
//        (activity as SearchActivity).searchArr.add(
//            0, str
//        )
//        searchRVAdapter.notifyDataSetChanged()

        (activity as SearchActivity).addToSharedPreference(str)

        (activity as SearchActivity).binding.mapEtSearch.setText(str)

        (activity as SearchActivity).supportFragmentManager.beginTransaction()
            .replace(R.id.search_frame, SearchResultFragment())
            .commitAllowingStateLoss()
    }

}
package com.softsquared.template.kotlin.src.main.search

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.softsquared.template.kotlin.config.BaseActivity
import com.softsquared.template.kotlin.databinding.ActivitySearchBinding

class SearchActivity : BaseActivity<ActivitySearchBinding>(ActivitySearchBinding::inflate){
    private var searchArr:ArrayList<String> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.mapIvBack.setOnClickListener{
            finish()
        }

        binding.searchBtn.setOnClickListener{
            var intent = Intent(this, SearchResultActivity::class.java)
            startActivity(intent)
        }

        searchArr.apply {
            this.add("문어")
            this.add("가리비")
            this.add("방어")
            this.add("광어")
            this.add("서울")
        }


        val searchRVAdapter = SearchRVAdapter(searchArr)
        searchRVAdapter.notifyItemInserted(searchArr.size)
        binding.searchRv.adapter = searchRVAdapter
        binding.searchRv.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
    }
}
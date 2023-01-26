package com.softsquared.template.kotlin.src.main.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.softsquared.template.kotlin.databinding.SearchItemBinding
class SearchRVAdapter(
    private val dataList: ArrayList<String>
) : RecyclerView.Adapter<SearchRVAdapter.ViewHolder>(){

    inner class ViewHolder(val binding:SearchItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(data: String){
            binding.searchTv.text = data
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ViewHolder {
        val binding = SearchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int = dataList.size
}
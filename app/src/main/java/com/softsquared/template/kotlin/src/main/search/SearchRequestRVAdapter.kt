package com.softsquared.template.kotlin.src.main.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.softsquared.template.kotlin.databinding.SearchItemBinding

class SearchRequestRVAdapter(
    private val dataList: ArrayList<String>,
    val onClickDelete: (str: String) -> Unit,
    val onClickBtn: (str: String) -> Unit
) : RecyclerView.Adapter<SearchRequestRVAdapter.ViewHolder>(){

    inner class ViewHolder(val binding:SearchItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(data: String){
            binding.searchItemTv.text = data
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
        holder.binding.searchItemIv.setOnClickListener{
            onClickDelete.invoke(dataList[position])
        }
        holder.binding.searchItemTv.setOnClickListener{
            onClickBtn.invoke(dataList[position])
        }
    }

    override fun getItemCount(): Int = dataList.size


}
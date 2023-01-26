package com.softsquared.template.kotlin.src.main.review

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.softsquared.template.kotlin.databinding.ReviewItemBinding

class ReviewRVAdapter(
    private val dataList: List<ReviewItem>
) : RecyclerView.Adapter<ReviewRVAdapter.ItemViewHolder>(){

    inner class ItemViewHolder(val binding:ReviewItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(data: ReviewItem){
            //binding.itemIvProfile
            binding.itemTvName.text = data.name
            binding.itemTvContent.text = data.content
            //binding.itemIvImage
            binding.itemTvLike.text = "좋아요 "+data.likes.toString()+"개"
            binding.itemTvLocation.text = data.loc
            binding.itemTvTime.text = data.regDate
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ItemViewHolder {
        val binding = ReviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int = dataList.size
}
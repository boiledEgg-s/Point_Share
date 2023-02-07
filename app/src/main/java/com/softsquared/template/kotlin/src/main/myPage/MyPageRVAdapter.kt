package com.softsquared.template.kotlin.src.main.myPage

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.softsquared.template.kotlin.databinding.MyPageReviewItemBinding
import com.softsquared.template.kotlin.src.main.review.ReviewActivity
import com.softsquared.template.kotlin.src.main.review.ReviewItem

class MyPageRVAdapter(
    private val dataList: List<ReviewItem>
) : RecyclerView.Adapter<MyPageRVAdapter.ItemViewHolder>(){

    inner class ItemViewHolder(val binding:MyPageReviewItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(data: ReviewItem){
            //binding.itemIvProfile
            binding.myPageReviewTv1.text = data.name
            binding.myPageReviewTv2.text = data.regDate
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ItemViewHolder {
        val binding = MyPageReviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(dataList[position])

        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context, ReviewActivity::class.java)
            intent.putExtra("data", dataList[position])
            ContextCompat.startActivity(holder.itemView.context, intent, null)
        }
    }

    override fun getItemCount(): Int = dataList.size
}
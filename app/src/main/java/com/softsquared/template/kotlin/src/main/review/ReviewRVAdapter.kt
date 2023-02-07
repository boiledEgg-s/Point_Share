package com.softsquared.template.kotlin.src.main.review

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.softsquared.template.kotlin.databinding.ReviewItemBinding

class ReviewRVAdapter(
    private val dataList: List<ReviewItem>
) : RecyclerView.Adapter<ReviewRVAdapter.ItemViewHolder>(){
    lateinit var binding: ReviewItemBinding
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
        binding = ReviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(dataList[position])

        //리사이클러뷰 인텐트 보내기
        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context, ReviewActivity::class.java)
            intent.putExtra("data", dataList[position])
            ContextCompat.startActivity(holder.itemView.context, intent, null)
        }
    }

    override fun getItemCount(): Int = dataList.size


}
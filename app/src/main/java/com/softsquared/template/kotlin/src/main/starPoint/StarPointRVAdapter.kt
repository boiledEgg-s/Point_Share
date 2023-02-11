package com.softsquared.template.kotlin.src.main.starPoint

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.softsquared.template.kotlin.databinding.ReviewItemBinding
import com.softsquared.template.kotlin.src.main.review.ReviewActivity
import com.softsquared.template.kotlin.src.main.search.model.ReviewDTO
import com.softsquared.template.kotlin.src.main.search.model.StarPointDTO

class StarPointRVAdapter(
    private val dataList: List<StarPointDTO>
) : RecyclerView.Adapter<StarPointRVAdapter.ItemViewHolder>(){
    lateinit var binding: ReviewItemBinding
    inner class ItemViewHolder(val binding:ReviewItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(data: StarPointDTO){
            //binding.itemIvProfile
            binding.itemTvName.text = data.nickname!!.replace("\"", "")
            binding.itemTvContent.text = data.title!!.replace("\"", "")
            //binding.itemIvImage
            binding.itemTvLike.text = "좋아요 "+data.likes.toString()+"개"
            binding.itemTvLocation.text = data.location!!.replace("\"", "")
            binding.itemTvTime.text = data.point_date!!.replace("\"", "").subSequence(0, 10)

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
            intent.putExtra("pointId", dataList[position].point_id)
            ContextCompat.startActivity(holder.itemView.context, intent, null)
        }
    }

    override fun getItemCount(): Int = dataList.size


}
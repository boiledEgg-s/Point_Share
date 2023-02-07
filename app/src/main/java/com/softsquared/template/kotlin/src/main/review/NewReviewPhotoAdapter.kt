package com.softsquared.template.kotlin.src.main.review

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.softsquared.template.kotlin.databinding.CreatePhotoItemBinding


class NewReviewPhotoAdapter(
    private val dataList: List<Drawable?>,
    val onClickDelete: (draw: Drawable?) -> Unit
) : RecyclerView.Adapter<NewReviewPhotoAdapter.ItemViewHolder>(){

    inner class ItemViewHolder(val binding:CreatePhotoItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(data: Drawable?){
            //binding.itemIvProfile
            binding.photoImg.setImageDrawable(data)
            binding.photoImg.clipToOutline = true
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ItemViewHolder {
        val binding = CreatePhotoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(dataList[position])

        holder.itemView.setOnClickListener{
            onClickDelete.invoke(dataList[position])
        }

    }

    override fun getItemCount(): Int = dataList.size
}
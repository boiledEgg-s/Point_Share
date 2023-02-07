package com.softsquared.template.kotlin.src.main.review

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.softsquared.template.kotlin.databinding.ReviewVpItemBinding

class VPRecyclerAdapter(
    private val bgColors: ArrayList<Int>) : RecyclerView.Adapter<VPRecyclerAdapter.PagerViewHolder>() {

    inner class PagerViewHolder(val binding: ReviewVpItemBinding):RecyclerView.ViewHolder(binding.root) {

        fun bind(@ColorRes bgColor: Int, position: Int) {
            binding.reviewVpImg.setBackgroundColor(ContextCompat.getColor(binding.reviewVpImg.context, bgColor))
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): PagerViewHolder {
        val binding = ReviewVpItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PagerViewHolder(binding)
    }
    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.bind(bgColors[position], position)
    }

    override fun getItemCount(): Int = bgColors.size
}

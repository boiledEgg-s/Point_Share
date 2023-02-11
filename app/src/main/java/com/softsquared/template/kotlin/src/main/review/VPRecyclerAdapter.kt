package com.softsquared.template.kotlin.src.main.review

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.softsquared.template.kotlin.databinding.ReviewVpItemBinding
import okhttp3.internal.wait
import java.net.URL

class VPRecyclerAdapter(
    private val images: ArrayList<String>) : RecyclerView.Adapter<VPRecyclerAdapter.PagerViewHolder>() {

    inner class PagerViewHolder(val binding: ReviewVpItemBinding):RecyclerView.ViewHolder(binding.root) {


        fun bind(photo: String, position: Int) {
            var bmp: Bitmap?
            Thread() {
                try{
                    val url = URL(photo)
                    val stream = url.openStream()

                    bmp = BitmapFactory.decodeStream(stream)

                    Log.d("INSIDE THREAD", "GETTING PHOTO $photo")
                    Log.d("INSIDE THREAD", "GETTING URL $url")
                    Log.d("INSIDE THREAD", "GETTING BMP $bmp")
                    Log.d("INSIDE THREAD", "GETTING STREAM $stream")

                    Handler(Looper.getMainLooper()).post{
                        binding.reviewVpImg.setImageBitmap(bmp)
                        binding.reviewVpImg.clipToOutline = true
                    }

                } catch (e: java.lang.Exception){
                    e.printStackTrace()
                }
                return@Thread
            }.start()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): PagerViewHolder {
        val binding = ReviewVpItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PagerViewHolder(binding)
    }
    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.bind(images[position], position)
    }

    override fun getItemCount(): Int = images.size
}

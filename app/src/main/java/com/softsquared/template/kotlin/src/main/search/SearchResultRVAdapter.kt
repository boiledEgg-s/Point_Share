package com.softsquared.template.kotlin.src.main.search

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.softsquared.template.kotlin.databinding.ReviewItemBinding
import com.softsquared.template.kotlin.src.main.review.ReviewActivity
import com.softsquared.template.kotlin.src.retrofit.model.GetPointDTO
import java.net.URL

class SearchResultRVAdapter(
    private val dataList: List<GetPointDTO>
) : RecyclerView.Adapter<SearchResultRVAdapter.ItemViewHolder>() {
    lateinit var binding: ReviewItemBinding

    inner class ItemViewHolder(val binding: ReviewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: GetPointDTO) {
            Log.w("~~~~~~~~~~~~~~~~~~~~~~~~~~~~StarPoint INSIDE THREAD", "${data.point_image_list}")
            Thread() {
                if(data.point_image_list != null) {
                    val img_url = URL(data.point_image_list!!.split(",")[0])
                    val img_stream = img_url.openStream()
                    val img_bmp = BitmapFactory.decodeStream(img_stream)

                    Log.w("~~~~~~~~~~~~~~~~~~~~~~~~~~~~StarPoint INSIDE THREAD", "GETTING img ${img_stream}")
                    Handler(Looper.getMainLooper()).post {
                        binding.itemIvImage.setImageBitmap(img_bmp)
                    }
                }
                if(data.user_img_url != null) {
                    val profile_url = URL(data.user_img_url)
                    val profile_stream = profile_url.openStream()
                    val profile_bmp = BitmapFactory.decodeStream(profile_stream)
                    Log.w("StarPoint INSIDE THREAD", "GETTING profile $profile_stream")

                    Handler(Looper.getMainLooper()).post {
                        binding.itemIvProfile.setImageBitmap(profile_bmp)
                    }
                }
                return@Thread
            }.start()

            //binding.itemIvProfile
            binding.itemTvName.text = data.nickname!!.replace("\"", "")
            binding.itemTvContent.text = data.title!!.replace("\"", "")
            //binding.itemIvImage
            binding.itemTvLike.text = "좋아요 " + data.likes.toString() + "개"
            binding.itemTvLocation.text = data.location!!.replace("\"", "")
            binding.itemTvTime.text = data.point_date!!.replace("\"", "")

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
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, ReviewActivity::class.java)
            intent.putExtra("pointId", dataList[position].point_id)
            intent.putExtra("userId", dataList[position].user_id)
            ContextCompat.startActivity(holder.itemView.context, intent, null)
        }
    }

    override fun getItemCount(): Int = dataList.size

    override fun getItemViewType(position: Int): Int {
        return position
    }
}
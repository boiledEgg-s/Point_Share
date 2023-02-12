package com.softsquared.template.kotlin.src.main.myPage

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.databinding.MyPageReviewItemBinding
import com.softsquared.template.kotlin.src.main.myPage.model.ReviewItem
import com.softsquared.template.kotlin.src.main.review.ReviewActivity
import java.net.URL
import kotlin.coroutines.CoroutineContext

class MyPageRVAdapter(
    private val dataList: List<ReviewItem>,
    private val context: Context
) : RecyclerView.Adapter<MyPageRVAdapter.ItemViewHolder>(){

    inner class ItemViewHolder(val binding:MyPageReviewItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(data: ReviewItem){
            if(data.point_img != null) {
                Log.d("MY_PAGE_RECYCLER", data.point_img!!.split(",")[0])
                Thread() {
                    try {
                        val url = URL(data.point_img!!.split(",")[0])
                        val stream = url.openStream()
                        val bmp = BitmapFactory.decodeStream(stream)

                        Handler(Looper.getMainLooper()).post {
                            binding.reviewImg.setImageBitmap(bmp)
                        }
                    } catch (e: java.lang.Exception) {
                        e.printStackTrace()
                    }
                    return@Thread
                }.start()
            } else{
                binding.background.setBackgroundColor(ActivityCompat.getColor(context, R.color.lightgrey))
            }
            binding.myPageReviewTv1.text = data.title
            binding.myPageReviewTv2.text = data.point_date
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
            intent.putExtra("pointId", dataList[position].point_id)
            intent.putExtra("userId", ApplicationClass.user_id)
            ContextCompat.startActivity(holder.itemView.context, intent, null)
        }
    }

    override fun getItemCount(): Int = dataList.size
    override fun getItemViewType(position: Int): Int {
        return position
    }
}
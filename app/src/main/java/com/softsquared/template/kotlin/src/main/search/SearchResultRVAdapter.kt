package com.softsquared.template.kotlin.src.main.search

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.softsquared.template.kotlin.databinding.ReviewItemBinding
import com.softsquared.template.kotlin.src.main.review.ReviewActivity
import com.softsquared.template.kotlin.src.retrofit.model.GetPointDTO

class SearchResultRVAdapter(
    private val dataList: List<GetPointDTO>
) : RecyclerView.Adapter<SearchResultRVAdapter.ItemViewHolder>(){
    lateinit var binding: ReviewItemBinding
    inner class ItemViewHolder(val binding:ReviewItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(data: GetPointDTO){
            //binding.itemIvProfile
            binding.itemTvName.text = data.nickname!!.replace("\"", "")
            binding.itemTvContent.text = data.title!!.replace("\"", "")
            //binding.itemIvImage
            binding.itemTvLike.text = "좋아요 "+data.likes.toString()+"개"
            binding.itemTvLocation.text = data.location!!.replace("\"", "")
            binding.itemTvTime.text = data.point_date!!.replace("\"", "").subSequence(0, 10)


//            var bmp: Bitmap?
//            Thread() {
//                try{
//                    val url = URL(photo)
//                    val stream = url.openStream()
//
//                    bmp = BitmapFactory.decodeStream(stream)
//
//                    Log.d("INSIDE THREAD", "GETTING PHOTO $photo")
//                    Log.d("INSIDE THREAD", "GETTING URL $url")
//                    Log.d("INSIDE THREAD", "GETTING BMP $bmp")
//                    Log.d("INSIDE THREAD", "GETTING STREAM $stream")
//
//                    Handler(Looper.getMainLooper()).post{
//                        binding.reviewVpImg.setImageBitmap(bmp)
//                        binding.reviewVpImg.clipToOutline = true
//                    }
//
//                } catch (e: java.lang.Exception){
//                    e.printStackTrace()
//                }
//                return@Thread
//            }.start()
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
            intent.putExtra("userId", dataList[position].user_id)
            ContextCompat.startActivity(holder.itemView.context, intent, null)
        }
    }

    override fun getItemCount(): Int = dataList.size


}
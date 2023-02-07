package com.softsquared.template.kotlin.src.main.myPage

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.config.BaseFragment
import com.softsquared.template.kotlin.databinding.FragmentMyPageBinding
import com.softsquared.template.kotlin.src.main.review.ReviewItem
import java.io.File

class MyPageFragment :
    BaseFragment<FragmentMyPageBinding>(FragmentMyPageBinding::bind, R.layout.fragment_my_page) {
    //ApplicationClass SharedPreferences
    private val sharedPref = ApplicationClass.sSharedPreferences
    private val editPref: SharedPreferences.Editor = sharedPref.edit()

    //리뷰 관련 전역변수
    private var myReviewItems: ArrayList<ReviewItem> = arrayListOf()
    private var likeReviewItems: ArrayList<ReviewItem> = arrayListOf()
    private var reviewItems: ArrayList<ReviewItem> = arrayListOf()
    private lateinit var reviewRVAdapter: MyPageRVAdapter

    //갤러리 연동 관련
    lateinit var imageFile:File
    private val imageResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()
    ){
            result ->
        if(result.resultCode == RESULT_OK){
            val imageUri = result.data?.data
            imageUri?.let{
                imageFile = File(getRealPathFromURI(it))
                editPref.putString("profileImg_String", it.toString())
                editPref.apply() // SharedPreferences 적용

                Glide.with(this)
                    .load(imageUri)
                    .centerCrop()
                    .apply(RequestOptions().override(500, 500))
                    .into(binding.myPageIvProfile)

                binding.myPageIvProfile.clipToOutline = true
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.P)
    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val str = sharedPref.getString("profileImg_String", null)
        if(str != null){
            Glide.with(this)
                .load(Uri.parse(str))
                .centerCrop()
                .apply(RequestOptions().override(500, 500))
                .into(binding.myPageIvProfile)
        } else{
            binding.myPageIvProfile.setImageDrawable(ActivityCompat.getDrawable(requireContext(),R.drawable.user_icon_default))
        }
        binding.myPageIvProfile.clipToOutline = true

        binding.myPageTvReviewNum.setOnClickListener {
            binding.radioBtn1.isChecked = true
        }
        binding.myPageTvLikeNum.setOnClickListener {
            binding.radioBtn2.isChecked = true
        }

        binding.radioBtn1.setOnCheckedChangeListener { buttonView, isChecked ->
            when (isChecked) {
                true -> {
                    resetReviewItems(myReviewItems)
                }
                false -> {}
            }
        }

        binding.radioBtn2.setOnCheckedChangeListener { buttonView, isChecked ->
            when (isChecked) {
                true -> {
                    resetReviewItems(likeReviewItems)
                }
                false -> {
                }
            }
        }


        // 갤러리 연동 방법
        binding.myPageCaremaIcon.setOnClickListener{
            val permissionCheck = ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                navigateGallery()
            }
            else if(shouldShowRequestPermissionRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
                showPermissionContextPopup()
            }

            // 권한 요청 하기(requestPermissions) -> 갤러리 접근(onRequestPermissionResult)
            else {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    ApplicationClass.REQUIRED_PERMISSIONS,
                    ApplicationClass.REQ_GALLERY
                )
            }
        }

        //reviewItems 세팅
        tempReviewsSetting()
        resetReviewItems(myReviewItems)
        binding.myPageTvLikeNum.text = likeReviewItems.size.toString()
        binding.myPageTvReviewNum.text = myReviewItems.size.toString()

        //어뎁터 생성 및 배열과 연결
        reviewRVAdapter = MyPageRVAdapter(reviewItems)
        binding.myPageRv.adapter = reviewRVAdapter
        binding.myPageRv.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
    }

    fun tempReviewsSetting() {
        var i = 0;
        while (i < 20) {
            myReviewItems.apply {
                this.add(
                    ReviewItem(
                        "profileImg",
                        "MyReviewPage->" + ('a' + i).toString() + i.toString(),
                        ('a' + i).toString() + ('a' + i).toString() + ('a' + i).toString(),
                        "image",
                        i,
                        ('a' + i).toString() + ('a' + i).toString() + "_loc",
                        i.toString() + i.toString() + i.toString() + i.toString() + "/" + i.toString() + i.toString() + "/" + i.toString() + i.toString()
                    )
                )
            }
            i++
        }
        i = 0
        while (i < 11) {
            likeReviewItems.apply {
                this.add(
                    ReviewItem(
                        "profileImg",
                        "MyLikePage->" + ('a' + i).toString() + i.toString(),
                        ('a' + i).toString() + ('a' + i).toString() + ('a' + i).toString(),
                        "image",
                        i,
                        ('a' + i).toString() + ('a' + i).toString() + "_loc",
                        i.toString() + i.toString() + i.toString() + i.toString() + "/" + i.toString() + i.toString() + "/" + i.toString() + i.toString()
                    )
                )
            }
            i++
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun resetReviewItems(arr:ArrayList<ReviewItem>){
        reviewItems.clear()
        reviewItems.addAll(arr)
        binding.myPageRv.adapter?.notifyDataSetChanged()
    }



    ///////////////////

    private fun getRealPathFromURI(uri: Uri):String {
        val buildName = Build.MANUFACTURER
        if(buildName.equals("Xiaomi")){
            return uri.path!!
        }
        var columnIndex = 0
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = requireContext().contentResolver.query(uri, proj, null, null, null)
        if(cursor!!.moveToFirst()){
            columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        }
        val result = cursor.getString(columnIndex)
        cursor.close()
        return result
    }
    private fun navigateGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        // 가져올 컨텐츠들 중에서 Image 만을 가져온다.
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
        imageResult.launch(intent)
    }
    private fun showPermissionContextPopup() {
        AlertDialog.Builder(requireContext())
            .setTitle("권한이 필요합니다.")
            .setMessage("프로필 이미지를 바꾸기 위해서는 갤러리 접근 권한이 필요합니다.")
            .setPositiveButton("동의하기") { _, _ ->
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    ApplicationClass.REQUIRED_PERMISSIONS,
                    1000
                )
            }
            .setNegativeButton("취소하기") { _, _ -> }
            .create()
            .show()
    }


}


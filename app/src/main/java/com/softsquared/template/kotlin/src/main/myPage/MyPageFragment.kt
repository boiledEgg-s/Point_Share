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
import android.util.Log
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
import com.softsquared.template.kotlin.src.main.myPage.model.ReviewItem
import com.softsquared.template.kotlin.src.retrofit.RetrofitClassInterface
import com.softsquared.template.kotlin.src.retrofit.RetrofitService
import com.softsquared.template.kotlin.src.retrofit.model.GetUserLikeDTO
import com.softsquared.template.kotlin.src.retrofit.model.GetUserPointDTO
import com.softsquared.template.kotlin.src.retrofit.response.PutUserResponse
import com.softsquared.template.kotlin.src.retrofit.response.UserLikeResponse
import com.softsquared.template.kotlin.src.retrofit.response.UserPointResponse
import com.softsquared.template.kotlin.src.retrofit.response.UserResponse
import java.io.File

class MyPageFragment :
    BaseFragment<FragmentMyPageBinding>(FragmentMyPageBinding::bind, R.layout.fragment_my_page),
    RetrofitClassInterface {
    //ApplicationClass SharedPreferences
    private val sharedPref = ApplicationClass.sSharedPreferences
    private val editPref: SharedPreferences.Editor = sharedPref.edit()

    //API 관련
    private val service = RetrofitService(this)

    //리뷰 관련 전역변수
    private var isLikePoint = false
    private var pageId = 1
    private var myPoints: ArrayList<ReviewItem> = arrayListOf()
    private var likePoints: ArrayList<ReviewItem> = arrayListOf()
    private var reviewItems: ArrayList<ReviewItem> = arrayListOf()
    private lateinit var pointRVAdapter: MyPageRVAdapter
    private lateinit var likeRVAdapter: MyPageRVAdapter


    //갤러리 연동 관련
    lateinit var imageFile: File
    private val imageResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val imageUri = result.data?.data
            imageUri?.let {
                imageFile = File(ApplicationClass().getRealPathFromURI(it, requireContext()))

                service.tryPutUser(ApplicationClass.user_id, imageFile)
                Log.d("MyPageFragment", "${ApplicationClass.user_id}, $imageFile, ")
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


        //어뎁터 생성 및 배열과 연결
        pointRVAdapter = MyPageRVAdapter(myPoints, requireContext())
        likeRVAdapter = MyPageRVAdapter(likePoints, requireContext())
        setAdapter(pointRVAdapter)


        //reviewItems 세팅
        service.tryGetUser(ApplicationClass.user_id)
        service.tryGetUserPoints(ApplicationClass.user_id)
        service.tryGetUserLikes(ApplicationClass.user_id, pageId.toString())

        val str = sharedPref.getString("profileImg_String", null)
        if (str != null) {
            Glide.with(this)
                .load(Uri.parse(str))
                .centerCrop()
                .apply(RequestOptions().override(500, 500))
                .into(binding.myPageIvProfile)
        } else {
            binding.myPageIvProfile.setImageDrawable(
                ActivityCompat.getDrawable(
                    requireContext(),
                    R.drawable.user_icon_default
                )
            )
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
                    setAdapter(pointRVAdapter)
                    isLikePoint = false
                }
                false -> {

                }
            }
        }

        binding.radioBtn2.setOnCheckedChangeListener { buttonView, isChecked ->
            when (isChecked) {
                true -> {
                    setAdapter(likeRVAdapter)
                    isLikePoint = true
                }
                false -> {
                }
            }
        }


        // 갤러리 연동 방법
        binding.myPageCaremaIcon.setOnClickListener {
            val permissionCheck = ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                navigateGallery()
            } else if (shouldShowRequestPermissionRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
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

        binding.myPageRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastVisibleItemPosition =
                    (binding.myPageRv.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
                val itemTotalCount = binding.myPageRv.adapter?.itemCount
                if (reviewItems.size >= 10) {
                    if (lastVisibleItemPosition + 1 >= reviewItems.size) {
                        //리스트 마지막(바닥) 도착!!!!! 다음 페이지 데이터 로드!!
                        Log.d("from recycler view", "reached end")
                        if (isLikePoint) {
                            service.tryGetUserLikes(ApplicationClass.user_id, (++pageId).toString())
                        }
                    }
                }
            }
        })


    }

    @SuppressLint("NotifyDataSetChanged")
    fun resetReviewItems(arr: ArrayList<ReviewItem>) {
        reviewItems.clear()
        reviewItems.addAll(arr)
        binding.myPageRv.adapter?.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setAdapter(reviewRVAdapter: MyPageRVAdapter) {
        binding.myPageRv.adapter = reviewRVAdapter
        binding.myPageRv.adapter?.notifyDataSetChanged()
        binding.myPageRv.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
    }


    ///////////////////
    fun navigateGallery() {
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


    /********************API RELATED CODES**********************/
    override fun onGetUserSuccess(response: UserResponse) {
        super.onGetUserSuccess(response)
        binding.myPageTvProfile.text = response.result.nickname
    }

    override fun onPutUserSuccess(response: PutUserResponse) {
        val data = response.result
        super.onPutUserSuccess(response)
        Log.d("MyPageFragment onPutUser", "Sucesss, ${data.userId}")
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onGetUserPointSuccess(response: UserPointResponse) {
        super.onGetUserPointSuccess(response)
        if (response.result != null) {
            for (i in response.result) {
                val temp = ReviewItem(i.point_id, i.title, i.point_date, i.point_img_list)
                myPoints.apply {
                    this.add(temp)
                }
            }
        }
        binding.myPageRv.adapter!!.notifyDataSetChanged()
        binding.myPageTvReviewNum.text = myPoints.size.toString()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onGetUserLikeSuccess(response: UserLikeResponse) {
        super.onGetUserLikeSuccess(response)
        if (response.result != null) {
            for (i in response.result) {
                val temp = ReviewItem(i.point_id, i.title, i.point_date, i.point_img_list)
                likePoints.apply {
                    this.add(temp)
                }
            }
        }
        binding.myPageRv.adapter!!.notifyDataSetChanged()
        binding.myPageTvLikeNum.text = likePoints.size.toString()
    }

}


package com.softsquared.template.kotlin.src.main.review
/*
리뷰 작성 페이지
1. 서버에 다음 도로명 주소 페이지.php 파일 보내기
2. 사진 및 데이터 POST
 */


import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.icu.util.Calendar
import android.net.Uri
import android.net.http.SslError
import android.os.*
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.config.BaseActivity
import com.softsquared.template.kotlin.databinding.ActivityNewReviewBinding
import com.softsquared.template.kotlin.src.main.review.model.PutReviewItem
import com.softsquared.template.kotlin.src.retrofit.RetrofitClassInterface
import com.softsquared.template.kotlin.src.retrofit.RetrofitService
import com.softsquared.template.kotlin.src.retrofit.model.PostPointDTO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.net.URI
import java.net.URL

class UpdateReviewActivity : BaseActivity<ActivityNewReviewBinding>(ActivityNewReviewBinding::inflate), RetrofitClassInterface{

    private lateinit var item:PutReviewItem
    private var check = false
    private var photoArr:ArrayList<Drawable?> = arrayListOf()
    lateinit var photoRVAdapter:NewReviewPhotoAdapter

    private var imageFiles = arrayListOf<File>()
    private lateinit var postReviewRequest:PostPointDTO

    private val service = RetrofitService(this)

    private val imageResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
            result ->
        if(result.resultCode == RESULT_OK){
            val imageUri = result.data?.data
            imageUri?.let{
                imageFiles.add(File(getRealPathFromURI(it, this)))
                Log.d("CHECK PHOTO TYPE", "URI: ${imageUri.toString()}")
                Log.d("CHECK PHOTO TYPE", "String: ${ApplicationClass().getRealPathFromURI(it, this).toString()}")
                Log.d("CHECK PHOTO TYPE", "FILE: ${File(ApplicationClass().getRealPathFromURI(it, this)).toString()}")

                val inputStream =  this.contentResolver.openInputStream(imageUri)
                val drawable = Drawable.createFromStream(inputStream, imageUri.toString())
                photoArr.apply {this.add(0, drawable)}
                photoRVAdapter.notifyItemInserted(0)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //날짜
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        photoRVAdapter = NewReviewPhotoAdapter(photoArr,
            onClickDelete = { deleteSearchString(it)})
        photoRVAdapter.notifyDataSetChanged()
        binding.newReviewRvPhoto.adapter = photoRVAdapter
        binding.newReviewRvPhoto.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)

        item = intent.getSerializableExtra("data", PutReviewItem::class.java) as PutReviewItem
        initViews(item, this)


        //뒤로 가기 버튼
        binding.newReviewIvBack.setOnClickListener{
            finish()
        }

        binding.reviewDeleteBtn.setOnClickListener{
            service.tryDeletePoints(item.pointId!!)
            setResult(9000)
            finish()
        }


        binding.newReviewIvCamera.setOnClickListener{
            val permissionCheck = ContextCompat.checkSelfPermission(this,
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
                    this,
                    ApplicationClass.REQUIRED_PERMISSIONS,
                    ApplicationClass.REQ_GALLERY
                )
            }
        }


        //포인트 등록 버튼
            binding.newReviewBtnFinish.setOnClickListener {
                if(check) {
                    postReviewRequest = PostPointDTO(null,
                        binding.newReviewTvTitle.text.toString(),
                        binding.newReviewTvContent.text.toString(),
                        binding.newReviewTvType.text.toString(),
                        binding.newReviewTvLocation.text.toString(),
                        binding.newReviewTvProduct.text.toString(),
                        binding.newReviewTvDate.text.toString(),
                        imageFiles)
                    var intent = Intent(this, ReviewUpdatedActivity::class.java)
                    intent.putExtra("data", postReviewRequest)
                    intent.putExtra("pointId", item.pointId)
                    startActivity(intent)
                    finish()
                } else {
                    Log.d("checking", "check is $check")
                }
            }

        //날짜 버튼
        binding.newReviewBtnDate.setOnClickListener{
            val dateDialog = DatePickerDialog(this, {_, year, month, day -> run {
                var str = year.toString()+"-"
                if((month+1) < 10) str += "0"
                str+= (month+1).toString()+"-"
                if(day < 10) str += "0"
                str += day.toString()
                binding.newReviewTvDate.setText(str)
            }
            }, year, month, day)
            dateDialog.show()
        }


        binding.newReviewTvLocation.setOnClickListener{
            showKakaoAddressWebView()
        }

        //텍스트 수정 시
        binding.newReviewTvTitle.addTextChangedListener(object:TW(){
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkEditTextFilled()
            }
        })
        binding.newReviewTvDate.addTextChangedListener(object:TW(){
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkEditTextFilled()
            }
        })
        binding.newReviewTvType.addTextChangedListener(object:TW(){
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkEditTextFilled()
            }
        })
        binding.newReviewTvLocation.addTextChangedListener(object:TW(){
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkEditTextFilled()
            }
        })
        binding.newReviewTvProduct.addTextChangedListener(object:TW(){
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkEditTextFilled()
            }
        })
        binding.newReviewTvContent.addTextChangedListener(object:TW(){
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkEditTextFilled()
            }
        })

    }

    private fun initViews(i:PutReviewItem, context:Context){
        binding.newReviewBtnFinish.setText("포인트 수정하기")
        binding.reviewDeleteBtn.visibility = View.VISIBLE
        //Log.w("====Review update======", i.images!![0])
        imageFiles.clear()
        photoArr.clear()
        if(i.images != null){
            for(img in i.images!!) {
                Thread() {
                    try {
                        val file = File(img)
                        imageFiles.add(0, file)
                        val img_url = URL(img)
                        val img_stream = img_url.openStream()
                        val img_bmp = BitmapFactory.decodeStream(img_stream)

                        Log.d("StarPoint INSIDE THREAD", "GETTING img ${img_stream}")

                        Handler(Looper.getMainLooper()).post {
                            val drawable = BitmapDrawable(resources, img_bmp)
                            photoArr.apply { this.add(0, drawable) }
                            photoRVAdapter.notifyItemInserted(0)
                        }
                    } catch (e: java.lang.Exception) {
                        e.printStackTrace()
                    }
                    return@Thread
                }.start()
            }
        }
        binding.newReviewTvTitle.setText(i.title)
        binding.newReviewTvDate.setText(i.point_date)
        binding.newReviewTvType.setText(i.point_Type)
        binding.newReviewTvProduct.setText(i.creature)
        binding.newReviewTvLocation.setText(i.location)
        binding.newReviewTvContent.setText(i.content)
        checkEditTextFilled()
    }


    fun checkEditTextFilled(){
        if(binding.newReviewTvTitle.text.isNotEmpty() &&
            binding.newReviewTvDate.text.isNotEmpty()  &&
            binding.newReviewTvType.text.isNotEmpty()  &&
            binding.newReviewTvLocation.text.isNotEmpty()  &&
            binding.newReviewTvProduct.text.isNotEmpty()  &&
            binding.newReviewTvContent.text.isNotEmpty()
        ) {
            Log.d("checking","Background must be changed")
            //binding.newReviewBtnFinish.setBackgroundColor(getColor(R.color.main_skyblue))
            check = true
        } else{
            check = false
        }

        binding.newReviewBtnFinish.backgroundTintList = when(check){
            true -> ColorStateList.valueOf(ContextCompat.getColor(this, R.color.main_skyblue))
            else -> ColorStateList.valueOf(ContextCompat.getColor(this, R.color.lightgrey))
        }
    }

    abstract class TW:TextWatcher{
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int){}

        override fun afterTextChanged(s: Editable?) {}

    }

    @SuppressLint("NotifyDataSetChanged")
    fun deleteSearchString(draw: Drawable?) {
        val i = photoArr.indexOf(draw)
        photoArr.remove(draw)
        imageFiles.removeAt(i)
        binding.newReviewRvPhoto.adapter?.notifyDataSetChanged()
    }

    private fun navigateGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        // 가져올 컨텐츠들 중에서 Image 만을 가져온다.
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
        imageResult.launch(intent)
    }

    private fun getRealPathFromURI(uri: Uri, context: Context):String {
        val buildName = Build.MANUFACTURER
        if(buildName.equals("Xiaomi")){
            return uri.path!!
        }
        var columnIndex = 0
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = context.contentResolver.query(uri, proj, null, null, null)
        if(cursor!!.moveToFirst()){
            columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        }
        val result = cursor.getString(columnIndex)
        cursor.close()
        return result
    }

    private fun showPermissionContextPopup() {
        AlertDialog.Builder(this)
            .setTitle("권한이 필요합니다.")
            .setMessage("프로필 이미지를 바꾸기 위해서는 갤러리 접근 권한이 필요합니다.")
            .setPositiveButton("동의하기") { _, _ ->
                ActivityCompat.requestPermissions(
                    this,
                    ApplicationClass.REQUIRED_PERMISSIONS,
                    1000
                )
            }
            .setNegativeButton("취소하기") { _, _ -> }
            .create()
            .show()
    }


    //카카오 도로명주소 달기
    private fun showKakaoAddressWebView() {

        binding.webView.settings.apply {
            javaScriptEnabled = true
            javaScriptCanOpenWindowsAutomatically = true
            setSupportMultipleWindows(true)
        }

        binding.webView.apply {
            addJavascriptInterface(WebViewData(), "Leaf")
            webViewClient = client
            webChromeClient = chromeClient
            loadUrl("13.125.187.165/loc.php")
        }
    }

    private val client: WebViewClient = object : WebViewClient() {

        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            return false
        }

        override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
            handler?.proceed()
        }
    }

    private inner class WebViewData {
        @JavascriptInterface
        fun getAddress(zoneCode: String, roadAddress: String, buildingName: String) {

            CoroutineScope(Dispatchers.Default).launch {

                withContext(CoroutineScope(Dispatchers.Main).coroutineContext) {

                    binding.newReviewTvLocation.setText("$roadAddress $buildingName")

                }
            }
        }
    }

    private val chromeClient = object : WebChromeClient() {

        override fun onCreateWindow(view: WebView?, isDialog: Boolean, isUserGesture: Boolean, resultMsg: Message?): Boolean {

            val newWebView = WebView(this@UpdateReviewActivity).apply{
                settings.javaScriptEnabled = true
            }


            val dialog = Dialog(this@UpdateReviewActivity)

            dialog.setContentView(newWebView)

            val params = dialog.window!!.attributes

            params.width = ViewGroup.LayoutParams.MATCH_PARENT
            params.height = ViewGroup.LayoutParams.MATCH_PARENT

            dialog.window!!.attributes = params
            dialog.show()

            newWebView.webChromeClient = object : WebChromeClient() {
                override fun onJsAlert(view: WebView, url: String, message: String, result: JsResult): Boolean {
                    super.onJsAlert(view, url, message, result)
                    return true
                }

                override fun onCloseWindow(window: WebView?) {
                    dialog.dismiss()
                }
            }

            (resultMsg!!.obj as WebView.WebViewTransport).webView = newWebView
            resultMsg.sendToTarget()

            return true
        }
    }
}
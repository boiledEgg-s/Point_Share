package com.softsquared.template.kotlin.src.main.map

/*
앱의 메인화면, 지도 화면

1. 마커 여러 개 등록
    - 마커용 GET API
    - 마커의 DIALOG GET API
2. 위치 설정 - 개인 위치 혹은 사용자 설정
 */

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.app.ActivityCompat.startActivity
import androidx.core.content.ContextCompat
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.BaseFragment
import com.softsquared.template.kotlin.databinding.FragmentMapBinding
import com.softsquared.template.kotlin.src.main.review.NewReviewActivity
import com.softsquared.template.kotlin.src.main.review.ReviewActivity
import com.softsquared.template.kotlin.src.main.search.SearchActivity
import com.softsquared.template.kotlin.src.retrofit.RetrofitClassInterface
import com.softsquared.template.kotlin.src.retrofit.RetrofitService
import com.softsquared.template.kotlin.src.retrofit.model.GetMarkDTO
import com.softsquared.template.kotlin.src.retrofit.response.GetMarkResponse
import com.softsquared.template.kotlin.src.retrofit.response.PointResponse
import net.daum.mf.map.api.CalloutBalloonAdapter
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView

class MapFragment :
    BaseFragment<FragmentMapBinding>(FragmentMapBinding::bind, R.layout.fragment_map),
    RetrofitClassInterface {
    private val PERMISSIONS_REQUEST_CODE = 100
    private var REQUIRED_PERMISSIONS = arrayOf(
        ACCESS_FINE_LOCATION
    )

    private val service = RetrofitService(this)
    private lateinit var markerEventListener: MarkerEventListener

    private lateinit var mapView: MapView
    var mapViewContainer:RelativeLayout?= null

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.w("======================MapFragment========================","onCreate() called")
        super.onViewCreated(view, savedInstanceState)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onResume() {
        Log.w("======================MapFragment========================","onResume() called")

        mapView = MapView(requireActivity()).also {
            mapViewContainer = RelativeLayout(activity)
            mapViewContainer?.layoutParams = RelativeLayout
                .LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            binding.mapLayout.addView(mapViewContainer)
            mapViewContainer?.addView(it)
        }
        var zoom = 11
        service.tryGetPoints(null, "최신순", "2")


        getCurrentLocation()
        mapView.setZoomLevel(zoom, true)

        //지도 위치 이동
        binding.mapBtnLoc.setOnClickListener {
            getCurrentLocation()
        }

        //지도 크기 조정
        binding.mapBtnInc.setOnClickListener {
            mapView.setZoomLevel(--zoom, true)
        }
        binding.mapBtnDec.setOnClickListener {
            mapView.setZoomLevel(++zoom, true)
        }

        //리뷰 작성
        binding.mapIvIcon.setOnClickListener {
            val intent = Intent(context, NewReviewActivity::class.java)
            startActivity(intent)
        }

        //검색창 터치
        binding.mapEtSearch.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> {
                        val intent = Intent(context, SearchActivity::class.java)
                        startActivity(intent)
                    }
                }
                return true //or false
            }
        })

        mapView.setCalloutBalloonAdapter(
            CustomBalloonAdapter(layoutInflater, requireContext())
        )
        markerEventListener = MarkerEventListener(requireContext())
        mapView.setPOIItemEventListener(markerEventListener)
        super.onResume()
    }




    fun getCurrentLocation() {
        val permissionCheck =
            ContextCompat.checkSelfPermission(requireContext(), ACCESS_FINE_LOCATION)
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            val lm = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
            try {
                val userCurLocation = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                val uLatitude = userCurLocation!!.latitude
                val uLogitude = userCurLocation.longitude
                val uCurPosition = MapPoint.mapPointWithGeoCoord(uLatitude, uLogitude)
                mapView.setMapCenterPoint(uCurPosition, true)
            } catch (e: java.lang.NullPointerException) {
                Log.e("LOCATION_ERROR", e.toString())

                ActivityCompat.finishAffinity(requireActivity())

//                val intent = Intent(context, MapFragment::class.java)
//                startActivity(intent)
                System.exit(0)
            }
        } else {
            Toast.makeText(requireContext(), "위치 권한이 없습니다.", Toast.LENGTH_SHORT).show()
//            binding.root.removeView(mapView)
            requestPermissions(
                requireActivity(),
                REQUIRED_PERMISSIONS,
                PERMISSIONS_REQUEST_CODE
            )
        }
    }

    fun addMarker(name: String, lat: Double, lon: Double) {
        val marker = MapPOIItem()

        marker.apply {
            itemName = name   // 마커 이름
            mapPoint = MapPoint.mapPointWithGeoCoord(lat, lon)   // 좌표
            markerType = MapPOIItem.MarkerType.CustomImage          // 마커 모양 (커스텀)
            customImageResourceId = R.drawable.map_marker_img_1
            selectedMarkerType = MapPOIItem.MarkerType.CustomImage  // 클릭 시 마커 모양 (커스텀)
            customSelectedImageResourceId = R.drawable.map_marker_img_1      // 클릭 시 커스텀 마커 이미지
            isCustomImageAutoscale = false      // 커스텀 마커 이미지 크기 자동 조정
            setCustomImageAnchor(0.5f, 1.0f)    // 마커 이미지 기준점
        }
        mapView.addPOIItem(marker)
    }

    class CustomBalloonAdapter(inflater: LayoutInflater, context:Context) :
        CalloutBalloonAdapter {
        val mCalloutBalloon: View = inflater.inflate(R.layout.map_dialog, null)
        val title: TextView = mCalloutBalloon.findViewById(R.id.dialog_tv_title)
        val tag: TextView = mCalloutBalloon.findViewById(R.id.dialog_tv_tags)
        val likes: TextView = mCalloutBalloon.findViewById(R.id.dialog_tv_likes)
        val context = context



        override fun getCalloutBalloon(poiItem: MapPOIItem?): View {
            // 마커 클릭 시 나오는 말풍선
            val str = poiItem!!.itemName.split(",")
            title.text = str[1]  // 해당 마커의 정보 이용 가능
            tag.text = str[2]
            likes.text = str[3]
            return mCalloutBalloon
        }

        override fun getPressedCalloutBalloon(poiItem: MapPOIItem?): View {
            // 말풍선 클릭 시
            Log.d("CLICKED ", "CLICKED" )
            return mCalloutBalloon
        }
    }

    class MarkerEventListener(val context: Context): MapView.POIItemEventListener {

        override fun onPOIItemSelected(mapView: MapView?, poiItem: MapPOIItem?) {
            // 마커 클릭 시
        }

        @Deprecated("Deprecated in Java")
        override fun onCalloutBalloonOfPOIItemTouched(mapView: MapView?, poiItem: MapPOIItem?) {
            // 말풍선 클릭 시 (Deprecated)
            // 이 함수도 작동하지만 그냥 아래 있는 함수에 작성하자
        }

        override fun onCalloutBalloonOfPOIItemTouched(mapView: MapView?, poiItem: MapPOIItem?, buttonType: MapPOIItem.CalloutBalloonButtonType?) {
            // 말풍선 클릭 시
            /*val builder = AlertDialog.Builder(context)
            val itemList = arrayOf("토스트", "마커 삭제", "취소")
            builder.setTitle("${poiItem?.itemName}")
            builder.setItems(itemList) { dialog, which ->
                when(which) {
                    0 -> Toast.makeText(context, "토스트", Toast.LENGTH_SHORT).show()  // 토스트
                    1 -> mapView?.removePOIItem(poiItem)    // 마커 삭제
                    2 -> dialog.dismiss()   // 대화상자 닫기
                }
            }
            builder.show()*/

            Log.d("***************clicked balloon*****************", "clickclickclc")

            val str = poiItem!!.itemName.split(",")
            Log.d("말풍선","이벤트로 이동 ${str[0]}")
            val intent = Intent(context, ReviewActivity::class.java)
            intent.putExtra("pointId", str[0])
            startActivity(context, intent,null)
        }

        override fun onDraggablePOIItemMoved(mapView: MapView?, poiItem: MapPOIItem?, mapPoint: MapPoint?) {
            // 마커의 속성 중 isDraggable = true 일 때 마커를 이동시켰을 경우
        }
    }



    override fun onGetPointsSuccess(response: PointResponse) {
        super.onGetPointsSuccess(response)


        for (point in response.result) {
            if (point.latitude == null || point.longitude == null) continue
            val tempLatitude = point.latitude!!
            val tempLongitude = point.longitude!!
            service.tryGetMapMark(tempLatitude, tempLongitude)
        }
    }

    override fun onGetMapMarkSuccess(response: GetMarkResponse) {
        super.onGetMapMarkSuccess(response)
        val point = response.result[0]
        addMarker(point.point_id.toString()+","+point.title.toString()+","+point.point_type.toString()+","+point.likes.toString(), point.latitude!!, point.longitude!!)
    }

    //****************************
    override fun onStart() {
        Log.w("======================MapFragment========================","onStart() called")
        super.onStart()
    }



    override fun onStop() {
        Log.w("======================MapFragment========================","onStop() called")
        super.onStop()
    }

    override fun onPause() {
        Log.w("======================MapFragment========================","onPause() called")
        binding.mapLayout.removeView(mapView)
        super.onPause()
    }

    override fun onDestroy() {
        Log.w("======================MapFragment========================","onDestroy() called")
        super.onDestroy()
    }

    override fun onDestroyView() {
        Log.w("======================MapFragment========================","onDestroyView() called")
        super.onDestroyView()
    }

}


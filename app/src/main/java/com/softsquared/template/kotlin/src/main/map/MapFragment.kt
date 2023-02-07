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
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.BaseFragment
import com.softsquared.template.kotlin.databinding.FragmentMapBinding
import com.softsquared.template.kotlin.src.main.review.NewReviewActivity
import com.softsquared.template.kotlin.src.main.search.SearchActivity
import net.daum.mf.map.api.CalloutBalloonAdapter
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint

class MapFragment :
    BaseFragment<FragmentMapBinding>(FragmentMapBinding::bind, R.layout.fragment_map) {
    private val PERMISSIONS_REQUEST_CODE = 100
    private var REQUIRED_PERMISSIONS = arrayOf(
        ACCESS_FINE_LOCATION
    )


    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var zoom = 4
        getCurrentLocation()
        binding.mapView.setZoomLevel(zoom, true)



        //지도 위치 이동
        binding.mapBtnLoc.setOnClickListener {
            getCurrentLocation()
        }

        //지도 크기 조정
        binding.mapBtnInc.setOnClickListener {
            binding.mapView.setZoomLevel(--zoom, true)
        }
        binding.mapBtnDec.setOnClickListener {
            binding.mapView.setZoomLevel(++zoom, true)
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
    }

    fun getCurrentLocation() {
        val permissionCheck = ContextCompat.checkSelfPermission(requireContext(), ACCESS_FINE_LOCATION)
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            val lm = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
            try {
                val userCurLocation = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                val uLatitude = userCurLocation!!.latitude
                val uLogitude = userCurLocation.longitude
                val uCurPosition = MapPoint.mapPointWithGeoCoord(uLatitude, uLogitude)
                binding.mapView.setCalloutBalloonAdapter(CustomBalloonAdapter(layoutInflater))
                addMarker("asdhdskljafdaslkjfhladsfhklasdfhlasdh", uLatitude, uLogitude)
                binding.mapView.setMapCenterPoint(uCurPosition, true)
            } catch (e: java.lang.NullPointerException) {
                Log.e("LOCATION_ERROR", e.toString())

                ActivityCompat.finishAffinity(requireActivity())

                val intent = Intent(context, MapFragment::class.java)
                startActivity(intent)
                System.exit(0)
            }
        } else {
            Toast.makeText(requireContext(), "위치 권한이 없습니다.", Toast.LENGTH_SHORT).show()
            requestPermissions(
                requireActivity(),
                REQUIRED_PERMISSIONS,
                PERMISSIONS_REQUEST_CODE
            )
        }
    }

    fun addMarker(name:String, lat:Double, lon:Double){
        val marker = MapPOIItem()
        marker.apply {
            itemName = name   // 마커 이름
            mapPoint = MapPoint.mapPointWithGeoCoord(lat, lon)   // 좌표
            markerType = MapPOIItem.MarkerType.CustomImage          // 마커 모양 (커스텀)
            customImageResourceId = R.drawable.map_marker_img_2
            selectedMarkerType = MapPOIItem.MarkerType.CustomImage  // 클릭 시 마커 모양 (커스텀)
            customSelectedImageResourceId = R.drawable.map_marker_img_1      // 클릭 시 커스텀 마커 이미지
            isCustomImageAutoscale = false      // 커스텀 마커 이미지 크기 자동 조정
            setCustomImageAnchor(0.5f, 1.0f)    // 마커 이미지 기준점
        }
        binding.mapView.addPOIItem(marker)
    }

    class CustomBalloonAdapter(inflater: LayoutInflater): CalloutBalloonAdapter {
        val mCalloutBalloon: View = inflater.inflate(R.layout.map_dialog, null)
        val title: TextView = mCalloutBalloon.findViewById(R.id.dialog_tv_title)
        val tag: TextView = mCalloutBalloon.findViewById(R.id.dialog_tv_tags)
        val likes: TextView = mCalloutBalloon.findViewById(R.id.dialog_tv_likes)

        override fun getCalloutBalloon(poiItem: MapPOIItem?): View {
            // 마커 클릭 시 나오는 말풍선
            title.text = poiItem?.itemName   // 해당 마커의 정보 이용 가능
            tag.text = "getCalloutBalloon"
            likes.text = "1"
            return mCalloutBalloon
        }

        override fun getPressedCalloutBalloon(poiItem: MapPOIItem?): View {
            // 말풍선 클릭 시
            title.text = poiItem?.itemName   // 해당 마커의 정보 이용 가능
            tag.text = "asdajsldkjaslkdjalsdjlaksjdajsdlkjasld"
            likes.text = "2"
            return mCalloutBalloon
        }
    }
}


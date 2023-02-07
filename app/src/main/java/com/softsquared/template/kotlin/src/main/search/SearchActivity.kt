package com.softsquared.template.kotlin.src.main.search

/*
(activity as Activity_Name) -> using activity in fragment
supportFragmentManager(findFragmentById(R.id.FRAGMENT_CONTAINER) as FRAGMENT_CLASS
 */

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.softsquared.template.kotlin.R
import com.softsquared.template.kotlin.config.ApplicationClass
import com.softsquared.template.kotlin.config.BaseActivity
import com.softsquared.template.kotlin.databinding.ActivitySearchBinding


class SearchActivity : BaseActivity<ActivitySearchBinding>(ActivitySearchBinding::inflate) {

    var searchArr: ArrayList<String> = arrayListOf()
    lateinit var sharedPref: SharedPreferences
    lateinit var editPref: SharedPreferences.Editor


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSearchArr()

        supportFragmentManager.beginTransaction()
            .replace(R.id.search_frame, SearchRequestFragment())
            .commitAllowingStateLoss()



        // EditText 키보드 이벤트 = 검색 아이콘 클릭 이벤트
        binding.mapEtSearch.setOnEditorActionListener(getEditorActionListener(binding.searchBtn))

        // 검색창 터치 이벤트
        binding.mapEtSearch.setOnClickListener{
            supportFragmentManager.beginTransaction()
                .replace(R.id.search_frame, SearchRequestFragment())
                .commitAllowingStateLoss()
            binding.mapEtSearch.isCursorVisible = true
        }

        // 검색 아이콘 클릭 이벤트
        binding.searchBtn.setOnClickListener {
            if(supportFragmentManager.findFragmentById(R.id.search_frame) is SearchRequestFragment){
                val str = binding.mapEtSearch.text.toString()
                if (str != "") {
                    addToSharedPreference(str)

                    supportFragmentManager.beginTransaction()
                        .replace(R.id.search_frame, SearchResultFragment())
                        .commitAllowingStateLoss()
                }
            }
        }
    }

    //sharedPreference에서 searchArr에 담기
    fun initSearchArr() {
        sharedPref = ApplicationClass.sSharedPreferences
        editPref = sharedPref.edit()
        val stringPref = sharedPref.getString("search_data", null)

        // SharedPreferences 데이터가 있으면 String을 ArrayList로 변환
        // fromJson → json 형태의 문자열을 명시한 객체로 변환(두번째 인자)
        if (stringPref != null && stringPref != "[]") {
            searchArr = GsonBuilder().create().fromJson(
                stringPref, object : TypeToken<ArrayList<String>>() {}.type
            )
        }
    }

    //searchArr에 요소 추가함과 sharedPrefence 동기화
    @SuppressLint("NotifyDataSetChanged")
    fun addToSharedPreference(str:String) {
//        // ArrayList에 추가
        if(searchArr.contains(str)) searchArr.remove(str)
        searchArr.add(
            0,
            str
        )
        if(searchArr.size >= 20) searchArr.removeAt(19)

        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.mapEtSearch.windowToken, 0)

        val searchRequest = supportFragmentManager.findFragmentById(R.id.search_frame) as SearchRequestFragment
        searchRequest.searchRVAdapter.notifyDataSetChanged()

        saveSharedPreference()

    }

    //searchArr 요소를 sharedPreference에 저장
    fun saveSharedPreference(){
        // ArrayList를 json 형태의 String으로 변환
        // toJson → json으로 변환된 문자열 리턴
        val stringPrefs = GsonBuilder().create().toJson(
            searchArr,
            object : TypeToken<ArrayList<String>>() {}.type
        )
        editPref.putString("search_data", stringPrefs) // SharedPreferences에 push
        editPref.apply() // SharedPreferences 적용
    }


    // 키보드에서 done(완료) 클릭 시 , 원하는 뷰 클릭되게 하는 메소드
    fun getEditorActionListener(view: View): TextView.OnEditorActionListener {
        return TextView.OnEditorActionListener { textView, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                view.callOnClick()
            }
            false
        }
    }
}
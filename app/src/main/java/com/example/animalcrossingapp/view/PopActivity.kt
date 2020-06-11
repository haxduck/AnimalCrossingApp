package com.example.animalcrossingapp.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import com.example.animalcrossingapp.R
import com.example.animalcrossingapp.controller.App
import com.example.animalcrossingapp.database.AnimalCrossingDB
import com.example.animalcrossingapp.database.Current
import com.example.animalcrossingapp.room.AnimalVO
import com.example.animalcrossingapp.toolbar.SecondFragment
import it.mirko.rangeseekbar.OnRangeSeekBarListener
import it.mirko.rangeseekbar.RangeSeekBar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_pop.*
import kotlinx.android.synthetic.main.activity_pop.bugRB
import kotlinx.android.synthetic.main.activity_pop.bugTgBtn14
import kotlinx.android.synthetic.main.activity_pop.bugTgBtn15
import kotlinx.android.synthetic.main.activity_pop.bugTgBtn18
import kotlinx.android.synthetic.main.activity_pop.bugTgBtn22
import kotlinx.android.synthetic.main.activity_pop.bugTgBtn23
import kotlinx.android.synthetic.main.activity_pop.bugTgBtn4
import kotlinx.android.synthetic.main.activity_pop.bugTgBtn6
import kotlinx.android.synthetic.main.activity_pop.bugTgBtn9
import kotlinx.android.synthetic.main.activity_pop.fishRB
import kotlinx.android.synthetic.main.activity_pop.monthCB1
import kotlinx.android.synthetic.main.activity_pop.monthCB10
import kotlinx.android.synthetic.main.activity_pop.monthCB11
import kotlinx.android.synthetic.main.activity_pop.monthCB12
import kotlinx.android.synthetic.main.activity_pop.monthCB2
import kotlinx.android.synthetic.main.activity_pop.monthCB3
import kotlinx.android.synthetic.main.activity_pop.monthCB4
import kotlinx.android.synthetic.main.activity_pop.monthCB5
import kotlinx.android.synthetic.main.activity_pop.monthCB6
import kotlinx.android.synthetic.main.activity_pop.monthCB7
import kotlinx.android.synthetic.main.activity_pop.monthCB8
import kotlinx.android.synthetic.main.activity_pop.monthCB9
import kotlinx.android.synthetic.main.activity_pop.searchTgBtn1
import kotlinx.android.synthetic.main.activity_pop.searchTgBtn2
import kotlinx.android.synthetic.main.activity_pop.searchTgBtn3
import kotlinx.android.synthetic.main.activity_pop.searchTgBtn4
import kotlinx.android.synthetic.main.activity_pop.searchTgBtn5
import kotlinx.android.synthetic.main.activity_pop.searchTgBtn6
import kotlinx.android.synthetic.main.activity_pop.sortRG
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.activity_test.*

class PopActivity : AppCompatActivity(), OnRangeSeekBarListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_pop)

        Log.d("this", this.toString())


        time_rangeSeekbar.setMax(24)
        time_rangeSeekbar.setMinDifference(1)
        time_rangeSeekbar.startProgress = 0
        time_rangeSeekbar.endProgress = 24
        time_rangeSeekbar.setOnRangeSeekBarListener(this)
        price_rangeSeekbar.setMax(15000)
        price_rangeSeekbar.setMinDifference(100)
        price_rangeSeekbar.startProgress = 0
        price_rangeSeekbar.endProgress = 15000
        price_rangeSeekbar.setOnRangeSeekBarListener(this)
        minTimeText.text = time_rangeSeekbar.startProgress.toString()
        maxTimeText.text = time_rangeSeekbar.endProgress.toString()
        minPriceText.text = price_rangeSeekbar.startProgress.toString()
        maxPriceText.text = price_rangeSeekbar.endProgress.toString()

        var sort = "%%"
        var list = arrayListOf<Current>()
        val hemi = App.prefs.hemisphere
        val loc = App.prefs.language

        //잡음 여부
        var flag: String = "%%"
        switch1.setOnCheckedChangeListener { buttonView, isChecked ->
            if (switch1.isChecked) flag = "0"
            else flag = "1"
        }

        //종류
        sortRG.setOnCheckedChangeListener { group, checkid ->
            if (fishRB.isChecked == true) sort = "魚"
            else if (bugRB.isChecked == true) sort = "虫"
            else sort = "%%"
        }

        //월
        fun getMonths (): ArrayList<String> {
            var month = ArrayList<String>()
            if (monthCB1.isChecked == true) month.add("1月")
            if (monthCB2.isChecked == true) month.add("2月")
            if (monthCB3.isChecked == true) month.add("3月")
            if (monthCB4.isChecked == true) month.add("4月")
            if (monthCB5.isChecked == true) month.add("5月")
            if (monthCB6.isChecked == true) month.add("6月")
            if (monthCB7.isChecked == true) month.add("7月")
            if (monthCB8.isChecked == true) month.add("8月")
            if (monthCB9.isChecked == true) month.add("9月")
            if (monthCB10.isChecked == true) month.add("10月")
            if (monthCB11.isChecked == true) month.add("11月")
            if (monthCB12.isChecked == true) month.add("12月")
            if (month.size == 0) {
                for (i in 1..12) {
                    month.add("" + i + "月")
                }
            }
            return month
        }


        var minTime = 0
        var maxTime = 24
        var minPrice = 0
        var maxPrice = 15000


        fun getPlaces(): ArrayList<String> {
            var pla = ArrayList<String>()
            if (searchTgBtn1.isChecked == true) pla.add("海")
            if (searchTgBtn2.isChecked == true) pla.add("川")
            if (searchTgBtn3.isChecked == true) pla.add("池")
            if (searchTgBtn4.isChecked == true) pla.add("河口")
            if (searchTgBtn5.isChecked == true) pla.add("崖の上")
            if (searchTgBtn6.isChecked == true) pla.add("桟橋")
            if (bugTgBtn4.isChecked == true) pla.add("切り株")
            if (bugTgBtn6.isChecked == true) pla.add("岩")
            if (bugTgBtn9.isChecked == true) pla.add("木")
            if (bugTgBtn14.isChecked == true) pla.add("空中")
            if (bugTgBtn15.isChecked == true) pla.add("花")
            if (bugTgBtn18.isChecked == true) pla.add("ゴミ")
            if (bugTgBtn22.isChecked == true) pla.add("池")
            if (bugTgBtn23.isChecked == true) pla.add("川")
            if (pla.size == 0) {
                pla.add("海")
                pla.add("川")
                pla.add("池")
                pla.add("河口")
                pla.add("崖の上")
                pla.add("桟橋")
                pla.add("切り株")
                pla.add("木")
                pla.add("空中")
                pla.add("花")
                pla.add("ゴミ")
                pla.add("池")
                pla.add("川")
            }
            return pla
        }


        search_btn.setOnClickListener {
            //가격
            minPrice = price_rangeSeekbar.startProgress
            maxPrice = price_rangeSeekbar.endProgress

            //시간
            minTime = time_rangeSeekbar.startProgress
            maxTime = time_rangeSeekbar.endProgress

            Log.d("is", switch1.isChecked.toString())
            Log.d("flag", flag)
            Log.d("sort", sort)
            Log.d("months", getMonths().toString())
            Log.d("time", "" + minTime + "/" + maxTime)
            Log.d("price", "" + minPrice + "/" + maxPrice)
            Log.d("loc", getPlaces().toString())

            var searchMap = hashMapOf<String, Any>(
                "flag" to flag,
                "sort" to sort,
                "months" to getMonths(),
                "minTime" to minTime,
                "maxTime" to maxTime,
                "minPrice" to minPrice,
                "maxPrice" to maxPrice,
                "places" to getPlaces()
            )
            /*val bundle: Bundle = Bundle()
            bundle.putSerializable("searchMap", searchMap)
            val frg = SecondFragment()
            frg.arguments = bundle
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_fragment, frg).addToBackStack(null).commit()*/
            var intent = Intent(this, MainActivity::class.java)
            intent.putExtra("searchMap", searchMap)
            startActivity(intent)
        }


        //한글패치
        if (App.prefs.language == "ko") {
            textView10.setText("보유/미보유")
            textView9.setText("종류")
            fishRB.setText("물고기")
            bugRB.setText("곤충")
            BothRB.setText("모두")
            textView6.setText("월")
            monthCB1.setText("1월")
            monthCB2.setText("2월")
            monthCB3.setText("3월")
            monthCB4.setText("4월")
            monthCB5.setText("5월")
            monthCB6.setText("6월")
            monthCB7.setText("7월")
            monthCB8.setText("8월")
            monthCB9.setText("9월")
            monthCB10.setText("10월")
            monthCB11.setText("11월")
            monthCB12.setText("12월")
            textView5.setText("가격")
            textView.setText("시간대")
            textView7.setText("서식지")
            textView8.setText("서식지")
            bugTgBtn23.setText("강")
            bugTgBtn9.setText("나무")
            bugTgBtn6.setText("바위")
            bugTgBtn15.setText("꽃")
            bugTgBtn22.setText("연못")
            bugTgBtn4.setText("그루터기")
            bugTgBtn14.setText("공중")
            bugTgBtn18.setText("쓰레기")
            searchTgBtn1.setText("바다")
            searchTgBtn2.setText("강")
            searchTgBtn3.setText("연못")
            searchTgBtn4.setText("하구")
            searchTgBtn5.setText("벼랑위")
            searchTgBtn6.setText("부두")
            search_btn.setText("검색")
        }

    }


    override fun onRangeValues(rangeSeekBar: RangeSeekBar?, start: Int, end: Int) {
        if (rangeSeekBar!!.id == R.id.time_rangeSeekbar) {
            minTimeText.text = start.toString()
            maxTimeText.text = end.toString()
        } else if (rangeSeekBar.id == R.id.price_rangeSeekbar) {
            minPriceText.text = start.toString()
            maxPriceText.text = end.toString()
        }

    }

}


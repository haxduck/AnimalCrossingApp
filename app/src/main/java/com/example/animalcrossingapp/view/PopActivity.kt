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
import it.mirko.rangeseekbar.RangeSeekBar
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

class PopActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_pop)

        var sort = ""
        var list = arrayListOf<Current>()
        val hemi = App.prefs.hemisphere
        val loc = App.prefs.language



        fun searchFB(): MutableSet<AnimalVO> {

            sortRG.setOnCheckedChangeListener { group, checkid ->
                if (fishRB.isChecked == true) sort = "fish"
                else if (bugRB.isChecked == true) sort = "bug"
                else sort = "all"
            }

            fun monthCalc(): ArrayList<String> {
                var month = ArrayList<String>()
                if (monthCB1.isChecked == true) month.add("1")
                if (monthCB2.isChecked == true) month.add("2")
                if (monthCB3.isChecked == true) month.add("3")
                if (monthCB4.isChecked == true) month.add("4")
                if (monthCB5.isChecked == true) month.add("5")
                if (monthCB6.isChecked == true) month.add("6")
                if (monthCB7.isChecked == true) month.add("7")
                if (monthCB8.isChecked == true) month.add("8")
                if (monthCB9.isChecked == true) month.add("9")
                if (monthCB10.isChecked == true) month.add("10")
                if (monthCB11.isChecked == true) month.add("11")
                if (monthCB12.isChecked == true) month.add("12")
                return month

            }

            var tMinVal = 0
            var tMaxVal = 24
            var min = 0
            var max = 15000

            price_rangeSeekbar.setOnRangeSeekBarListener { rangeSeekBar: RangeSeekBar?, start: Int, end: Int ->
                if (rangeSeekBar != null) {
                    rangeSeekBar.setMax(24)
                    tMinVal = rangeSeekBar.startProgress
                    tMaxVal = rangeSeekBar.endProgress
                }
            }

            time_rangeSeekbar.setOnRangeSeekBarListener { rangeSeekBar: RangeSeekBar?, start: Int, end: Int ->
                if (rangeSeekBar != null) {
                    rangeSeekBar.setMax(15000)
                    min = rangeSeekBar.startProgress
                    max = rangeSeekBar.endProgress
                }

            }

            fun habitat(): ArrayList<String> {
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
                return pla
            }

            val testdb = AnimalCrossingDB.getInstance(this)!!

            //1차 검색
            val testList =
                testdb.animalCrossingDao().select(hemi, sort, min, max, habitat())
            Log.d("list", testList.size.toString())

            //월 배열화 후 검색
            val nlist = mutableSetOf<Int>()
            val alist = arrayListOf<AnimalVO>()
            val mlist = monthCalc().map { it.replace("月", "").toInt() }

            //시간 배열화 후 검색
            var n = tMinVal
            var m = tMaxVal
            val rarr: ArrayList<Int> = arrayListOf()
            val aarr = mutableSetOf<AnimalVO>()
            for (i in n..m) {
                rarr.add(i)
            }
//        alist.forEach {
//            val narr = it.time!!.replace("[", "").replace("]", "").split(", ").map { it.toInt() }
//            for (i in rarr) {
//                if (narr.indexOf(i) >= 0) {
//                    aarr.add(testdb.animalCrossingDao().selectId(it.aid))
//                }
//            }
//        }


            var str = ""
            aarr.forEach {
                str += "[ " + it.aid + ", " + it.name + ", " + it.sort + ", " + it.price + " ]" + "\n"
            }
            Log.d(
                "kw",
                sort.toString() + min.toString() + max.toString() + mlist + habitat() + rarr
            )
            Log.d("total", aarr.size.toString())
            val tmp = "show"
//            val id = this.getResources().getIdentifier(tmp, "drawable", this.getPackageName())
//            Log.d("id", id.toString())

            return aarr
        }
        search_btn.setOnClickListener {
            val list = searchFB()
            val slist = arrayListOf<AnimalVO>()
            val bundle: Bundle = Bundle()
            list.forEach{
                slist.add(it)
            }
            bundle.putParcelableArrayList("slist", slist)
            val frg = SecondFragment()
            frg.arguments = bundle
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_fragment, frg).addToBackStack(null).commit()
        }



        /*if (list.addAll(arguments?.getParcelableArrayList("list")!!) == null ){
            var clist = db.animalCrossingDao().selectAll()
            clist.forEach {
                list.add(it)
            }
        } else {
            list.addAll(arguments?.getParcelableArrayList("list")!!)
        }*/

        //한글패치
        if(App.prefs.language == "ko"){
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
}
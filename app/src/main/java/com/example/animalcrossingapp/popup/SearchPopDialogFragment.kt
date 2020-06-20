package com.example.animalcrossingapp.popup

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.annotation.RequiresApi
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.inflate
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.*
import com.example.animalcrossingapp.R
import com.example.animalcrossingapp.controller.App
import com.example.animalcrossingapp.database.AnimalCrossingDB
import com.example.animalcrossingapp.database.Current
import com.example.animalcrossingapp.databinding.ActivityPopBinding
import com.example.animalcrossingapp.databinding.ActivityPopBinding.inflate
import com.example.animalcrossingapp.databinding.ListviewitemBinding
import com.example.animalcrossingapp.model.AnimalViewModel
import com.example.animalcrossingapp.model.KeywordViewModel
import com.example.animalcrossingapp.toolbar.SecondFragment
import com.example.animalcrossingapp.view.MainActivity
import it.mirko.rangeseekbar.OnRangeSeekBarListener
import it.mirko.rangeseekbar.RangeSeekBar
import kotlinx.android.synthetic.main.activity_pop.*
import kotlinx.android.synthetic.main.activity_pop.view.*
import kotlinx.android.synthetic.main.listviewitem.view.*
import java.lang.IllegalStateException

class SearchPopDialogFragment : DialogFragment(), OnRangeSeekBarListener {

    private lateinit var aView: Any
    private lateinit var aModel: Any
    private lateinit var mContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onDetach() {
        super.onDetach()
        mContext
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val binding = inflate<ActivityPopBinding>(
            LayoutInflater.from(context), R.layout.activity_pop, null, false
        )
        val view = binding.root
        aView = view
        val model = ViewModelProvider(activity as ViewModelStoreOwner)[KeywordViewModel::class.java]
        aModel = model

        model.switch.observe(activity as MainActivity, Observer { view.switch1.isChecked = it })
        model.sort.observe(activity as MainActivity, Observer {
            view.sortRG.check(it)
            if (it == R.id.fishRB) selectFish()
            else if (it == R.id.bugRB) selectBug()
            else selectAll(mContext)
        })
        model.m1.observe(activity as MainActivity, Observer { view.monthCB1.isChecked = it })
        model.m2.observe(activity as MainActivity, Observer { view.monthCB2.isChecked = it })
        model.m3.observe(activity as MainActivity, Observer { view.monthCB3.isChecked = it })
        model.m4.observe(activity as MainActivity, Observer { view.monthCB4.isChecked = it })
        model.m5.observe(activity as MainActivity, Observer { view.monthCB5.isChecked = it })
        model.m6.observe(activity as MainActivity, Observer { view.monthCB6.isChecked = it })
        model.m7.observe(activity as MainActivity, Observer { view.monthCB7.isChecked = it })
        model.m8.observe(activity as MainActivity, Observer { view.monthCB8.isChecked = it })
        model.m9.observe(activity as MainActivity, Observer { view.monthCB9.isChecked = it })
        model.m10.observe(activity as MainActivity, Observer { view.monthCB10.isChecked = it })
        model.m11.observe(activity as MainActivity, Observer { view.monthCB11.isChecked = it })
        model.m12.observe(activity as MainActivity, Observer { view.monthCB12.isChecked = it })
        model.minT.observe(
            activity as MainActivity,
            Observer { view.time_rangeSeekbar.startProgress = it })
        model.maxT.observe(
            activity as MainActivity,
            Observer { view.time_rangeSeekbar.endProgress = it })
        model.minP.observe(
            activity as MainActivity,
            Observer { view.price_rangeSeekbar.startProgress = it })
        model.maxP.observe(
            activity as MainActivity,
            Observer { view.price_rangeSeekbar.endProgress = it })
        model.b1.observe(activity as MainActivity, Observer { view.bugTgBtn1.isChecked = it })
        model.b2.observe(activity as MainActivity, Observer { view.bugTgBtn2.isChecked = it })
        model.b3.observe(activity as MainActivity, Observer { view.bugTgBtn3.isChecked = it })
        model.b4.observe(activity as MainActivity, Observer { view.bugTgBtn4.isChecked = it })
        model.b5.observe(activity as MainActivity, Observer { view.bugTgBtn5.isChecked = it })
        model.b6.observe(activity as MainActivity, Observer { view.bugTgBtn6.isChecked = it })
        model.b7.observe(activity as MainActivity, Observer { view.bugTgBtn7.isChecked = it })
        model.b8.observe(activity as MainActivity, Observer { view.bugTgBtn8.isChecked = it })
        model.f1.observe(activity as MainActivity, Observer { view.searchTgBtn1.isChecked = it })
        model.f2.observe(activity as MainActivity, Observer { view.searchTgBtn2.isChecked = it })
        model.f3.observe(activity as MainActivity, Observer { view.searchTgBtn3.isChecked = it })
        model.f4.observe(activity as MainActivity, Observer { view.searchTgBtn4.isChecked = it })
        model.f5.observe(activity as MainActivity, Observer { view.searchTgBtn5.isChecked = it })
        model.f6.observe(activity as MainActivity, Observer { view.searchTgBtn6.isChecked = it })


        /////
        view.time_rangeSeekbar.setMax(24)
        view.time_rangeSeekbar.setMinDifference(1)
        /*view.time_rangeSeekbar.startProgress = 0
        view.time_rangeSeekbar.endProgress = 24*/
        view.time_rangeSeekbar.setOnRangeSeekBarListener(this)
        view.price_rangeSeekbar.setMax(15000)
        view.price_rangeSeekbar.setMinDifference(100)
        /*view.price_rangeSeekbar.startProgress = 0
        view.price_rangeSeekbar.endProgress = 15000*/
        view.price_rangeSeekbar.setOnRangeSeekBarListener(this)
        view.minTimeText.text = view.time_rangeSeekbar.startProgress.toString()
        view.maxTimeText.text = view.time_rangeSeekbar.endProgress.toString()
        view.minPriceText.text = view.price_rangeSeekbar.startProgress.toString()
        view.maxPriceText.text = view.price_rangeSeekbar.endProgress.toString()

        var sort = "%%"
        var list = arrayListOf<Current>()
        val hemi = App.prefs.hemisphere
        val loc = App.prefs.language

        //잡음 여부
        var flag: String = "1"
        if (view.switch1.isChecked) flag = "0"
        else flag = "1"
        view.switch1.setOnCheckedChangeListener { buttonView, isChecked ->
            model.switch.value = isChecked
            if (view.switch1.isChecked) flag = "0"
            else flag = "1"
        }

        //종류
        view.sortRG.setOnCheckedChangeListener { group, checkid ->
            model.sort.value = checkid
            if (checkid == R.id.fishRB) {
                sort = "魚"
//                selectFish()
            } else if (checkid == R.id.bugRB) {
                sort = "虫"
//                selectBug()
            } else if (checkid == R.id.BothRB) {
                sort = "%%"
//                selectAll()
            }
        }


        //월
        view.monthCB1.setOnCheckedChangeListener { buttonView, isChecked ->
            model.m1.value = isChecked
        }
        view.monthCB2.setOnCheckedChangeListener { buttonView, isChecked ->
            model.m2.value = isChecked
        }
        view.monthCB3.setOnCheckedChangeListener { buttonView, isChecked ->
            model.m3.value = isChecked
        }
        view.monthCB4.setOnCheckedChangeListener { buttonView, isChecked ->
            model.m4.value = isChecked
        }
        view.monthCB5.setOnCheckedChangeListener { buttonView, isChecked ->
            model.m5.value = isChecked
        }
        view.monthCB6.setOnCheckedChangeListener { buttonView, isChecked ->
            model.m6.value = isChecked
        }
        view.monthCB7.setOnCheckedChangeListener { buttonView, isChecked ->
            model.m7.value = isChecked
        }
        view.monthCB8.setOnCheckedChangeListener { buttonView, isChecked ->
            model.m8.value = isChecked
        }
        view.monthCB9.setOnCheckedChangeListener { buttonView, isChecked ->
            model.m9.value = isChecked
        }
        view.monthCB10.setOnCheckedChangeListener { buttonView, isChecked ->
            model.m10.value = isChecked
        }
        view.monthCB11.setOnCheckedChangeListener { buttonView, isChecked ->
            model.m11.value = isChecked
        }
        view.monthCB12.setOnCheckedChangeListener { buttonView, isChecked ->
            model.m12.value = isChecked
        }
        fun getMonths(): ArrayList<String> {
            var month = ArrayList<String>()
            if (view.monthCB1.isChecked == true) month.add("1月")
            if (view.monthCB2.isChecked == true) month.add("2月")
            if (view.monthCB3.isChecked == true) month.add("3月")
            if (view.monthCB4.isChecked == true) month.add("4月")
            if (view.monthCB5.isChecked == true) month.add("5月")
            if (view.monthCB6.isChecked == true) month.add("6月")
            if (view.monthCB7.isChecked == true) month.add("7月")
            if (view.monthCB8.isChecked == true) month.add("8月")
            if (view.monthCB9.isChecked == true) month.add("9月")
            if (view.monthCB10.isChecked == true) month.add("10月")
            if (view.monthCB11.isChecked == true) month.add("11月")
            if (view.monthCB12.isChecked == true) month.add("12月")
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

        view.bugTgBtn1.setOnCheckedChangeListener { buttonView, isChecked ->
            model.b1.value = isChecked
        }
        view.bugTgBtn2.setOnCheckedChangeListener { buttonView, isChecked ->
            model.b2.value = isChecked
        }
        view.bugTgBtn3.setOnCheckedChangeListener { buttonView, isChecked ->
            model.b3.value = isChecked
        }
        view.bugTgBtn4.setOnCheckedChangeListener { buttonView, isChecked ->
            model.b4.value = isChecked
        }
        view.bugTgBtn5.setOnCheckedChangeListener { buttonView, isChecked ->
            model.b5.value = isChecked
        }
        view.bugTgBtn6.setOnCheckedChangeListener { buttonView, isChecked ->
            model.b6.value = isChecked
        }
        view.bugTgBtn7.setOnCheckedChangeListener { buttonView, isChecked ->
            model.b7.value = isChecked
        }
        view.bugTgBtn8.setOnCheckedChangeListener { buttonView, isChecked ->
            model.b8.value = isChecked
        }
        view.searchTgBtn1.setOnCheckedChangeListener { buttonView, isChecked ->
            model.f1.value = isChecked
        }
        view.searchTgBtn2.setOnCheckedChangeListener { buttonView, isChecked ->
            model.f2.value = isChecked
        }
        view.searchTgBtn3.setOnCheckedChangeListener { buttonView, isChecked ->
            model.f3.value = isChecked
        }
        view.searchTgBtn4.setOnCheckedChangeListener { buttonView, isChecked ->
            model.f4.value = isChecked
        }
        view.searchTgBtn5.setOnCheckedChangeListener { buttonView, isChecked ->
            model.f5.value = isChecked
        }
        view.searchTgBtn6.setOnCheckedChangeListener { buttonView, isChecked ->
            model.f6.value = isChecked
        }

        fun getPlaces(): ArrayList<String> {
            var pla = ArrayList<String>()
            if (view.searchTgBtn1.isChecked) pla.add("海")
            if (view.searchTgBtn2.isChecked) {
                pla.add("川")
                pla.add("川の近くの空中")
            }
            if (view.searchTgBtn3.isChecked) pla.add("池")
            if (view.searchTgBtn4.isChecked) pla.add("河口")
            if (view.searchTgBtn5.isChecked) pla.add("崖の上")
            if (view.searchTgBtn6.isChecked) pla.add("桟橋")

            if (view.bugTgBtn1.isChecked) {
                pla.add("海岸")
                pla.add("砂浜の岩")
                pla.add("砂浜")
            }
            if (view.bugTgBtn2.isChecked) {
                pla.add("木")
                pla.add("木、ヤシの木")
                pla.add("木揺")
                pla.add("切り株")
                pla.add("ヤシの木")
            }
            if (view.bugTgBtn3.isChecked) {
                pla.add("岩")
                pla.add("雨の日の切り株、岩")
            }
            if (view.bugTgBtn4.isChecked) {
                pla.add("花")
                pla.add("黒・青・紫の花の周辺")
                pla.add("白い花")
            }
            if (view.bugTgBtn5.isChecked) {
                pla.add("地")
                pla.add("地面の家具のマーク")
                pla.add("土の中")
                pla.add("住人")
                pla.add("雪玉")
                pla.add("地面")
            }
            if (view.bugTgBtn6.isChecked) pla.add("切り株")
            if (view.bugTgBtn7.isChecked) {
                pla.add("空中")
                pla.add("街灯")
            }
            if (view.bugTgBtn8.isChecked) {
                pla.add("ゴミ")
                pla.add("カブ")
                pla.add("長靴、缶、タイヤ、腐ったカブ")
            }
            if (pla.size == 0) {
                pla.add("海")
                pla.add("川")
                pla.add("川の近くの空中")
                pla.add("池")
                pla.add("河口")
                pla.add("崖の上")
                pla.add("桟橋")
                pla.add("海岸")
                pla.add("砂浜の岩")
                pla.add("砂浜")
                pla.add("木")
                pla.add("木、ヤシの木")
                pla.add("木揺")
                pla.add("切り株")
                pla.add("ヤシの木")
                pla.add("岩")
                pla.add("雨の日の切り株、岩")
                pla.add("花")
                pla.add("黒・青・紫の花の周辺")
                pla.add("白い花")
                pla.add("地")
                pla.add("地面の家具のマーク")
                pla.add("土の中")
                pla.add("住人")
                pla.add("雪玉")
                pla.add("地面")
                pla.add("切り株")
                pla.add("空中")
                pla.add("街灯")
                pla.add("ゴミ")
                pla.add("カブ")
                pla.add("長靴、缶、タイヤ、腐ったカブ")
            }
            return pla
        }


        view.search_btn.setOnClickListener {
            //가격
            minPrice = view.price_rangeSeekbar.startProgress
            maxPrice = view.price_rangeSeekbar.endProgress

            //시간
            minTime = view.time_rangeSeekbar.startProgress
            maxTime = view.time_rangeSeekbar.endProgress

            var times = arrayListOf<Int>()
            for (i in (minTime..maxTime)) {
                times.add(i)
            }

            var searchMap = hashMapOf<String, Any>(
                "flag" to flag,
                "sort" to sort,
                "months" to getMonths(),
                "minTime" to minTime,
                "maxTime" to maxTime,
                "minPrice" to minPrice,
                "maxPrice" to maxPrice,
                "places" to getPlaces(),
                "times" to times
            )
            dismiss()
            val bundle: Bundle = Bundle()
            bundle.putSerializable("searchMap", searchMap)
            bundle.putString("selector", "detail")
            val frg = SecondFragment()
            frg.arguments = bundle
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_fragment, frg).addToBackStack(null).commit()
            /*var intent = Intent(requireContext(), MainActivity::class.java)
            intent.putExtra("searchMap", searchMap)
            startActivity(intent)*/
        }


        //한글패치
        if (App.prefs.language == "ko") {
            view.textView10.setText("보유/미보유")
            view.textView9.setText("종류")
            view.fishRB.setText("물고기")
            view.bugRB.setText("곤충")
            view.BothRB.setText("모두")
            view.textView6.setText("월")
            view.monthCB1.setText("1월")
            view.monthCB2.setText("2월")
            view.monthCB3.setText("3월")
            view.monthCB4.setText("4월")
            view.monthCB5.setText("5월")
            view.monthCB6.setText("6월")
            view.monthCB7.setText("7월")
            view.monthCB8.setText("8월")
            view.monthCB9.setText("9월")
            view.monthCB10.setText("10월")
            view.monthCB11.setText("11월")
            view.monthCB12.setText("12월")
            view.textView5.setText("가격")
            view.textView.setText("시간대")
            view.textView7.setText("서식지")
            view.textView8.setText("서식지")
            view.bugTgBtn1.setText("강")
            view.bugTgBtn2.setText("나무")
            view.bugTgBtn3.setText("바위")
            view.bugTgBtn4.setText("꽃")
            view.bugTgBtn5.setText("땅")
            view.bugTgBtn6.setText("그루터기")
            view.bugTgBtn7.setText("공중")
            view.bugTgBtn8.setText("쓰레기")
            view.searchTgBtn1.setText("바다")
            view.searchTgBtn2.setText("강")
            view.searchTgBtn3.setText("연못")
            view.searchTgBtn4.setText("하구")
            view.searchTgBtn5.setText("벼랑위")
            view.searchTgBtn6.setText("부두")
            view.search_btn.setText("검색")
        }
        ////

        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setView(view)
                .setPositiveButton("",
                    DialogInterface.OnClickListener { dialog, id ->

                    })
                .setNegativeButton("",
                    DialogInterface.OnClickListener { dialog, id ->

                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onRangeValues(rangeSeekBar: RangeSeekBar?, start: Int, end: Int) {

        if (rangeSeekBar!!.id == R.id.time_rangeSeekbar) {
            (aView as View).minTimeText.text = start.toString()
            (aView as View).maxTimeText.text = end.toString()
            (aModel as KeywordViewModel).minT.value = start
            (aModel as KeywordViewModel).maxT.value = end
        } else if (rangeSeekBar.id == R.id.price_rangeSeekbar) {
            (aView as View).minPriceText.text = start.toString()
            (aView as View).maxPriceText.text = end.toString()
            (aModel as KeywordViewModel).minP.value = start
            (aModel as KeywordViewModel).maxP.value = end
        }

    }

    fun selectFish() {
        val view = aView as View
        view.bugTgBtn1.setBackgroundColor(Color.WHITE)
        view.bugTgBtn1.setTextColor(Color.GRAY)
        view.bugTgBtn1.isClickable = false
        view.bugTgBtn2.setBackgroundColor(Color.WHITE)
        view.bugTgBtn2.setTextColor(Color.GRAY)
        view.bugTgBtn2.isClickable = false
        view.bugTgBtn3.setBackgroundColor(Color.WHITE)
        view.bugTgBtn3.setTextColor(Color.GRAY)
        view.bugTgBtn3.isClickable = false
        view.bugTgBtn4.setBackgroundColor(Color.WHITE)
        view.bugTgBtn4.setTextColor(Color.GRAY)
        view.bugTgBtn4.isClickable = false
        view.bugTgBtn5.setBackgroundColor(Color.WHITE)
        view.bugTgBtn5.setTextColor(Color.GRAY)
        view.bugTgBtn5.isClickable = false
        view.bugTgBtn6.setBackgroundColor(Color.WHITE)
        view.bugTgBtn6.setTextColor(Color.GRAY)
        view.bugTgBtn6.isClickable = false
        view.bugTgBtn7.setBackgroundColor(Color.WHITE)
        view.bugTgBtn7.setTextColor(Color.GRAY)
        view.bugTgBtn7.isClickable = false
        view.bugTgBtn8.setBackgroundColor(Color.WHITE)
        view.bugTgBtn8.setTextColor(Color.GRAY)
        view.bugTgBtn8.isClickable = false
        view.searchTgBtn1.setBackgroundColor(Color.parseColor("#E9E9E9"))
        view.searchTgBtn1.setTextColor(Color.BLACK)
        view.searchTgBtn1.isClickable = true
        view.searchTgBtn2.setBackgroundColor(Color.parseColor("#E9E9E9"))
        view.searchTgBtn2.setTextColor(Color.BLACK)
        view.searchTgBtn2.isClickable = true
        view.searchTgBtn3.setBackgroundColor(Color.parseColor("#E9E9E9"))
        view.searchTgBtn3.setTextColor(Color.BLACK)
        view.searchTgBtn3.isClickable = true
        view.searchTgBtn4.setBackgroundColor(Color.parseColor("#E9E9E9"))
        view.searchTgBtn4.setTextColor(Color.BLACK)
        view.searchTgBtn4.isClickable = true
        view.searchTgBtn5.setBackgroundColor(Color.parseColor("#E9E9E9"))
        view.searchTgBtn5.setTextColor(Color.BLACK)
        view.searchTgBtn5.isClickable = true
        view.searchTgBtn6.setBackgroundColor(Color.parseColor("#E9E9E9"))
        view.searchTgBtn6.setTextColor(Color.BLACK)
        view.searchTgBtn6.isClickable = true

        view.searchTgBtn1.background =
            getDrawable(mContext, R.drawable.button_search)
        view.searchTgBtn2.background =
            getDrawable(mContext, R.drawable.button_search)
        view.searchTgBtn3.background =
            getDrawable(mContext, R.drawable.button_search)
        view.searchTgBtn4.background =
            getDrawable(mContext, R.drawable.button_search)
        view.searchTgBtn5.background =
            getDrawable(mContext, R.drawable.button_search)
        view.searchTgBtn6.background =
            getDrawable(mContext, R.drawable.button_search)
    }

    fun selectBug() {
        val view = aView as View
        view.searchTgBtn1.setBackgroundColor(Color.WHITE)
        view.searchTgBtn1.setTextColor(Color.GRAY)
        view.searchTgBtn1.isClickable = false
        view.searchTgBtn2.setBackgroundColor(Color.WHITE)
        view.searchTgBtn2.setTextColor(Color.GRAY)
        view.searchTgBtn2.isClickable = false
        view.searchTgBtn3.setBackgroundColor(Color.WHITE)
        view.searchTgBtn3.setTextColor(Color.GRAY)
        view.searchTgBtn3.isClickable = false
        view.searchTgBtn4.setBackgroundColor(Color.WHITE)
        view.searchTgBtn4.setTextColor(Color.GRAY)
        view.searchTgBtn4.isClickable = false
        view.searchTgBtn5.setBackgroundColor(Color.WHITE)
        view.searchTgBtn5.setTextColor(Color.GRAY)
        view.searchTgBtn5.isClickable = false
        view.searchTgBtn6.setBackgroundColor(Color.WHITE)
        view.searchTgBtn6.setTextColor(Color.GRAY)
        view.searchTgBtn6.isClickable = false

        view.bugTgBtn1.setBackgroundColor(Color.parseColor("#E9E9E9"))
        view.bugTgBtn1.setTextColor(Color.BLACK)
        view.bugTgBtn1.isClickable = true
        view.bugTgBtn2.setBackgroundColor(Color.parseColor("#E9E9E9"))
        view.bugTgBtn2.setTextColor(Color.BLACK)
        view.bugTgBtn2.isClickable = true
        view.bugTgBtn3.setBackgroundColor(Color.parseColor("#E9E9E9"))
        view.bugTgBtn3.setTextColor(Color.BLACK)
        view.bugTgBtn3.isClickable = true
        view.bugTgBtn4.setBackgroundColor(Color.parseColor("#E9E9E9"))
        view.bugTgBtn4.setTextColor(Color.BLACK)
        view.bugTgBtn4.isClickable = true
        view.bugTgBtn5.setBackgroundColor(Color.parseColor("#E9E9E9"))
        view.bugTgBtn5.setTextColor(Color.BLACK)
        view.bugTgBtn5.isClickable = true
        view.bugTgBtn6.setBackgroundColor(Color.parseColor("#E9E9E9"))
        view.bugTgBtn6.setTextColor(Color.BLACK)
        view.bugTgBtn6.isClickable = true
        view.bugTgBtn7.setBackgroundColor(Color.parseColor("#E9E9E9"))
        view.bugTgBtn7.setTextColor(Color.BLACK)
        view.bugTgBtn7.isClickable = true
        view.bugTgBtn8.setBackgroundColor(Color.parseColor("#E9E9E9"))
        view.bugTgBtn8.setTextColor(Color.BLACK)
        view.bugTgBtn8.isClickable = true

        view.bugTgBtn1.background = getDrawable(mContext, R.drawable.button_search)
        view.bugTgBtn2.background = getDrawable(mContext, R.drawable.button_search)
        view.bugTgBtn3.background = getDrawable(mContext, R.drawable.button_search)
        view.bugTgBtn4.background = getDrawable(mContext, R.drawable.button_search)
        view.bugTgBtn5.background = getDrawable(mContext, R.drawable.button_search)
        view.bugTgBtn6.background = getDrawable(mContext, R.drawable.button_search)
        view.bugTgBtn7.background = getDrawable(mContext, R.drawable.button_search)
        view.bugTgBtn8.background = getDrawable(mContext, R.drawable.button_search)
    }

    fun selectAll(context: Context){
        val view = aView as View
        view.bugTgBtn1.setBackgroundColor(Color.parseColor("#E9E9E9"))
        view.bugTgBtn1.setTextColor(Color.BLACK)
        view.bugTgBtn1.isClickable = true
        view.bugTgBtn2.setBackgroundColor(Color.parseColor("#E9E9E9"))
        view.bugTgBtn2.setTextColor(Color.BLACK)
        view.bugTgBtn2.isClickable = true
        view.bugTgBtn3.setBackgroundColor(Color.parseColor("#E9E9E9"))
        view.bugTgBtn3.setTextColor(Color.BLACK)
        view.bugTgBtn3.isClickable = true
        view.bugTgBtn4.setBackgroundColor(Color.parseColor("#E9E9E9"))
        view.bugTgBtn4.setTextColor(Color.BLACK)
        view.bugTgBtn4.isClickable = true
        view.bugTgBtn5.setBackgroundColor(Color.parseColor("#E9E9E9"))
        view.bugTgBtn5.setTextColor(Color.BLACK)
        view.bugTgBtn5.isClickable = true
        view.bugTgBtn6.setBackgroundColor(Color.parseColor("#E9E9E9"))
        view.bugTgBtn6.setTextColor(Color.BLACK)
        view.bugTgBtn6.isClickable = true
        view.bugTgBtn7.setBackgroundColor(Color.parseColor("#E9E9E9"))
        view.bugTgBtn7.setTextColor(Color.BLACK)
        view.bugTgBtn7.isClickable = true
        view.bugTgBtn8.setBackgroundColor(Color.parseColor("#E9E9E9"))
        view.bugTgBtn8.setTextColor(Color.BLACK)
        view.bugTgBtn8.isClickable = true

        view.searchTgBtn1.setBackgroundColor(Color.parseColor("#E9E9E9"))
        view.searchTgBtn1.setTextColor(Color.BLACK)
        view.searchTgBtn1.isClickable = true
        view.searchTgBtn2.setBackgroundColor(Color.parseColor("#E9E9E9"))
        view.searchTgBtn2.setTextColor(Color.BLACK)
        view.searchTgBtn2.isClickable = true
        view.searchTgBtn3.setBackgroundColor(Color.parseColor("#E9E9E9"))
        view.searchTgBtn3.setTextColor(Color.BLACK)
        view.searchTgBtn3.isClickable = true
        view.searchTgBtn4.setBackgroundColor(Color.parseColor("#E9E9E9"))
        view.searchTgBtn4.setTextColor(Color.BLACK)
        view.searchTgBtn4.isClickable = true
        view.searchTgBtn5.setBackgroundColor(Color.parseColor("#E9E9E9"))
        view.searchTgBtn5.setTextColor(Color.BLACK)
        view.searchTgBtn5.isClickable = true
        view.searchTgBtn6.setBackgroundColor(Color.parseColor("#E9E9E9"))
        view.searchTgBtn6.setTextColor(Color.BLACK)
        view.searchTgBtn6.isClickable = true

        view.searchTgBtn1.background =
            getDrawable(mContext, R.drawable.button_search)
        view.searchTgBtn2.background =
            getDrawable(mContext, R.drawable.button_search)
        view.searchTgBtn3.background =
            getDrawable(mContext, R.drawable.button_search)
        view.searchTgBtn4.background =
            getDrawable(mContext, R.drawable.button_search)
        view.searchTgBtn5.background =
            getDrawable(mContext, R.drawable.button_search)
        view.searchTgBtn6.background =
            getDrawable(mContext, R.drawable.button_search)

        view.bugTgBtn1.background = getDrawable(mContext, R.drawable.button_search)
        view.bugTgBtn2.background = getDrawable(mContext, R.drawable.button_search)
        view.bugTgBtn3.background = getDrawable(mContext, R.drawable.button_search)
        view.bugTgBtn4.background = getDrawable(mContext, R.drawable.button_search)
        view.bugTgBtn5.background = getDrawable(mContext, R.drawable.button_search)
        view.bugTgBtn6.background = getDrawable(mContext, R.drawable.button_search)
        view.bugTgBtn7.background = getDrawable(mContext, R.drawable.button_search)
        view.bugTgBtn8.background = getDrawable(mContext, R.drawable.button_search)

    }

}
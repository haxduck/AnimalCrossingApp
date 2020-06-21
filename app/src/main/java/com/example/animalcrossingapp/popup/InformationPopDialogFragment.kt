package com.example.animalcrossingapp.popup

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.animalcrossingapp.R
import com.example.animalcrossingapp.controller.App
import com.example.animalcrossingapp.database.AnimalCrossingDB
import com.example.animalcrossingapp.database.Current
import com.example.animalcrossingapp.databinding.ListPopCopyBinding
import com.example.animalcrossingapp.databinding.ListviewitemBinding
import com.example.animalcrossingapp.model.AnimalViewModel
import com.example.animalcrossingapp.model.KeywordViewModel
import com.example.animalcrossingapp.view.MainActivity
import kotlinx.android.synthetic.main.gridviewitem2.view.*
import kotlinx.android.synthetic.main.list_pop_copy.view.*
import kotlinx.android.synthetic.main.listviewitem.view.*
import java.lang.IllegalStateException
import java.util.*
import kotlin.collections.ArrayList

class InformationPopDialogFragment : DialogFragment() {

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val orginAnimal = arguments?.getParcelable<Current>("animal")
        val model: AnimalViewModel = ViewModelProvider(this).get(AnimalViewModel::class.java)
        val dao = AnimalCrossingDB.getInstance(requireContext())?.animalCrossingDao()!!
        val lang = App.prefs.language
        val animal = dao.selectCode(
            orginAnimal?.information_code!!.toUpperCase(Locale.ROOT),
            App.prefs.hemisphere!!)
        //
        val hourStr: String
        val monthStr: String
        var nextMonth: String = ""
        var nextDay = ""
        when (App.prefs.language) {
            "ja" -> {
                hourStr = "時"
                monthStr = "月"
            }
            "ko" -> {
                hourStr = "시"
                monthStr = "월"
            }
            else -> {
                hourStr = "時"
                monthStr = "月"
            }
        }

        //시간 출력
        var tarr = animal.time!!.split(",").map{it.toInt()}.toCollection(ArrayList())
        tarr.sortWith(compareBy({it}))
        var str = ""
        //내일 포함
        if (tarr.get(0) == 1 && tarr.get(tarr.size - 1) == 24 && tarr.size < 24) {
            var i = 0
            while (tarr.get(i+1) - tarr.get(i) == 1) {
                tarr.add(tarr.get(0))
                tarr.remove(tarr.get(0))
            }
            tarr.add(tarr.get(0))
            tarr.remove(tarr.get(0))
            when(App.prefs.language) {
                "ko" -> nextDay = "내일 "
                "ja" -> nextDay = "翌日 "
            }
        }
        //기간 나누기
        var oneFlag = false
        var twoFlag = false
        for ( i in 0..tarr.size - 1 ) {
            var flag = false
            if ( i == 0 && tarr.size > 1) {
                if (tarr.get(1) - tarr.get(0) > 1) {
                    twoFlag = true
                } else {
                    str += tarr.get(0)
                }
            }
            if ( i > 0 && tarr.get(i) - tarr.get(i - 1) > 1 ) {
                if (oneFlag == false) {
                    str += " ~ ${tarr.get(i-1)}${hourStr}, ${tarr.get(i)}"
                    if (i == tarr.size - 1) str += hourStr
                } else {
                    if (i == tarr.size - 1) {
                        str += "${hourStr}, ${tarr.get(i)}${hourStr}"
                    } else {
                        str += ", ${tarr.get(i)}${hourStr}"
                    }
                    oneFlag = false
                }
                if ( i == tarr.size -1 && tarr.size > 1 ) flag = true
            }
            if ( i == tarr.size -1 && tarr.size > 1 && flag == false ) {
                str += " ~ ${nextDay}${tarr.get(i)}${hourStr}"
            }
            if (tarr.size == 1) str += "${tarr.get(0)}${hourStr}"
        }
        if (twoFlag == true) str = str.replaceFirst("~", "")
        if (tarr.size == 24) {
            str = str.replaceFirst("1", "0")
        }
        //
        var marr = animal.month!!.replace("月", "").split(",").map{it.toInt()}.toCollection(ArrayList())
        marr.sortWith(compareBy({it}))
        var str1 = ""
        //내년 포함
        if (marr.get(0) == 1 && marr.get(marr.size - 1) == 12 && marr.size < 12) {
            var i = 0
            while (marr.get(i+1) - marr.get(i) == 1) {
                marr.add(marr.get(0))
                marr.remove(marr.get(0))
            }
            marr.add(marr.get(0))
            marr.remove(marr.get(0))
            when(App.prefs.language) {
                "ko" -> nextMonth = "내년 "
                "ja" -> nextMonth = "来年 "
            }
        }
        // 월 출력
        //기간 나누기
        var oneFlag1 = false
        var twoFlag1 = false
        for ( i in 0..marr.size - 1 ) {
            var flag = false
            if ( i == 0 && marr.size > 1 ) {
                if (marr.get(1) - marr.get(0) > 1) {
                    twoFlag1 = true
                } else {
                    str1 += marr.get(0)
                }
            }
            if ( i > 0 && marr.get(i) - marr.get(i - 1) > 1 ) {
                if (oneFlag1 == false) {
                    str1 += " ~ ${marr.get(i-1)}${monthStr}, ${marr.get(i)}"
                    if (i == marr.size - 1) str1 += monthStr
                } else {
                    if (i == marr.size - 1) {
                        str1 += "${monthStr}, ${marr.get(i)}${monthStr}"
                    } else {
                        str1 += ", ${marr.get(i)}"
                    }
                    oneFlag1 = false
                }
                if (i < marr.size - 1 && marr.get(i) - marr.get(i-1) > 1 && marr.get(i+1) - marr.get(i) > 1) {
                    oneFlag1 = true
                }
                if ( i == marr.size -1 && marr.size > 1 ) flag = true
            }
            if ( i == marr.size -1 && marr.size > 1 && flag == false ) {
                str1 += " ~ ${nextMonth}${marr.get(i)}${monthStr}"
            }
            if (marr.size == 1) str1 += "${marr.get(0)}${monthStr}"
        }
        if ( twoFlag1 == true ) str1 = str1.replaceFirst("~", "")
        //
        val binding= DataBindingUtil.inflate<ListPopCopyBinding>(
            LayoutInflater.from(context), R.layout.list_pop_copy, null, false)

        val view = binding.root

        binding.current = animal
        binding.month = str1
        binding.time = str
        binding.lang = lang

        var flag = animal?.flag
        var info = animal?.information_code!!
        view.search_btn.setOnClickListener {
            if (flag == "1") {
                dao.updateCatchFlag(info , "0")
                flag = "0"
            } else {
                dao.updateCatchFlag(info , "1")
                flag = "1"
            }
            dismiss()
        }
        //이번달 지나면 한동안 못잡는 것들
        var nextMonth1 = Calendar.getInstance().get(Calendar.MONTH) + 2
        if (nextMonth1 > 11) nextMonth1 = nextMonth1 - 11
        for (i in 0..marr.size - 1) {
            if (marr[i] == nextMonth1) {
                view.view_img1.setBackgroundResource(R.drawable.grid_wrap3)
                break
            } else {
                Log.d("ttt", "${marr} / ${nextMonth1}")
                view.view_img1.setBackgroundResource(R.drawable.grid_wrap3_uc)
            }
        }

        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setView(view)
                .setPositiveButton("",
                DialogInterface.OnClickListener { dialog, id ->
                    if (flag == "1") {
                        dao.updateCatchFlag(info , "0")
                        flag = "0"
                    } else {
                        dao.updateCatchFlag(info , "1")
                        flag = "1"
                    }
                })
                .setNegativeButton("",
                DialogInterface.OnClickListener { dialog, id ->

                })
            builder.create()
        }?: throw IllegalStateException("Activity cannot be null")
    }

}
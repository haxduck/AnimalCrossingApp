package com.example.animalcrossingapp.controller

import android.content.Context
import com.example.animalcrossingapp.vo.AllVO
import com.example.animalcrossingapp.vo.BugVO
import com.example.animalcrossingapp.vo.FishVO
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

object MainController {

    //initialactivity
    fun readIniFlag(): String? {
        return App.prefs.initialFlag
    }

    fun onInitialFlag() {
        App.prefs.initialFlag = "1"
    }

    fun setHemiSphere(hemi: String) {
        App.prefs.hemisphere = hemi
    }

    //mainactivity
    fun currentTime(): String {
        val date: Date = Date()
        val cal = Calendar.getInstance()
        cal.time = date
        val hours = cal.get(Calendar.HOUR)
        val sdf = SimpleDateFormat("yyyy/M/d/ H:00 ~ ${hours + 1}:00")
        val currentDate = sdf.format(Date())
        return currentDate
    }

}
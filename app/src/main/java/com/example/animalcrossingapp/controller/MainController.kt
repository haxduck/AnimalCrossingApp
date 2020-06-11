package com.example.animalcrossingapp.controller

import android.app.Application
import com.example.animalcrossingapp.controller.App
import java.text.SimpleDateFormat
import java.util.*

object MainController: Application() {

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
        var hours = cal.get(Calendar.HOUR)
        if (hours > 12 ) hours = hours + 12
        val sdf = SimpleDateFormat("YYYY/MM/dd h:00")
        val currentDate = sdf.format(Date())
        return currentDate
    }

}
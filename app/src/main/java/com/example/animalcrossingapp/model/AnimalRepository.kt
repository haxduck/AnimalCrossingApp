package com.example.animalcrossingapp.model

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.animalcrossingapp.controller.App
import com.example.animalcrossingapp.database.AnimalCrossingDAO
import com.example.animalcrossingapp.database.Current
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class AnimalRepository(dao: AnimalCrossingDAO) {
    private val dao = dao
    private val hemishpere = App.prefs.hemisphere!!
    private val currentTime: String = Calendar.getInstance().get(Calendar.HOUR_OF_DAY).toString()
    private val thisMonth = Calendar.getInstance().get(Calendar.MONTH) + 1
    private val currentMonth = "" + thisMonth + "月"
    fun getAnimals(): LiveData<List<Current>> {
        return dao.selectAll(hemishpere)
    }
    fun getCurrent(): LiveData<List<Current>> {
        return dao.selectLiveCurrentAnimal(hemishpere, currentTime, currentMonth)
    }
    fun getArrange(): LiveData<List<Current>> {
        return dao.selectLiveArrange(hemishpere)
    }
    fun getDetail(map: HashMap<String, Any>): LiveData<List<Current>> {
        var flag = map["flag"] as String
        var sort = map["sort"] as String
        var months = map["months"] as ArrayList<String>
        var minTime = map["minTime"] as Int
        var maxTime = map["maxTime"] as Int
        var minPrice = map["minPrice"] as Int
        var maxPrice = map["maxPrice"] as Int
        var places = map["places"] as ArrayList<String>
        var times = map["times"] as ArrayList<Int>
        return dao.selectLiveDetail(flag, sort, months, minPrice, maxPrice, places, times)
    }
    fun getSearch(keyword: String) : LiveData<List<Current>> {
        return dao.selectLiveSearch(keyword)
    }
}
package com.example.animalcrossingapp.model

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.animalcrossingapp.controller.App
import com.example.animalcrossingapp.database.AnimalCrossingDAO
import com.example.animalcrossingapp.database.AnimalCrossingDB
import com.example.animalcrossingapp.database.Current
import java.util.*

class AnimalRepository(dao: AnimalCrossingDAO) {
    private val dao = dao
    private val hemishpere = App.prefs.hemisphere!!
    private val currentTime: String = Calendar.getInstance().get(Calendar.HOUR_OF_DAY).toString()
    private val thisMonth = Calendar.getInstance().get(Calendar.MONTH) + 1
    private val currentMonth = "" + thisMonth + "月"
    fun getAnimals(): LiveData<List<Current>> {
        return dao.selectAll()
    }
    fun getCurrent(): LiveData<List<Current>> {
        return dao.selectLiveCurrentAnimal(hemishpere, currentTime, currentMonth)
    }
    fun getArrange(): LiveData<List<Current>> {
        return dao.selectLiveArrange(hemishpere)
    }
}
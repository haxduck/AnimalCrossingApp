package com.example.animalcrossingapp.livedata

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.animalcrossingapp.database.AnimalCrossingDB
import com.example.animalcrossingapp.database.Current

class AnimalRepository(context: Context) {

    private val db  = AnimalCrossingDB.getInstance(context)!!

    private val animals: LiveData<List<Current>> by lazy {
        db.animalCrossingDao().selectLive()
    }

    fun getAll(): LiveData<List<Current>> {
        return animals
    }

}
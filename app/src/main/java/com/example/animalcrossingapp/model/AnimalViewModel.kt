package com.example.animalcrossingapp.model

import android.app.Application
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.animalcrossingapp.database.AnimalCrossingDB
import com.example.animalcrossingapp.database.Current
import com.example.animalcrossingapp.toolbar.FirstFragment
import kotlinx.android.synthetic.main.activity_pop.*

class AnimalViewModel(app: Application) : AndroidViewModel(app) {
    private val context = app.applicationContext
    private val dao = AnimalCrossingDB.getInstance(context)!!.animalCrossingDao()
    private val repository = AnimalRepository(dao)

    val animals = repository.getAnimals()
    val currentAnimals = repository.getCurrent()
    val arrangeAnimals = repository.getArrange()

    fun getDetail(map: HashMap<String, Any>): LiveData<List<Current>> {
        return repository.getDetail(map)
    }

    fun getSearch(keyword: String): LiveData<List<Current>> {
        return repository.getSearch(keyword)
    }
}
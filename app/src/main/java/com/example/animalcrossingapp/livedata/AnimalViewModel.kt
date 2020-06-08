package com.example.animalcrossingapp.livedata

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.animalcrossingapp.database.AnimalCrossingDB
import com.example.animalcrossingapp.database.Current

class AnimalViewModel(context: Context): ViewModel() {

    private val repository: AnimalRepository by lazy {
        AnimalRepository(context)
    }

    private val animals: LiveData<List<Current>> by lazy {
        repository.getAll()
    }

    fun getAll() = animals

}
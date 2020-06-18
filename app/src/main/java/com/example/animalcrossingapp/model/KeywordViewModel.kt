package com.example.animalcrossingapp.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.example.animalcrossingapp.R
import kotlinx.android.synthetic.main.activity_pop.view.*

class KeywordViewModel: ViewModel() {
    val switch: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    val sort: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>(R.id.BothRB)
    }
    val m1: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    val m2: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    val m3: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    val m4: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    val m5: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    val m6: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    val m7: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    val m8: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    val m9: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    val m10: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    val m11: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    val m12: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    val minT: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }
    val maxT: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>(24)
    }
    val minP: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }
    val maxP: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>(15000)
    }
    val b1: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    val b2: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    val b3: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    val b4: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    val b5: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    val b6: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    val b7: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    val b8: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    val f1: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    val f2: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    val f3: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    val f4: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    val f5: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    val f6: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
}
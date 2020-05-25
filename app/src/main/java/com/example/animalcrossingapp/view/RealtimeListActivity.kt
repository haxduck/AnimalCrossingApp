package com.example.animalcrossingapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.animalcrossingapp.R
import com.example.animalcrossingapp.controller.AllAdapter
import com.example.animalcrossingapp.controller.AnimalAdapter
import com.example.animalcrossingapp.controller.App
import com.example.animalcrossingapp.controller.MainController
import com.example.animalcrossingapp.room.AnimalDB
import com.example.animalcrossingapp.room.AnimalVO
import com.example.animalcrossingapp.vo.AllVO
import kotlinx.android.synthetic.main.activity_realtime_list.*
import java.util.*
import kotlin.collections.ArrayList

class RealtimeListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_realtime_list)

        val tempList = searchRealTimeList()
        val realTimeList: ArrayList<AnimalVO> = arrayListOf()
        tempList.forEach{
            realTimeList.add(it)
        }
        //listView.setText(flist.toString() + blist.toString())

        recycler_view.apply {
            layoutManager = LinearLayoutManager(this@RealtimeListActivity)
            adapter = AnimalAdapter(realTimeList) { animal ->
                Toast.makeText(this@RealtimeListActivity, "$animal", Toast.LENGTH_SHORT).show()

            }

        }

    }

    fun searchRealTimeList(): MutableSet<AnimalVO> {
        //실시간 현재 시간 / 반구 /
        val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        val hemisphere = App.prefs.hemisphere
        val db = AnimalDB.getInstance(this)!!
        val allList = db.animalDao().selectAll()
        val realTimeList = mutableSetOf<AnimalVO>()
        val thisMonthList = mutableSetOf<AnimalVO>()
        allList.forEach { animal ->
            //월
            val monthList =
                animal.nm!!.replace("[", "").replace("]", "").split(", ").map { it.toInt() }
            monthList.forEach {
                if (it == Calendar.getInstance().get(Calendar.MONTH) + 1) {
                    thisMonthList.add(animal)
                }
            }

            //시간
            thisMonthList.forEach { thisMonthAnimal ->
                val timeList = arrayListOf<String>()
                var intList: List<Int>
                timeList.add(thisMonthAnimal.time!!)
                timeList.forEach {
                    intList = it.replace("[", "").replace("]", "").split(", ").map { it.toInt() }
                    intList.forEach {
                        if (it == currentHour) {
                            realTimeList.add(thisMonthAnimal)
                        }
                    }
                }
            }
        }
        return realTimeList
    }

}
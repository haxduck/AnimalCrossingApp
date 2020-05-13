package com.example.animalcrossingapp.controller

import com.example.animalcrossingapp.vo.BugVO
import com.example.animalcrossingapp.vo.FishVO
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

object MainController {

    fun currentTime(): String {
        val date: Date = Date()
        val cal = Calendar.getInstance()
        cal.time = date
        val hours = cal.get(Calendar.HOUR_OF_DAY)
        val sdf = SimpleDateFormat("yyyy年M月d日 h:00 ~ ${hours + 1}:00")
        val currentDate = sdf.format(Date())
        return currentDate
    }

    fun currentFishList(): ArrayList<FishVO> {
        var fishList = arrayListOf<FishVO>(
            FishVO("물고기1", 1000, "c"),
            FishVO("물고기2", 2000, "")
        )
        return fishList
    }

    fun catchFishList(): ArrayList<FishVO> {
        var catchFishList: ArrayList<FishVO> = arrayListOf()
        currentFishList().forEach {
            if(it.catch_flag == "c") {
                catchFishList.add(it)
            }
        }
        return catchFishList
    }

    fun currentBugList(): ArrayList<BugVO> {
        var bugList = arrayListOf<BugVO>(
            BugVO("벌레1", 100, "c"),
            BugVO("벌레2", 200, "")
        )
        return bugList
    }

    fun catchBugList(): ArrayList<BugVO> {
        var catchBugList: ArrayList<BugVO> = arrayListOf()
        currentBugList().forEach {
            if(it.catch_flag == "c") {
                catchBugList.add(it)
            }
        }
        return catchBugList
    }

}
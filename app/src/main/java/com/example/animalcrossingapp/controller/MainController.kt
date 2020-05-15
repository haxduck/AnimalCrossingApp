package com.example.animalcrossingapp.controller

import android.content.Context
import com.example.animalcrossingapp.dao.AllDBHelper
import com.example.animalcrossingapp.dao.FishDBHelper
import com.example.animalcrossingapp.vo.AllVO
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
        val sdf = SimpleDateFormat("yyyy/M/d/ H:00 ~ ${hours + 1}:00")
        val currentDate = sdf.format(Date())
        return currentDate
    }

    fun currentAllList(context: Context): ArrayList<AllVO> {
        lateinit var allDBHelper: AllDBHelper
        allDBHelper = AllDBHelper(context)
        var allList = allDBHelper.readAll()

        /*var fishList = arrayListOf<FishVO>(
            FishVO("물고기1", 1000, "c"),
            FishVO("물고기2", 2000, "")
        )*/
        return allList
    }

    fun currentFishList(context: Context): ArrayList<AllVO> {
        var fishList: ArrayList<AllVO> = arrayListOf()
        currentAllList(context).forEach {
            if(it.sort == "f") {
                fishList.add(it)
            }
        }
        /*var fishList = arrayListOf<FishVO>(
            FishVO("물고기1", 1000, "c"),
            FishVO("물고기2", 2000, "")
        )*/
        return fishList
    }

    fun catchFishList(context: Context): ArrayList<AllVO> {
        var catchFishList: ArrayList<AllVO> = arrayListOf()
        currentFishList(context).forEach {
            if(it.catch_flag == "c") {
                catchFishList.add(it)
            }
        }
        return catchFishList
    }

    fun currentBugList(context: Context): ArrayList<AllVO> {
        var bugList: ArrayList<AllVO> = arrayListOf()
        currentAllList(context).forEach {
            if(it.sort == "b") {
                bugList.add(it)
            }
        }

        return bugList
    }

    fun catchBugList(context: Context): ArrayList<AllVO> {
        var catchBugList: ArrayList<AllVO> = arrayListOf()
        currentBugList(context).forEach {
            if(it.catch_flag == "c") {
                catchBugList.add(it)
            }
        }
        return catchBugList
    }

    lateinit var fishDBHelper: FishDBHelper
    fun checkCatch(fish: FishVO, context: Context) {
        fishDBHelper = FishDBHelper(context)
        if(fish.catch_flag == ""){
            fishDBHelper.updateFish(FishVO(fish.name_japan, fish.price, "c"))
        } else {
            fishDBHelper.updateFish(FishVO(fish.name_japan, fish.price, ""))
        }
    }

    lateinit var allDBHelper: AllDBHelper
    fun checkCatchA(all: AllVO, context: Context) {
        allDBHelper = AllDBHelper(context)
        if(all.catch_flag == ""){
            allDBHelper.updateAll(AllVO(all.name_japan, all.price, "c", all.sort))
        } else {
            allDBHelper.updateAll(AllVO(all.name_japan, all.price, "", all.sort))
        }
    }


}
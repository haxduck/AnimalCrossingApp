package com.example.animalcrossingapp.controller

import android.content.Context
import com.example.animalcrossingapp.dao.AllDBHelper
import com.example.animalcrossingapp.dao.FishDBHelper
import com.example.animalcrossingapp.vo.AllVO
import com.example.animalcrossingapp.vo.FishVO
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

object MainController {

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
        val hours = cal.get(Calendar.HOUR_OF_DAY)
        val sdf = SimpleDateFormat("yyyy/M/d/ H:00 ~ ${hours + 1}:00")
        val currentDate = sdf.format(Date())
        return currentDate
    }

    fun currentAllList(): ArrayList<AllVO> {
        lateinit var allDBHelper: AllDBHelper
        allDBHelper = AllDBHelper()
        var allList = allDBHelper.readAll()
        if (allList.size == 0 ) {
            throw Exception("システム障害が発生しました。")
        } else {
            return allList
        }
    }

    fun currentFishList(context: Context): ArrayList<AllVO> {
        var fishList: ArrayList<AllVO> = arrayListOf()
        currentAllList().forEach {
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
            if(it.catch_flag == "1") {
                catchFishList.add(it)
            }
        }
        return catchFishList
    }

    fun currentBugList(): ArrayList<AllVO> {
        var bugList: ArrayList<AllVO> = arrayListOf()
        currentAllList().forEach {
            if(it.sort == "b") {
                bugList.add(it)
            }
        }

        return bugList
    }

    fun catchBugList(): ArrayList<AllVO> {
        var catchBugList: ArrayList<AllVO> = arrayListOf()
        currentBugList().forEach {
            if(it.catch_flag == "1") {
                catchBugList.add(it)
            }
        }
        return catchBugList
    }

//    lateinit var fishDBHelper: FishDBHelper
//    fun checkCatch(fish: FishVO, context: Context) {
//        fishDBHelper = FishDBHelper(context)
//        if(fish.catch_flag == "0"){
//            fishDBHelper.updateFish(FishVO(fish.name_japan, fish.price, "1", fish.sort))
//        } else {
//            fishDBHelper.updateFish(FishVO(fish.name_japan, fish.price, "0", fish.sort))
//        }
//    }

    lateinit var allDBHelper: AllDBHelper
    fun checkCatchA(all: AllVO, context: Context) {
        allDBHelper = AllDBHelper()
        if(all.catch_flag == "0"){
            allDBHelper.updateAll(AllVO(all.name_japan, all.price, "1", all.sort))
        } else {
            allDBHelper.updateAll(AllVO(all.name_japan, all.price, "0", all.sort))
        }
    }


}
package com.example.animalcrossingapp.controller

import android.content.Context
import com.example.animalcrossingapp.room.AnimalDAO
import com.example.animalcrossingapp.room.AnimalVO
//import com.example.animalcrossingapp.dao.FishDBHelper
import com.example.animalcrossingapp.vo.BugVO
import com.example.animalcrossingapp.vo.FishVO

object ListController {

    fun AllFishList(context: Context): List<AnimalVO> {
        lateinit var fishDBHelper: AnimalDAO
//        fishDBHelper = FishDBHelper(context)
//
//
        var fishList = fishDBHelper.selectCatchFish()
//
//       if(fishList.size == 0){
//
//           throw Exception("システム障害が発生しました。")
//       }

        return fishList
    }
//
    fun AllBugList(context: Context): List<AnimalVO> {
        lateinit var bugDBHelper: AnimalDAO
//        bugDBHelper = BugDBHelper(context)


        var bugList = bugDBHelper.selectCatchBug()

        if(bugList.size == 0){

           /* throw Exception("システム障害が発生しました。")*/
        }

        return bugList
    }











}
package com.example.animalcrossingapp.controller

import android.content.Context
import com.example.animalcrossingapp.dao.BugDBHelper
import com.example.animalcrossingapp.dao.FishDBHelper
import com.example.animalcrossingapp.vo.BugVO
import com.example.animalcrossingapp.vo.FishVO

object ListController {

    fun AllFishList(context: Context): ArrayList<FishVO> {
        lateinit var fishDBHelper: FishDBHelper
        fishDBHelper = FishDBHelper(context)


        var fishList = fishDBHelper.readAllFishes()

       if(fishList.size == 0){

           throw Exception("システム障害が発生しました。")
       }

        return fishList
    }

    fun AllBugList(context: Context): ArrayList<BugVO> {
        lateinit var bugDBHelper: BugDBHelper
        bugDBHelper = BugDBHelper(context)


        var bugList = bugDBHelper.readAllBugs()

        if(bugList.size == 0){

           /* throw Exception("システム障害が発生しました。")*/
        }

        return bugList
    }











}
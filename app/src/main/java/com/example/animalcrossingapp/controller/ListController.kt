package com.example.animalcrossingapp.controller

import android.content.Context
import com.example.animalcrossingapp.dao.FishDBHelper
import com.example.animalcrossingapp.vo.FishVO

object ListController {

    fun AllFishList(context: Context): ArrayList<FishVO> {
        lateinit var fishDBHelper: FishDBHelper
        fishDBHelper = FishDBHelper(context)
        var fishList = fishDBHelper.readAllFishes()



        return fishList
    }

}
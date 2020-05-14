package com.example.animalcrossingapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.animalcrossingapp.R
import com.example.animalcrossingapp.controller.App
import com.example.animalcrossingapp.controller.MainController
import com.example.animalcrossingapp.dao.FishDBHelper
import com.example.animalcrossingapp.vo.BugVO
import com.example.animalcrossingapp.vo.FishVO
import kotlinx.android.synthetic.main.activity_all_fish_list.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    //lateinit var fishDBHelper: FishDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //fishDBHelper = FishDBHelper(this)
        //var fishes = fishDBHelper.readAllFishes()
        var context = this

        //첫 실행 판단 prefs.xml 저장
        val iniFlag = App.prefs.initialFlag
        Toast.makeText(this, "플래그: $iniFlag", Toast.LENGTH_LONG).show()

        if(iniFlag == "1") {
            setContentView(R.layout.activity_main)
        } else {
            setContentView(R.layout.activity_main)
            val nextIntent = Intent(this, InitialActivity::class.java)
            startActivity(nextIntent)
        }

        textView2.setText(
                    "リアルタイム情報" + "\n" +
                    MainController.currentTime()
        )

        textView5.setText(
            MainController.currentFishList(context).toString() + "\n"
            + MainController.currentBugList().toString()
        )

        var flist:ArrayList<FishVO> = MainController.currentFishList(context)
        var blist:ArrayList<BugVO> = MainController.currentBugList()
        textView5.setOnClickListener{
            val nextIntent = Intent(this, RealtimeListActivity::class.java)
            nextIntent.putExtra("flist", flist)
            nextIntent.putExtra("blist", blist)
            startActivity(nextIntent)
        }

        textView3.setText("" + MainController.catchFishList(context).size + "/" + MainController.currentFishList(context).size)
        textView4.setText("" + MainController.catchBugList().size + "/" + MainController.currentBugList().size)

        settingBtn.setOnClickListener{
            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
        }

        if(intent.hasExtra("msg")){
            hankyu.setText(intent.getStringExtra("msg"))
        } else {
            hankyu.setText(App.prefs.hemisphere)
        }

        //choi
         tvall1.setOnClickListener{
             val intent = Intent(this, AllFishList::class.java)
             startActivity(intent)

        }

    }

    //test
    fun clearXml(view: View) {
        App.prefs.initialFlag = ""
        App.prefs.hemisphere = ""
    }
    //

}

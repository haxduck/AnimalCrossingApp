package com.example.animalcrossingapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.animalcrossingapp.R
import com.example.animalcrossingapp.controller.App
import com.example.animalcrossingapp.controller.MainController
import com.example.animalcrossingapp.room.AnimalDB
import com.example.animalcrossingapp.room.AnimalVO
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val context = this

        val iniFlag = MainController.readIniFlag()
        Toast.makeText(this, "플래그: $iniFlag", Toast.LENGTH_LONG).show()

        if (iniFlag == "1") {
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


        /*//실시간 현재 시간 / 반구 /
        val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        val hemisphere = App.prefs.hemisphere
        val db = AnimalDB.getInstance(this)!!
        val allList = db.animalDao().selectAll()
        val realTimeList = mutableSetOf<AnimalVO>()
        val thisMonthList = mutableSetOf<AnimalVO>()
        allList.forEach { animal ->
            //월
            val monthList = animal.nm!!.replace("[", "").replace("]", "").split(", ").map { it.toInt() }
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
                timeList.forEach{
                    intList = it.replace("[", "").replace("]", "").split(", ").map { it.toInt() }
                    intList.forEach {
                        if (it == currentHour) {
                            realTimeList.add(thisMonthAnimal)
                        }
                    }
                }
            }

        }*/

        textView5.text = searchRealTimeList().toString()

        /* var flist:ArrayList<AllVO> = MainController.currentFishList(context)
         var blist:ArrayList<BugVO> = MainController.currentBugList()*/
        textView5.setOnClickListener {
            val nextIntent = Intent(this, RealtimeListActivity::class.java)
            /*nextIntent.putExtra("flist", flist)
            nextIntent.putExtra("blist", blist)*/
            startActivity(nextIntent)
        }

        textView3.setText(
            "" + MainController.catchFishList(context).size + "/" + MainController.currentFishList(
                context
            ).size
        )
        textView4.setText(
            "" + MainController.catchBugList(context).size + "/" + MainController.currentBugList(
                context
            ).size
        )

        settingBtn.setOnClickListener {
            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
        }

        if (intent.hasExtra("msg")) {
            hankyu.setText(intent.getStringExtra("msg"))
        } else {
            hankyu.setText(App.prefs.hemisphere)
        }
    }

    //test
    fun clearXml(view: View) {
        App.prefs.initialFlag = ""
        App.prefs.hemisphere = ""
    }
    //

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

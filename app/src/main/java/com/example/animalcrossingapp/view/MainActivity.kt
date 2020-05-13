package com.example.animalcrossingapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.animalcrossingapp.R
import com.example.animalcrossingapp.controller.App
import com.example.animalcrossingapp.controller.MainController
import com.example.animalcrossingapp.vo.FishVO
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //첫 실행 판단 prefs.xml 저장
        val iniFlag = App.prefs.initialFlag
        Toast.makeText(this, "플래그: $iniFlag", Toast.LENGTH_LONG).show()

        if(iniFlag == "1") {
            setContentView(R.layout.activity_main)
        } else {
            val nextIntent = Intent(this, InitialActivity::class.java)
            startActivity(nextIntent)
        }

        textView2.setText(
            MainController.currentTime() + "\n"
            + "現在時間に捕らえる"
        )

        textView3.setText("" + MainController.catchFishList().size + "/" + MainController.currentFishList().size)
        textView4.setText("" + MainController.catchBugList().size + "/" + MainController.currentBugList().size)

    }

    //test
    fun clearXml(view: View) {
        App.prefs.initialFlag = ""
        App.prefs.hemisphere = ""
    }
    //

}

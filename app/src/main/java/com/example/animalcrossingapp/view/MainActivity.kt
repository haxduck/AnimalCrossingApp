package com.example.animalcrossingapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.animalcrossingapp.R
import com.example.animalcrossingapp.controller.App

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val iniFlag = App.prefs.initialFlag
        if (iniFlag == "") {
            Toast.makeText(this, "텍스트가 초기화되었습니다.", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "저장됨: $iniFlag", Toast.LENGTH_LONG).show()
        }

        if(iniFlag == "1") {
            setContentView(R.layout.activity_main)
        } else {
            val nextIntent = Intent(this, InitialActivity::class.java)
            startActivity(nextIntent)
        }

    }

    fun clearXml(view: View) {
        App.prefs.initialFlag = ""
        App.prefs.hemisphere = ""
    }

}

package com.example.animalcrossingapp.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.animalcrossingapp.R
import com.example.animalcrossingapp.controller.MainController
import kotlinx.android.synthetic.main.activity_language.*

class LanguageActivity : AppCompatActivity() {
    private val mLanguageCode = "ko-rKR"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        tv_ani.setText(
            /*MainController.currentFishList(context).toString() + "\n"
            + MainController.currentBugList().toString()*/
            MainController.currentAllList().toString()
        )
    }
}
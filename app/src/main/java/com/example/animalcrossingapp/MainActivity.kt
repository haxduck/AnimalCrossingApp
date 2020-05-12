package com.example.animalcrossingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var flag: Int = 0
        if(flag == 0) {
            setContentView(R.layout.activity_initial)
        } else {
            setContentView(R.layout.activity_main)
        }

    }

}

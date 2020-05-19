package com.example.animalcrossingapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.animalcrossingapp.R
import com.example.animalcrossingapp.controller.ListController
import kotlinx.android.synthetic.main.activity_all_bug_list.*

class AllBugList : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_bug_list)

        var blist = ListController.AllBugList(this)

        if (blist.size == 0) {

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)


        }

        tv2.setText(blist.toString())

    }
}

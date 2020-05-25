package com.example.animalcrossingapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.animalcrossingapp.R
import com.example.animalcrossingapp.controller.ListController
import kotlinx.android.synthetic.main.activity_all_fish_list.*

class AllFishList : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_fish_list)

        var flist= ListController.AllFishList(this)

        if(flist.size==0){

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)


        }



        tx1.setText(flist.toString())




    }


}

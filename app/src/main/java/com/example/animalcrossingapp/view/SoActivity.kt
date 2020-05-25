package com.example.animalcrossingapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.animalcrossingapp.R
import com.example.animalcrossingapp.controller.MainController
import com.example.animalcrossingapp.util.SortingWithName
import kotlinx.android.synthetic.main.activity_so.*

class SoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_so)

        tv_sort.setText( MainController.currentAllList(this).toString())

        upsort.setOnClickListener{
            tv_sort
        }
    }
}

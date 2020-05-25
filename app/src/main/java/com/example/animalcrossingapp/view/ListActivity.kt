package com.example.animalcrossingapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.animalcrossingapp.R
import com.example.animalcrossingapp.controller.AnimalAdapter
import com.example.animalcrossingapp.room.AnimalVO
import kotlinx.android.synthetic.main.activity_realtime_list.*

class ListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_realtime_list)

        val list = intent.getParcelableArrayListExtra<AnimalVO>("list")

        recycler_view.apply {
            layoutManager = LinearLayoutManager(this@ListActivity)
            adapter = AnimalAdapter(list) { animal ->
                Toast.makeText(this@ListActivity, "$animal", Toast.LENGTH_SHORT).show()

            }

        }

    }
}

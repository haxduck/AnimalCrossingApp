package com.example.animalcrossingapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.animalcrossingapp.R
import com.example.animalcrossingapp.controller.AnimalAdapter
import com.example.animalcrossingapp.controller.CurrentAdapter
import com.example.animalcrossingapp.database.AnimalCrossingDB
import com.example.animalcrossingapp.database.Current
import com.example.animalcrossingapp.database.Information
import com.example.animalcrossingapp.room.AnimalVO
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.listviewitem.view.*

class ListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        val view: View = View(this)
        var list = intent.getParcelableArrayListExtra<Current>("list")

        recycler_view.apply {
            layoutManager = LinearLayoutManager(this@ListActivity)
            adapter = CurrentAdapter(list, context, view) { animal ->
            }
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}

package com.example.animalcrossingapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.animalcrossingapp.R
import com.example.animalcrossingapp.controller.AnimalAdapter
import com.example.animalcrossingapp.controller.CurrentAdapter
import com.example.animalcrossingapp.database.AnimalCrossingDB
import com.example.animalcrossingapp.database.Current
import com.example.animalcrossingapp.database.Information
import com.example.animalcrossingapp.room.AnimalVO
import kotlinx.android.synthetic.main.activity_realtime_list.*
import kotlinx.android.synthetic.main.listviewitem.view.*

class ListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_realtime_list)

        val db = AnimalCrossingDB.getInstance(this)!!

//        val list = intent.getParcelableArrayListExtra<AnimalVO>("list")
        var list = intent.getParcelableArrayListExtra<Current>("list")

        var idx = 0
        recycler_view.apply {
            layoutManager = LinearLayoutManager(this@ListActivity)
            adapter = CurrentAdapter(list) { animal ->
                idx++
                Toast.makeText(this@ListActivity, "$animal", Toast.LENGTH_SHORT).show()
                if (animal.flag == "1" && idx % 2 == 1) {
                    val flag = "0"
                    db.animalCrossingDao().updateCatchFlag(animal.information_code!!, flag)
                } else if (animal.flag == "1" && idx % 2 == 0) {
                    val flag = "1"
                    db.animalCrossingDao().updateCatchFlag(animal.information_code!!, flag)
                } else if (animal.flag != "1" && idx % 2 == 1) {
                    val flag = "1"
                    db.animalCrossingDao().updateCatchFlag(animal.information_code!!, flag)
                } else if (animal.flag != "1" && idx % 2 == 0) {
                    val flag = "0"
                    db.animalCrossingDao().updateCatchFlag(animal.information_code!!, flag)
                }
                Log.d("idx", idx.toString())

            }
        }

    }
}

package com.example.animalcrossingapp.view

import AnimalAdapter2
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.animalcrossingapp.R
import com.example.animalcrossingapp.database.AnimalCrossingDB
import com.example.animalcrossingapp.room.AnimalDB
import kotlinx.android.synthetic.main.activity_realtime_list.recycler_view

class AllListActivity2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_list2)

        val db = AnimalCrossingDB.getInstance(this)!!

        val selectall = db.animalCrossingDao().selectCurrentAnimal("北半球", "8", "1月")



        recycler_view.apply {
            layoutManager = LinearLayoutManager(this@AllListActivity2)
            adapter = AnimalAdapter2(selectall) { animal ->
                Toast.makeText(this@AllListActivity2, "$animal", Toast.LENGTH_SHORT).show()

            }

        }
        /*btn_bug.setOnClickListener{

            val nextIntent = Intent(this, AllListActivity::class.java)
            startActivity(nextIntent)
        }*/

       /* btn_bug.setOnClickListener{
            val nextIntent = Intent(this, AllListActivity::class.java)
            startActivity(nextIntent)

        }*/




    }
}

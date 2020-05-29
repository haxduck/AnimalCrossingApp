
package com.example.animalcrossingapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.animalcrossingapp.R
import com.example.animalcrossingapp.database.AnimalCrossingDB
import kotlinx.android.synthetic.main.activity_all_list.*
import kotlinx.android.synthetic.main.activity_main.*

class AllListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_list)

        val pdb = AnimalCrossingDB.getInstance(this)!!

        val realTimeList = pdb.animalCrossingDao().selectCurrentAnimal("北半球", "8", "1月")
        var imgArr = Array(realTimeList.size, {0})
        var idx = 0
        realTimeList.forEach {
            var id = it.information_code
            imgArr[idx] = this.getResources().getIdentifier(id, "drawable", this.getPackageName())
            idx++
        }
        val griviewAdapter = GridviewAdapter(this, imgArr)
        gridView2.adapter = griviewAdapter

    }
}


package com.example.animalcrossingapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.animalcrossingapp.R
import com.example.animalcrossingapp.vo.BugVO
import com.example.animalcrossingapp.vo.FishVO
import kotlinx.android.synthetic.main.activity_realtime_list.*

class RealtimeListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_realtime_list)

        var flist = intent.getSerializableExtra("flist") as ArrayList<FishVO>?
        var blist = intent.getSerializableExtra("blist") as ArrayList<BugVO>?

        listView.setText(flist.toString() + "\n" + blist.toString())
    }
}

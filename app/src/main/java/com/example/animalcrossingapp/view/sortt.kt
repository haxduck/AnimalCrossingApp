package com.example.animalcrossingapp.view

import android.view.View
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.animalcrossingapp.R
import com.example.animalcrossingapp.util.AllVO
import com.example.animalcrossingapp.util.ListViewAdapter
import java.util.*

class sortt : AppCompatActivity() {
    var adapter: ListViewAdapter? = null
    var allList =
        ArrayList<AllVO>()

    init {

        val desc =
            findViewById<View>(R.id.nmdesc) as Button
        desc.setOnClickListener {
            val soDesc =
                Comparator<AllVO> { item1, item2 ->
                    var ret = 0
                    if ((item1.name!![0] as Int) - (item2.name!![0] as Int) > 0 ) ret = 1
                    else if ((item1.name!![0] as Int) - (item2.name!![0] as Int) == 0 )ret = 0
                    else ret = -1
                    ret

                }
            Collections.sort(allList, soDesc)
            adapter!!.notifyDataSetChanged()
        }

        val asc =
            findViewById<View>(R.id.nmasc) as Button
        desc.setOnClickListener {
            val soAsc =
                Comparator<AllVO> { item1, item2 ->
                    var ret = 0
                    if ((item1.name!![0] as Int) - (item2.name!![0] as Int) < 0 ) ret = 1
                    else if ((item1.name!![0] as Int) - (item2.name!![0] as Int) == 0 )ret = 0
                    else ret = -1
                    ret

                }
            Collections.sort(allList, soAsc)
            adapter!!.notifyDataSetChanged()
        }
    }
}
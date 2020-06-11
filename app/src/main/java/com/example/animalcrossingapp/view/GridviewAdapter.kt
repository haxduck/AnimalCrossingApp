package com.example.animalcrossingapp.view

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.animalcrossingapp.R
import com.example.animalcrossingapp.database.AnimalCrossingDB
import com.example.animalcrossingapp.database.Current
import kotlinx.android.synthetic.main.gridviewitem1.view.*
import kotlinx.android.synthetic.main.gridviewitem1.view.gridView_img
import kotlinx.android.synthetic.main.gridviewitem2.view.*
import java.util.*

class GridviewAdapter(val context: Context, val list: List<Current>):BaseAdapter(){
    private var imgArr = arrayOf(Int)
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.gridviewitem1,null)


        var imgArr = Array(list.size, { 0 })
        var idx = 0
        list.forEach {
            var id = it.information_code
            imgArr[idx] =
                context.getResources().getIdentifier(id, "drawable", context.getPackageName())
            idx++
        }
        view.gridView_img.setImageResource(imgArr[p0])

        var db = AnimalCrossingDB.getInstance(context)!!
        val nextMonth = Calendar.getInstance().get(Calendar.MONTH) + 2

        var animal = db.animalCrossingDao().selectCode(list[p0].information_code!!.toUpperCase())
        var marr = animal.month!!.replace("月","").split(",")
        for (i in (0..marr.size - 1)) {
            if (marr[i] == nextMonth.toString()) {
                view.gridView_img.setBackgroundColor(Color.WHITE)
                break
            }
            else {
                view.gridView_img.setBackgroundColor(Color.RED)
            }
        }

        return view
    }

    override fun getItem(position: Int): Any {
        return 0
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return list.size
    }

}
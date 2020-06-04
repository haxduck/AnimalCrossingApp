package com.example.animalcrossingapp.view

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.animalcrossingapp.R
import com.example.animalcrossingapp.database.Current
import kotlinx.android.synthetic.main.fragment_tab_layout_all_list.view.*
import kotlinx.android.synthetic.main.gridviewitem1.view.*

class ClickableGridviewAdapter(val context: Context, val list: ArrayList<Current>):BaseAdapter(){

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.gridviewitem2,null)
        var img_list = Array(list.size, {0})
        var idx = 0
        list.forEach {
            var id = it.information_code
            img_list[idx] = context.getResources().getIdentifier(id, "drawable", context.getPackageName())
            idx++
        }
        view.gridView_img.setImageResource(img_list[p0])
        if (list[p0].flag == "1" ) view.gridView_img.setBackgroundColor(Color.parseColor("#B9E9DB"))
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
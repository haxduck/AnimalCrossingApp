package com.example.animalcrossingapp.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.animalcrossingapp.R
import kotlinx.android.synthetic.main.gridviewitem1.view.*

class GridviewAdapter(val context: Context, val img_list : Array<Int>):BaseAdapter(){
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.gridviewitem1,null)

        view.gridView_img.setImageResource(img_list[p0])


        return view
    }

    override fun getItem(position: Int): Any {
        return 0
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return img_list.size
    }

}
package com.example.animalcrossingapp.util

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.animalcrossingapp.R
import java.util.*

class ListViewAdapter(itemList: ArrayList<AllVO>?) :
    BaseAdapter() {

    private var allList: ArrayList<AllVO>? = null


    override fun getCount(): Int {
        return allList!!.size
    }


    override fun getView(
        position: Int,
        convertView: View,
        parent: ViewGroup
    ): View {
        var convertView = convertView
        val pos = position
        val context = parent.context


        if (convertView == null) {
            val inflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.activity_sort, parent, false)
        }


        val all = allList!![position]


         
        return convertView
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItem(position: Int): Any {
        return allList!![position]
    }

    init {
        allList = itemList ?: ArrayList()
    }
}
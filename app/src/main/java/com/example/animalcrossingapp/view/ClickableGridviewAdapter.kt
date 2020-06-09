package com.example.animalcrossingapp.view

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.animalcrossingapp.R
import com.example.animalcrossingapp.controller.CurrentAdapter
import com.example.animalcrossingapp.database.AnimalCrossingDB
import com.example.animalcrossingapp.database.Current
import kotlinx.android.synthetic.main.fragment_tab_layout_all_list.view.*
import kotlinx.android.synthetic.main.gridviewitem1.view.gridView_img
import kotlinx.android.synthetic.main.gridviewitem2.view.*

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
        //시작시 잡은거 안잡은거 체크
        if (list[p0].flag == "1" ) view.grid_wrap2.setBackgroundResource(R.drawable.grid_wrap2_r)

        //클릭 함수
        var db = AnimalCrossingDB.getInstance(context)!!
        var flag = list[p0].flag
        var info = list[p0].information_code!!
        view.gridView_img.setOnClickListener {
            if (flag == "1") {
                db.animalCrossingDao().updateCatchFlag(info , "0")
//                view.gridView_img.setBackgroundColor(Color.WHITE)
                view.grid_wrap2.setBackgroundResource(R.drawable.grid_wrap2)
                flag = "0"
            } else {
                db.animalCrossingDao().updateCatchFlag(info , "1")
                view.grid_wrap2.setBackgroundResource(R.drawable.grid_wrap2_r)
                flag = "1"
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
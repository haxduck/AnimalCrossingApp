package com.example.animalcrossingapp.view

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.animalcrossingapp.R
import com.example.animalcrossingapp.controller.CurrentAdapter
import com.example.animalcrossingapp.database.AnimalCrossingDB
import com.example.animalcrossingapp.database.Current
import com.example.animalcrossingapp.databinding.ItemAnimalBinding.inflate
import kotlinx.android.synthetic.main.fragment_first.view.*
import kotlinx.android.synthetic.main.fragment_tab_layout_all_list.view.*
import kotlinx.android.synthetic.main.gridviewitem1.view.*
import kotlinx.android.synthetic.main.gridviewitem1.view.gridView_img
import kotlinx.android.synthetic.main.gridviewitem2.view.*
import java.util.*
import kotlin.collections.ArrayList

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

        var db = AnimalCrossingDB.getInstance(context)!!
        //시작시 잡은거 안잡은거 체크
        if (list[p0].flag == "1" ) view.grid_wrap2.setBackgroundResource(R.drawable.grid_wrap2_r)
        else view.grid_wrap2.setBackgroundResource(R.drawable.grid_wrap3)

        var nextMonth = Calendar.getInstance().get(Calendar.MONTH) + 2
        if (nextMonth > 11) nextMonth = nextMonth - 11

        var animal = db.animalCrossingDao().selectCode(list[p0].information_code!!.toUpperCase())
        var marr = animal.month!!.replace("月","").split(",")
        for (i in (0..marr.size - 1)) {
            if (marr[i] == nextMonth.toString()) {
                view.grid_wrap2.setBackgroundResource(R.drawable.grid_wrap3)
                break
            }
            else {
                view.grid_wrap2.setBackgroundResource(R.drawable.grid_wrap3_uc)
            }
        }

        //클릭 함수
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
        view.gridView_img.setOnLongClickListener {
            Toast.makeText(context,"dfsdf", Toast.LENGTH_LONG).show()

            /*var popupView = LayoutInflater.from(context).inflate(R.layout.popup_window, null)
            var popupWindow = popupView
            popupWindow.*/


            true
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
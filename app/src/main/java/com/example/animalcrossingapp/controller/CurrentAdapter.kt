package com.example.animalcrossingapp.controller

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.animalcrossingapp.R
import com.example.animalcrossingapp.database.AnimalCrossingDB
import com.example.animalcrossingapp.database.Current
import com.example.animalcrossingapp.databinding.ItemCurrentBinding
import com.example.animalcrossingapp.databinding.ListviewitemBinding
import kotlinx.android.synthetic.main.fragment_tab_layout_fish_list.view.*
import kotlinx.android.synthetic.main.gridviewitem1.view.*
import kotlinx.android.synthetic.main.listviewitem.view.*
import kotlinx.android.synthetic.main.listviewitem.view.grid_wrap

class CurrentAdapter(
    val items: ArrayList<Current>,
    val context: Context,
    val view: View,
    private val clickListener: (current: Current) -> Unit
) :
    RecyclerView.Adapter<CurrentAdapter.CurrentViewHolder>() {
    class CurrentViewHolder(val binding: ListviewitemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.listviewitem, parent, false)
        val viewHolder = CurrentViewHolder(ListviewitemBinding.bind(view))
        view.setOnClickListener {
            clickListener.invoke(items[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun getItemId(position: Int): Long {
        return items.get(position).hashCode().toLong()
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: CurrentViewHolder, position: Int) {

        var hourStr: String
        var monthStr: String
        var nextMonth: String = ""
        var nextDay = ""
        when (App.prefs.language) {
            "ja" -> {
                hourStr = "時"
                monthStr = "月"
            }
            "ko" -> {
                hourStr = "시"
                monthStr = "월"
            }
            else -> {
                hourStr = "時"
                monthStr = "月"
            }
        }

        //시간 출력
        var tarr = items[position].time!!.split(",").map{it.toInt()}.toCollection(ArrayList())
        tarr.sortWith(compareBy({it}))
        var str = ""
        //내일 포함
        if (tarr.get(0) == 1 && tarr.get(tarr.size - 1) == 24 && tarr.size < 24) {
            var i = 0
            while (tarr.get(i+1) - tarr.get(i) == 1) {
                tarr.add(tarr.get(0))
                tarr.remove(tarr.get(0))
            }
            tarr.add(tarr.get(0))
            tarr.remove(tarr.get(0))
            when(App.prefs.language) {
                "ko" -> nextDay = "내일 "
                "ja" -> nextDay = "翌日 "
            }
        }
        //기간 나누기
        var oneFlag = false
        var twoFlag = false
        for ( i in 0..tarr.size - 1 ) {
            var flag = false
            if ( i == 0 && tarr.size > 1) {
                if (tarr.get(1) - tarr.get(0) > 1) {
                    twoFlag = true
                } else {
                    str += tarr.get(0)
                }
            }
            if ( i > 0 && tarr.get(i) - tarr.get(i - 1) > 1 ) {
                if (oneFlag == false) {
                    str += " ~ ${tarr.get(i-1)}${hourStr}, ${tarr.get(i)}"
                    if (i == tarr.size - 1) str += hourStr
                } else {
                    if (i == tarr.size - 1) {
                        str += "${hourStr}, ${tarr.get(i)}${hourStr}"
                    } else {
//                        str += ", ${tarr.get(i)}${hourStr}"
                        if (tarr[i-1] - tarr[i-2] > 1 && tarr[i] - tarr[i-1] > 1) {
                            str += "${hourStr}, ${tarr.get(i)}"
                        } else {
                            str += ", ${tarr.get(i)}"
                        }
                    }
                    oneFlag = false
                }
                if (i < tarr.size - 1 && tarr.get(i) - tarr.get(i-1) > 1 && tarr.get(i+1) - tarr.get(i) > 1) {
                    oneFlag = true
                }
                if ( i == tarr.size -1 && tarr.size > 1 ) flag = true
            }
            if ( i == tarr.size -1 && tarr.size > 1 && flag == false ) {
                str += " ~ ${nextDay}${tarr.get(i)}${hourStr}"
            }
            if (tarr.size == 1) str += "${tarr.get(0)}${hourStr}"
        }
        if (twoFlag == true) str = str.replaceFirst("~", "")
        if (tarr.size == 24) {
            str = str.replaceFirst("1", "0")
        }
        //월 출력
        var marr = items[position].month!!.replace("月", "").split(",").map{it.toInt()}.toCollection(ArrayList())
        marr.sortWith(compareBy({it}))
        var str1 = ""
        //내년 포함
        if (marr.get(0) == 1 && marr.get(marr.size - 1) == 12 && marr.size < 12) {
            var i = 0
            while (marr.get(i+1) - marr.get(i) == 1) {
                marr.add(marr.get(0))
                marr.remove(marr.get(0))
            }
            marr.add(marr.get(0))
            marr.remove(marr.get(0))
            when(App.prefs.language) {
                "ko" -> nextMonth = "내년 "
                "ja" -> nextMonth = "来年 "
            }
        }
        // 월 출력
        //기간 나누기
        var oneFlag1 = false
        var twoFlag1 = false
        for ( i in 0..marr.size - 1 ) {
            var flag = false
            if ( i == 0 && marr.size > 1 ) {
                if (marr.get(1) - marr.get(0) > 1) {
                    twoFlag1 = true
                } else {
                    str1 += marr.get(0)
                }
            }
            if ( i > 0 && marr.get(i) - marr.get(i - 1) > 1 ) {
                if (oneFlag1 == false) {
                    str1 += " ~ ${marr.get(i-1)}${monthStr}, ${marr.get(i)}"
                    if (i == marr.size - 1) str1 += monthStr
                } else {
                    if (i == marr.size - 1) {
                        str1 += "${monthStr}, ${marr.get(i)}${monthStr}"
                    } else {
//                        str1 += ", ${marr.get(i)}"
                        if (marr[i-1] - marr[i-2] > 1 && marr[i] - marr[i-1] > 1) {
                            str1 += "${monthStr}, ${marr[i]}"
                        } else {
                            str1 += ", ${marr[i]}"
                        }
                    }
                    oneFlag1 = false
                }
                if (i < marr.size - 1 && marr.get(i) - marr.get(i-1) > 1 && marr.get(i+1) - marr.get(i) > 1) {
                    oneFlag1 = true
                }
                if ( i == marr.size -1 && marr.size > 1 ) flag = true
            }
            if ( i == marr.size -1 && marr.size > 1 && flag == false ) {
                str1 += " ~ ${nextMonth}${marr.get(i)}${monthStr}"
            }
            if (marr.size == 1) str1 += "${marr.get(0)}${monthStr}"
        }
        if ( twoFlag1 == true ) str1 = str1.replaceFirst("~", "")
        //
        
        holder.binding.time = str
        holder.binding.month = str1
        holder.binding.current = items[position]
        holder.binding.lang = App.prefs.language
        val db = AnimalCrossingDB.getInstance(holder.itemView.context)!!
        var flag = items[position].flag
        holder.itemView.setOnClickListener {
            if (flag == "1") {
                db.animalCrossingDao().updateCatchFlag(items[position].information_code!!, "0")
                holder.itemView.grid_wrap.background =
                    holder.itemView.resources.getDrawable(R.drawable.list_wrap)
                holder.itemView.view_img.background =
                    holder.itemView.resources.getDrawable(R.drawable.grid_wrap)
                flag = "0"
            } else {
                db.animalCrossingDao().updateCatchFlag(items[position].information_code!!, "1")
                holder.itemView.grid_wrap.background =
                    holder.itemView.resources.getDrawable(R.drawable.list_wrap_c)
                holder.itemView.view_img.background =
                    holder.itemView.resources.getDrawable(R.drawable.grid_wrap_r)
                flag = "1"
            }

        }

    }
}
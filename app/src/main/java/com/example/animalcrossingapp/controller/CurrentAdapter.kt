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
        val dao = AnimalCrossingDB.getInstance(context)?.animalCrossingDao()!!
        var tarr = items[position].time!!.split(",")
        var str = ""
        var flist: ArrayList<Int> = arrayListOf(tarr[0].toInt())
        var slist: ArrayList<Int> = arrayListOf()
        var f = 0
        for (i in 1..tarr.size - 1) {
            if (tarr[i].toInt() - tarr[i - 1].toInt() == 1 && f == 0) {
                flist.add(tarr[i].toInt())
            } else {
                f = 1
                slist.add(tarr[i].toInt())
            }
        }
        var hourStr: String
        var monthStr: String
        var mirai: String
        when (App.prefs.language) {
            "ja" -> {
                hourStr = "時"
                monthStr = "月"
                mirai = " ~ 来年"
            }
            "ko" -> {
                hourStr = "시"
                monthStr = "월"
                mirai = " ~ 내년"
            }
            else -> {
                hourStr = "時"
                monthStr = "月"
                mirai = " ~ 来年"
            }
        }
        if (slist.size == 0 && flist.size != 1) {
            str = flist[0].toString() + " ~ " + flist[flist.size - 1].toString() + hourStr
        } else if (slist.size != 0) {
            str = flist[0].toString() + " ~ " + flist[flist.size - 1].toString() +
                    hourStr + ", " + slist[0].toString() + " ~ " + slist[slist.size - 1].toString() + hourStr
        } else {
            str = flist[0].toString() + hourStr
        }
        //
        var marr = items[position].month!!.replace("月", "").split(",")
        var str1 = ""
        var flist1: ArrayList<Int> = arrayListOf(marr[0].toInt())
        var slist1: ArrayList<Int> = arrayListOf()
        var fl = 0
        for (i in 1..marr.size - 1) {
            if (marr[i].toInt() - marr[i - 1].toInt() == 1 && fl == 0) {
                flist1.add(marr[i].toInt())
            } else {
                fl = 1
                slist1.add(marr[i].toInt())
            }
        }

        if (slist1.size == 0 && flist1.size != 1) {
            str1 = flist1[0].toString() + " ~ " + flist1[flist1.size - 1].toString() + monthStr
        } else if (slist1.size != 0) {
            if (flist1[0] == 1 && slist1[slist1.size - 1] == 12 && marr.size < 12) {
                str1 = slist1[0].toString() + mirai + flist1[flist1.size - 1] + monthStr
            } else {
                str1 = flist1[0].toString() + " ~ " + flist1[flist1.size - 1].toString() +
                        monthStr + ", " + slist1[0].toString() + " ~ " + slist1[slist1.size - 1].toString() + monthStr
            }
        } else {
            str1 = flist1[0].toString() + monthStr
        }
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
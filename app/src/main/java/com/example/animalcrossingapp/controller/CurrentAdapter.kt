package com.example.animalcrossingapp.controller

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.animalcrossingapp.R
import com.example.animalcrossingapp.database.AnimalCrossingDB
import com.example.animalcrossingapp.database.Current
import com.example.animalcrossingapp.databinding.ItemCurrentBinding
import com.example.animalcrossingapp.databinding.ListviewitemBinding
import kotlinx.android.synthetic.main.listviewitem.view.*

class CurrentAdapter(val items: ArrayList<Current>,
                     private val clickListener: (current: Current) -> Unit) :
    RecyclerView.Adapter<CurrentAdapter.CurrentViewHolder>() {
    class CurrentViewHolder(val binding: ListviewitemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.listviewitem, parent, false)
        val viewHolder = CurrentViewHolder(ListviewitemBinding.bind(view))
        view.setOnClickListener {
            clickListener.invoke(items[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun getItemCount(): Int = items.size


    override fun onBindViewHolder(holder: CurrentViewHolder, position: Int) {
        holder.binding.current = items[position]
        val db = AnimalCrossingDB.getInstance(holder.itemView.context)!!
        var flag = items[position].flag
//        if (flag == "1") holder.itemView.view_img.setBackgroundColor(Color.parseColor("#B9E9DB"))
        holder.itemView.setOnClickListener {
            if (flag == "1") {
                db.animalCrossingDao().updateCatchFlag(items[position].information_code!!, "0")
                holder.itemView.grid_wrap.background = holder.itemView.resources.getDrawable(R.drawable.list_wrap)
//                holder.itemView.view_img.setBackgroundColor(Color.parseColor("#FFFFFF"))
                flag = "0"
            } else {
                db.animalCrossingDao().updateCatchFlag(items[position].information_code!!, "1")
                holder.itemView.grid_wrap.background = holder.itemView.resources.getDrawable(R.drawable.list_wrap_c)
//                holder.itemView.view_img.setBackgroundColor(Color.parseColor("#B9E9DB"))
                flag = "1"
            }
        }

    }
}
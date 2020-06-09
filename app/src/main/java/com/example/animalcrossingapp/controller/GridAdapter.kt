package com.example.animalcrossingapp.controller

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.animalcrossingapp.R
import com.example.animalcrossingapp.database.AnimalCrossingDB
import com.example.animalcrossingapp.database.Current
import com.example.animalcrossingapp.databinding.ItemGridBinding
import kotlinx.android.synthetic.main.gridviewitem1.view.*
import kotlinx.android.synthetic.main.item_grid.view.*
import kotlinx.android.synthetic.main.listviewitem.view.*
import kotlinx.android.synthetic.main.listviewitem.view.grid_wrap

class GridAdapter(val items: ArrayList<Current>,
                  val context: Context,
                  private val clickListener: (current: Current) -> Unit) :
    RecyclerView.Adapter<GridAdapter.GridViewHolder>() {
    class GridViewHolder(val binding: ItemGridBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_grid, parent, false)
        val viewHolder = GridViewHolder(ItemGridBinding.bind(view))
        view.setOnClickListener {
            clickListener.invoke(items[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: GridViewHolder, position: Int) {
        holder.binding.grid = items[position]
        val db = AnimalCrossingDB.getInstance(holder.itemView.context)!!
        var flag = items[position].flag
        holder.itemView.setOnClickListener {
            if (flag == "1") {
                db.animalCrossingDao().updateCatchFlag(items[position].information_code!!, "0")
                holder.itemView.grid_img.background = holder.itemView.resources.getDrawable(R.drawable.grid_wrap)
                flag = "0"
            } else {
                db.animalCrossingDao().updateCatchFlag(items[position].information_code!!, "1")
                holder.itemView.grid_img.background = holder.itemView.resources.getDrawable(R.drawable.grid_wrap_r)
                flag = "1"
            }

        }
    }
}
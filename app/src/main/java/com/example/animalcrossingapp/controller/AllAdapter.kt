package com.example.animalcrossingapp.controller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.animalcrossingapp.R
import com.example.animalcrossingapp.databinding.ItemAllBinding
import com.example.animalcrossingapp.vo.AllVO

class AllAdapter(val items: ArrayList<AllVO>,
                 private val clickListener: (all: AllVO) -> Unit) :
    RecyclerView.Adapter<AllAdapter.AllViewHolder>() {
    class AllViewHolder(val binding: ItemAllBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_all, parent, false)
        val viewHolder = AllViewHolder(ItemAllBinding.bind(view))
        view.setOnClickListener {
            clickListener.invoke(items[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun getItemCount(): Int = items.size


    override fun onBindViewHolder(holder: AllViewHolder, position: Int) {
        holder.binding.all = items[position]
    }
}
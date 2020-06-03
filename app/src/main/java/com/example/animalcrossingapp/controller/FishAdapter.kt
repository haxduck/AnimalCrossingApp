package com.example.animalcrossingapp.controller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.animalcrossingapp.R
import com.example.animalcrossingapp.databinding.ItemFishBinding
import com.example.animalcrossingapp.vo.FishVO

class FishAdapter(val items: ArrayList<FishVO>,
                  private val clickListener: (fish: FishVO) -> Unit) :
    RecyclerView.Adapter<FishAdapter.FishViewHolder>() {
    class FishViewHolder(val binding: ItemFishBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FishViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_fish, parent, false)
        val viewHolder = FishViewHolder(ItemFishBinding.bind(view))
        view.setOnClickListener {
            clickListener.invoke(items[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun getItemCount(): Int = items.size


    override fun onBindViewHolder(holder: FishViewHolder, position: Int) {
        holder.binding.fish = items[position]
    }
}
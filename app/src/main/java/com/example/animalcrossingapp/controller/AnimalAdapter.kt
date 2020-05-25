package com.example.animalcrossingapp.controller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.animalcrossingapp.R
import com.example.animalcrossingapp.databinding.ItemAnimalBinding
import com.example.animalcrossingapp.room.AnimalVO

class AnimalAdapter(val items: List<AnimalVO>,
                    private val clickListener: (animal: AnimalVO) -> Unit) :
    RecyclerView.Adapter<AnimalAdapter.AnimalViewHolder>() {
    class AnimalViewHolder(val binding: ItemAnimalBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_animal, parent, false)
        val viewHolder = AnimalViewHolder(ItemAnimalBinding.bind(view))
        view.setOnClickListener {
            clickListener.invoke(items[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun getItemCount(): Int = items.size


    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
        holder.binding.animal = items[position]
    }
}
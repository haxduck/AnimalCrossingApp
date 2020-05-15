package com.example.animalcrossingapp.controller

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.animalcrossingapp.R
import com.example.animalcrossingapp.databinding.ItemBugBinding
import com.example.animalcrossingapp.databinding.ItemFishBinding
import com.example.animalcrossingapp.vo.BugVO
import com.example.animalcrossingapp.vo.FishVO
import kotlinx.android.synthetic.main.activity_initial.view.*
import kotlinx.android.synthetic.main.item_bug.view.*

class BugAdapter(val items: ArrayList<BugVO>,
                 private val clickListener: (bug: BugVO) -> Unit) :
    RecyclerView.Adapter<BugAdapter.BugViewHolder>() {
    class BugViewHolder(val binding: ItemBugBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BugViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_bug, parent, false)
        val viewHolder = BugViewHolder(ItemBugBinding.bind(view))
        view.setOnClickListener {
            clickListener.invoke(items[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun getItemCount(): Int = items.size


    override fun onBindViewHolder(holder: BugViewHolder, position: Int) {
        holder.binding.bug = items[position]
    }
}
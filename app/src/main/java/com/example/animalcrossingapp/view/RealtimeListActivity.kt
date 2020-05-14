package com.example.animalcrossingapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.animalcrossingapp.R
import com.example.animalcrossingapp.controller.FishAdapter
import com.example.animalcrossingapp.controller.MainController
import com.example.animalcrossingapp.dao.FishDBHelper
import com.example.animalcrossingapp.databinding.ItemFishBinding
import com.example.animalcrossingapp.vo.BugVO
import com.example.animalcrossingapp.vo.FishVO
import kotlinx.android.synthetic.main.activity_realtime_list.*

class RealtimeListActivity : AppCompatActivity() {

    lateinit var fishDBHelper: FishDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_realtime_list)

        fishDBHelper = FishDBHelper(this)

        //var flist = intent.getSerializableExtra("flist") as ArrayList<FishVO>
        //var blist = intent.getSerializableExtra("blist") as ArrayList<BugVO>
        var flist = MainController.currentFishList(this)

        //listView.setText(flist.toString() + blist.toString())

        recycler_view.apply {
            layoutManager = LinearLayoutManager(this@RealtimeListActivity)
            adapter = FishAdapter(flist) { fish ->
                Toast.makeText(this@RealtimeListActivity, "$fish", Toast.LENGTH_SHORT).show()
                if(fish.catch_flag == ""){
                    fishDBHelper.updateFish(FishVO(fish.name_japan, fish.price, "c"))
                } else {
                    fishDBHelper.updateFish(FishVO(fish.name_japan, fish.price, ""))
                }
            }
        }

    }

}

/*class FishAdapter(val items: ArrayList<FishVO>,
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
}*/

package com.example.animalcrossingapp.toolbar

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.animalcrossingapp.R
import com.example.animalcrossingapp.controller.CurrentAdapter
import com.example.animalcrossingapp.database.AnimalCrossingDB
import com.example.animalcrossingapp.database.Current
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.fragment_second.view.*

/**
 * A simple [Fragment] subclass.
 */
class SecondFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_second, container, false)
        val intent = Intent (activity, SecondFragment::class.java)
        val context: Context = requireContext()
        val db = AnimalCrossingDB.getInstance(context)!!
        var list = arrayListOf<Current>()

        view.search_wrap.setOnClickListener{
            view.test2.setVisibility(if (view.test2.isShown()) View.GONE else View.VISIBLE)
        }

        try{
            list.addAll(arguments?.getParcelableArrayList("list")!!)
        }catch(e: KotlinNullPointerException){
            var clist = db.animalCrossingDao().selectAll()
            clist.forEach {
                list.add(it)
            }
        }
        /*if (list.addAll(arguments?.getParcelableArrayList("list")!!) == null ){
            var clist = db.animalCrossingDao().selectAll()
            clist.forEach {
                list.add(it)
            }
        } else {
            list.addAll(arguments?.getParcelableArrayList("list")!!)
        }*/

        view.test2.setVisibility(View.GONE)

        view.price_rangeSeekbar.setRangeColor(Color.BLACK)
        view.time_rangeSeekbar.setRangeColor(Color.BLACK)

        view.recycler_view.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = CurrentAdapter(list) { animal ->
            }
        }

        return view
    }


    /*companion object {
        val TAG = SecondFragment::class.java.simpleName
        @JvmStatic
        fun newInstance() = SecondFragment()
    }*/


}

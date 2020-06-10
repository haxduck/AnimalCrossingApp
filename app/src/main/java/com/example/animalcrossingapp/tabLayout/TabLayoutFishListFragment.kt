package com.example.animalcrossingapp.tabLayout

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.example.animalcrossingapp.R
import com.example.animalcrossingapp.controller.App
import com.example.animalcrossingapp.controller.CurrentAdapter
import com.example.animalcrossingapp.database.AnimalCrossingDB
import com.example.animalcrossingapp.database.Current
import com.example.animalcrossingapp.livedata.AnimalViewModel
import com.example.animalcrossingapp.view.ClickableGridviewAdapter
import kotlinx.android.synthetic.main.fragment_tab_layout_fish_list.*
import kotlinx.android.synthetic.main.fragment_tab_layout_fish_list.view.*
import kotlinx.android.synthetic.main.fragment_tab_layout_fish_list.view.m4
import kotlinx.android.synthetic.main.fragment_tab_layout_fish_list.view.tabLayoutFishList
import kotlinx.android.synthetic.main.fragment_tab_layout_fish_list.view.toggleButton3
import kotlinx.android.synthetic.main.fragment_tab_layout_insect_list.view.*
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TabLayoutFishListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TabLayoutFishListFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_tab_layout_fish_list, container, false)
        val context: Context = requireContext()
        val db = AnimalCrossingDB.getInstance(context)!!
        val dbList = arrayListOf<Current>()
        val clist = db.animalCrossingDao().selectTablayoutAllFish()
        val list = arguments?.getParcelableArrayList<Current>("flist")!!
        if (list.size == 0) clist.forEach { dbList.add(it) }
        else {
            dbList.addAll(list)
        }
        //라이브
        val selector = arguments?.getString("selector")
        val keyword = arguments?.getString("keyword")!!
        val hemishpere = App.prefs.hemisphere!!
        val currentTime: String = Calendar.getInstance().get(Calendar.HOUR_OF_DAY).toString()
        val thisMonth = Calendar.getInstance().get(Calendar.MONTH) + 1
        val currentMonth = "" + thisMonth + "月"
        var liveList: LiveData<List<Current>>

        when (selector) {
            "current" -> liveList = db.animalCrossingDao()
                .selectLiveCurrentAnimal(hemishpere, currentTime, currentMonth)
            "arrange" -> liveList = db.animalCrossingDao().selectLiveArrange(hemishpere)
            "search" -> liveList = db.animalCrossingDao().selectLiveSearch(keyword)
            else -> liveList = db.animalCrossingDao().selectAll()
        }
//        val realTimeList = db.animalCrossingDao().selectCurrentAnimal(hemishpere, currentTime, currentMonth)
//        var liveList = db.animalCrossingDao().selectAll()

        view.tabLayoutFishList.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = CurrentAdapter(dbList, context, view){ animal -> }
        }
        val griviewAdapter = ClickableGridviewAdapter(requireContext(), dbList)
        view.gridView5.adapter = griviewAdapter

        val mainObserver = Observer<List<Current>> { animals ->
            dbList.clear()
            animals.forEach {
                if (it.sortation == "魚") dbList.add(it)
            }
            if (view.toggleButton3.isChecked) {
            view.tabLayoutFishList.apply {
                layoutManager = LinearLayoutManager(activity)
                adapter = CurrentAdapter(dbList, context, view) { animal -> }
            }
            } else {
            val griviewAdapter = ClickableGridviewAdapter(requireContext(), dbList)
            view.gridView5.adapter = griviewAdapter
            }
        }
        liveList.observe(viewLifecycleOwner, mainObserver)
        //


        /*var imgArr = Array(dbList.size, {0})
        var idx = 0
        dbList.forEach {
            var id = it.information_code
            imgArr[idx] = this.getResources().getIdentifier(id, "drawable", requireContext().getPackageName())
            idx++
        }*/

        /*val griviewAdapter = GridviewAdapter2(requireContext(), imgArr)
        view.gridView4.adapter = griviewAdapter*/


        view.m4.setVisibility(View.GONE)

        view.toggleButton3.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                toggleButton3.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_list_2))
                tabLayoutFishList.setVisibility(View.GONE)
                m4.setVisibility(View.VISIBLE)
            } else {
                toggleButton3.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_grid_2))
                tabLayoutFishList.setVisibility(View.VISIBLE)
                m4.setVisibility(View.GONE)
            }
        }

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TabLayoutFishListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TabLayoutFishListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
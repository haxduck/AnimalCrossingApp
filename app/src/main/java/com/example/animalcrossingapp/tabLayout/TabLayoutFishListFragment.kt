package com.example.animalcrossingapp.tabLayout

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.example.animalcrossingapp.R
import com.example.animalcrossingapp.controller.App
import com.example.animalcrossingapp.controller.CurrentAdapter
import com.example.animalcrossingapp.controller.GridAdapter
import com.example.animalcrossingapp.database.AnimalCrossingDB
import com.example.animalcrossingapp.database.Current
import com.example.animalcrossingapp.model.AnimalViewModel
import com.example.animalcrossingapp.toolbar.ErrorFragment
import com.example.animalcrossingapp.toolbar.SecondFragment
import com.example.animalcrossingapp.view.ClickableGridviewAdapter
import com.example.animalcrossingapp.view.MainActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_tab_layout_fish_list.*
import kotlinx.android.synthetic.main.fragment_tab_layout_fish_list.view.*
import kotlinx.android.synthetic.main.fragment_tab_layout_fish_list.view.m4
import kotlinx.android.synthetic.main.fragment_tab_layout_fish_list.view.tabLayoutFishList
import kotlinx.android.synthetic.main.fragment_tab_layout_fish_list.view.toggleButton3
import kotlinx.android.synthetic.main.fragment_tab_layout_insect_list.view.*
import java.util.*
import kotlin.collections.HashMap

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_tab_layout_fish_list, container, false)
        val context: Context = requireContext()
        val db = AnimalCrossingDB.getInstance(context)!!
        val dbList = arrayListOf<Current>()
        /*val clist = db.animalCrossingDao().selectTablayoutAllFish()
        val list = arguments?.getParcelableArrayList<Current>("flist")!!
        if (list.size == 0) clist.forEach { dbList.add(it) }
        else {
            dbList.addAll(list)
        }*/
        //라이브
        val selector = arguments?.getString("selector")
        val keyword = arguments?.getString("keyword")!!
        val searchMap = arguments?.getSerializable("searchMap") as HashMap<String, Any>
        val hemishpere = App.prefs.hemisphere!!
        val currentTime: String = Calendar.getInstance().get(Calendar.HOUR_OF_DAY).toString()
        val thisMonth = Calendar.getInstance().get(Calendar.MONTH) + 1
        val currentMonth = "" + thisMonth + "月"
        var liveList: LiveData<List<Current>>
        val model: AnimalViewModel = ViewModelProviders.of(this).get(AnimalViewModel::class.java)
        when (selector) {
            "current" -> liveList = model.currentAnimals
            "arrange" -> liveList = model.arrangeAnimals
            "search" -> {
                liveList = db.animalCrossingDao().selectLiveSearch(keyword)
                liveList.observe(viewLifecycleOwner, Observer { animals ->
                    if (animals.size == 0) {
                        val bundle: Bundle = bundleOf()
                        bundle.putString("ErrorCode", "0")
                        val frg = ErrorFragment()
                        frg.arguments = bundle
                        (activity as MainActivity).supportFragmentManager.beginTransaction()
                            .replace(R.id.main_fragment, frg).addToBackStack(null).commit()
                        (activity as MainActivity).bottomBar.setActiveItem(1)
                    }
                    else liveList = db.animalCrossingDao().selectLiveSearch(keyword)
                })

            }
            "detail" -> {
                liveList = model.getDetail(searchMap)
            }
            else -> liveList = model.animals
        }
//        val realTimeList = db.animalCrossingDao().selectCurrentAnimal(hemishpere, currentTime, currentMonth)
//        var liveList = db.animalCrossingDao().selectAll()

        view.tabLayoutFishList.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = CurrentAdapter(dbList, context, view){ animal -> }
        }
        /*val griviewAdapter = ClickableGridviewAdapter(requireContext(), dbList)
        view.gridView5.adapter = griviewAdapter*/
        view.m4.apply {
            layoutManager = GridLayoutManager(context, 5, GridLayoutManager.HORIZONTAL, false)
            adapter = GridAdapter(dbList, context) { animal -> }
        }

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
            /*val griviewAdapter = ClickableGridviewAdapter(requireContext(), dbList)
            view.gridView5.adapter = griviewAdapter*/
                view.m4.apply {
                    layoutManager = GridLayoutManager(context, 5, GridLayoutManager.HORIZONTAL, false)
                    adapter = GridAdapter(dbList, context) { animal -> }
                }
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

}
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
import kotlinx.android.synthetic.main.fragment_first.view.*
import kotlinx.android.synthetic.main.fragment_tab_layout_fish_list.*
import kotlinx.android.synthetic.main.fragment_tab_layout_fish_list.view.*
import kotlinx.android.synthetic.main.fragment_tab_layout_fish_list.view.m4
import kotlinx.android.synthetic.main.fragment_tab_layout_fish_list.view.tabLayoutFishList
import kotlinx.android.synthetic.main.fragment_tab_layout_fish_list.view.toggleButton3
import kotlinx.android.synthetic.main.fragment_tab_layout_insect_list.view.*
import java.util.*
import kotlin.collections.HashMap

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

        if (App.prefs.language == "ko") {
            view.textViewF11.text = "이름"
            view.textViewF12.text = "가격"
        }

        //라이브
        val selector = arguments?.getString("selector")
        val keyword = arguments?.getString("keyword")!!
        val searchMap = arguments?.getSerializable("searchMap") as HashMap<String, Any>
        val hemishpere = App.prefs.hemisphere!!
        val currentTime: String = Calendar.getInstance().get(Calendar.HOUR_OF_DAY).toString()
        val thisMonth = Calendar.getInstance().get(Calendar.MONTH) + 1
        val currentMonth = "" + thisMonth + "月"
        var liveList: LiveData<List<Current>>
        val model: AnimalViewModel = ViewModelProvider(this).get(AnimalViewModel::class.java)
        when (selector) {
            "current" -> liveList = model.currentAnimals
            "arrange" -> {
                liveList = model.arrangeAnimals
                view.sortBtn.visibility = View.GONE
            }
            "search" -> liveList = model.getSearch(keyword)
            "detail" -> liveList = model.getDetail(searchMap)
            else -> liveList = model.animals
        }

        view.tabLayoutFishList.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = CurrentAdapter(dbList, context, view) { animal -> }
        }
        /*val griviewAdapter = ClickableGridviewAdapter(requireContext(), dbList)
        view.gridView5.adapter = griviewAdapter*/
        view.m4.apply {
            layoutManager = GridLayoutManager(context, 5, GridLayoutManager.HORIZONTAL, false)
            adapter = GridAdapter(dbList, context) { animal -> }
        }

        val mainObserver = Observer<List<Current>> { animals ->
            Log.d("asdf", animals.toString())
            dbList.clear()
            animals.forEach {
                if (it.sortation == "魚") dbList.add(it)
            }
            if (dbList.size == 0) {
                if (App.prefs.language == "ko") {
                    view.ExceptionTextF.visibility = View.VISIBLE
                    view.ExceptionTextF.text = "0건"
                } else {
                    view.ExceptionTextF.visibility = View.VISIBLE
                    view.ExceptionTextF.text = "0件"
                }
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
                    layoutManager =
                        GridLayoutManager(context, 5, GridLayoutManager.HORIZONTAL, false)
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
        view.fsort_wrap.setVisibility(View.GONE)

        /*view.sortBtn.setOnClickListener {
            view.fsort_wrap.setVisibility(View.VISIBLE)
            view.sortBtn.setOnClickListener {
                view.fsort_wrap.setVisibility(View.GONE)
                view.sortBtn.setOnClickListener {
                    view.fsort_wrap.setVisibility(View.VISIBLE)
                    view.sortBtn.setOnClickListener {
                        view.fsort_wrap.setVisibility(View.GONE)
                    }
                }
            }
        }*/

        view.sortBtn.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) view.fsort_wrap.setVisibility(View.VISIBLE)
            else view.fsort_wrap.setVisibility(View.GONE)
        }

        view.fpriceBtn.setOnCheckedChangeListener { _, isChecked ->

            if (!isChecked) {
                var plist = sortByPriceDown(dbList)
                fpriceBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_baseline_arrow_drop_down_24))
                view.tabLayoutFishList.apply {
                    layoutManager = LinearLayoutManager(activity)
                    adapter = CurrentAdapter(plist, context, view) { animal -> }
                }
                view.m4.apply {
                    layoutManager =
                        GridLayoutManager(context, 5, GridLayoutManager.HORIZONTAL, false)
                    adapter = GridAdapter(dbList, context) { animal -> }
                }
            } else {
                var plist = sortByPriceUp(dbList)
                fpriceBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_baseline_arrow_drop_up_24))
                view.tabLayoutFishList.apply {
                    layoutManager = LinearLayoutManager(activity)
                    adapter = CurrentAdapter(plist, context, view) { animal -> }
                }
                view.m4.apply {
                    layoutManager =
                        GridLayoutManager(context, 5, GridLayoutManager.HORIZONTAL, false)
                    adapter = GridAdapter(dbList, context) { animal -> }
                }
            }


            /* var plist = sortByPrice(dbList)

             view.tabLayoutFishList.apply {
                 layoutManager = LinearLayoutManager(activity)
                 adapter = CurrentAdapter(plist, context, view) { animal -> }
             }*/
        }

        view.fnameBtn.setOnCheckedChangeListener { _, isChecked ->

            if (!isChecked) {
                var plist = sortByNameDown(dbList)
                fnameBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_baseline_arrow_drop_down_24))
                view.tabLayoutFishList.apply {
                    layoutManager = LinearLayoutManager(activity)
                    adapter = CurrentAdapter(plist, context, view) { animal -> }
                }
                view.m4.apply {
                    layoutManager =
                        GridLayoutManager(context, 5, GridLayoutManager.HORIZONTAL, false)
                    adapter = GridAdapter(dbList, context) { animal -> }
                }
            } else {
                var plist = sortByNameUp(dbList)
                fnameBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_baseline_arrow_drop_up_24))
                view.tabLayoutFishList.apply {
                    layoutManager = LinearLayoutManager(activity)
                    adapter = CurrentAdapter(plist, context, view) { animal -> }
                }
                view.m4.apply {
                    layoutManager =
                        GridLayoutManager(context, 5, GridLayoutManager.HORIZONTAL, false)
                    adapter = GridAdapter(dbList, context) { animal -> }
                }
            }
        }

        return view
    }

    fun sortByPriceUp(klist: ArrayList<Current>): ArrayList<Current> {
        var plist: ArrayList<Current> = klist
        for (i in 0 until plist.size) {
            for (j in i until plist.size - 1) {
                if (plist[i].price < plist[j].price) {
                    var temp: Current = plist[i]
                    plist[i] = plist[j]
                    plist[j] = temp
                }
            }
        }
        return plist
    }

    fun sortByPriceDown(klist: ArrayList<Current>): ArrayList<Current> {
        var plist: ArrayList<Current> = klist
        for (i in 0 until plist.size) {
            for (j in i until plist.size - 1) {
                if (plist[i].price > plist[j].price) {
                    var temp: Current = plist[i]
                    plist[i] = plist[j]
                    plist[j] = temp
                }
            }
        }
        return plist
    }

    fun sortByNameDown(klist: ArrayList<Current>): ArrayList<Current> {
        var plist: ArrayList<Current> = klist
        for (i in 0 until plist.size) {
            for (j in i until plist.size - 1) {
                if (App.prefs.language == "ja") {
                    if (plist[i].name.toString()[0].toInt() > plist[j].name.toString()[0].toInt()) {
                        var temp: Current = plist[i]
                        plist[i] = plist[j]
                        plist[j] = temp
                    }
                } else {
                    if (plist[i].namek.toString()[0].toInt() > plist[j].namek.toString()[0].toInt()) {
                        var temp: Current = plist[i]
                        plist[i] = plist[j]
                        plist[j] = temp
                    }
                }
            }
        }
        return plist
    }

    fun sortByNameUp(klist: ArrayList<Current>): ArrayList<Current> {
        var plist: ArrayList<Current> = klist
        for (i in 0 until plist.size) {
            for (j in i until plist.size - 1) {
                if (App.prefs.language == "ja") {
                    if (plist[i].name.toString()[0].toInt() < plist[j].name.toString()[0].toInt()) {
                        var temp: Current = plist[i]
                        plist[i] = plist[j]
                        plist[j] = temp
                    }
                } else {
                    if (plist[i].namek.toString()[0].toInt() < plist[j].namek.toString()[0].toInt()) {
                        var temp: Current = plist[i]
                        plist[i] = plist[j]
                        plist[j] = temp
                    }
                }
            }
        }
        return plist
    }


}
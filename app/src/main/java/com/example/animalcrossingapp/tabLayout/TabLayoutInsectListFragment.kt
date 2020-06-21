package com.example.animalcrossingapp.tabLayout

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.animalcrossingapp.R
import com.example.animalcrossingapp.controller.App
import com.example.animalcrossingapp.controller.CurrentAdapter
import com.example.animalcrossingapp.controller.GridAdapter
import com.example.animalcrossingapp.database.AnimalCrossingDB
import com.example.animalcrossingapp.database.Current
import com.example.animalcrossingapp.model.AnimalViewModel
import kotlinx.android.synthetic.main.fragment_tab_layout_insect_list.*
import kotlinx.android.synthetic.main.fragment_tab_layout_insect_list.m2
import kotlinx.android.synthetic.main.fragment_tab_layout_insect_list.view.*
import kotlinx.android.synthetic.main.fragment_tab_layout_insect_list.view.m2
import kotlinx.android.synthetic.main.fragment_tab_layout_insect_list.view.tabLayoutInsectList
import java.util.*

class TabLayoutInsectListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_tab_layout_insect_list, container, false)
        val context: Context = requireContext()
        val db = AnimalCrossingDB.getInstance(context)!!
        val dbList = arrayListOf<Current>()
        /*val clist = db.animalCrossingDao().selectTablayoutAllInsect()
        val list = arguments?.getParcelableArrayList<Current>("blist")!!
        if (list.size == 0) clist.forEach{dbList.add(it)}
        else { dbList.addAll(list) }*/
        if (App.prefs.language == "ko") {
            view.textViewB11.text = "이름"
            view.textViewB12.text = "가격"
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
                view.sButton.visibility = View.GONE
            }
            "search" -> liveList = model.getSearch(keyword)
            /*"detail" -> liveList = model.getDetail(searchMap)*/
            "detail" -> {
//                liveList = model.getDetail(searchMap)
                var codeList = arrayListOf<String>()
                model.getDetail(searchMap).forEach {
                    codeList.add(it.information_code!!.toUpperCase())
                }
                liveList = model.getFullList(codeList)
            }
            else -> liveList = model.animals
        }

        view.tabLayoutInsectList.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = CurrentAdapter(dbList, context, view) { animal -> }
        }
        /*val griviewAdapter = ClickableGridviewAdapter(requireContext(), dbList)
        view.gridView3.adapter = griviewAdapter*/
        view.m2.apply {
            layoutManager = GridLayoutManager(context, 5, GridLayoutManager.HORIZONTAL, false)
            adapter = GridAdapter(dbList, context) { animal -> }
        }
//        var liveList = db.animalCrossingDao().selectAll()
//        Log.d("live", liveList.)
        val mainObserver = Observer<List<Current>> { animals ->
            dbList.clear()
            animals.forEach {
                if (it.sortation == "虫") dbList.add(it)
            }
            if (dbList.size == 0) {
                if (App.prefs.language == "ko") {
                    view.ExceptionTextB.visibility = View.VISIBLE
                    view.ExceptionTextB.text = "0건"
                } else {
                    view.ExceptionTextB.visibility = View.VISIBLE
                    view.ExceptionTextB.text = "0件"
                }
            }
            if (view.toggleButton1.isChecked) {
                view.tabLayoutInsectList.apply {
                    layoutManager = LinearLayoutManager(activity)
                    adapter = CurrentAdapter(dbList, context, view) { animal -> }
                }
            } else {
                /*val griviewAdapter = ClickableGridviewAdapter(requireContext(), dbList)
                view.gridView3.adapter = griviewAdapter*/
                view.m2.apply {
                    layoutManager =
                        GridLayoutManager(context, 5, GridLayoutManager.HORIZONTAL, false)
                    adapter = GridAdapter(dbList, context) { animal -> }
                }
            }
        }
        liveList.observe(viewLifecycleOwner, mainObserver)
        //

        fun sortByName(klist: ArrayList<Current>): ArrayList<Current> {
            var plist: ArrayList<Current> = klist
            for (i in 0 until plist.size - 1) {
                for (j in i until plist.size - 1) {
                    if (plist[i].name.toString()[0].toInt() > plist[j].name.toString()[0].toInt()) {
                        val temp: Current = plist[j + 1]
                        plist[j + 1] = plist[j]
                        plist[j] = temp
                    }
                }
            }
            return plist
        }


        fun sortByPrice(klist: ArrayList<Current>): ArrayList<Current> {
            var plist: ArrayList<Current> = klist
            for (i in 0 until plist.size - 1) {
                for (j in i until plist.size - 1) {
                    if (plist[i].price > plist[j].price) {
                        val temp: Current = plist[j + 1]
                        plist[j + 1] = plist[j]
                        plist[j] = temp
                    }
                }
            }
            return plist
        }

        /*var imgArr = Array(dbList.size, {0})
        var idx = 0
        dbList.forEach {
            var id = it.information_code
            imgArr[idx] = this.getResources().getIdentifier(id, "drawable", requireContext().getPackageName())
            idx++
        }*/

        /*val griviewAdapter = GridviewAdapter2(requireContext(), imgArr)
         view.gridView4.adapter = griviewAdapter*/


        view.m2.setVisibility(View.GONE)

        view.toggleButton1.setOnCheckedChangeListener { _, isChecked ->
            val arrangeList = db.animalCrossingDao().selectArrange(hemishpere)
            val list = arrayListOf<Current>()
            arrangeList.forEach {
                if(it.sortation == "虫") list.add(it)
            }
            if (isChecked) {
                toggleButton1.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_list_2))
                tabLayoutInsectList.setVisibility(View.GONE)
                m2.setVisibility(View.VISIBLE)
                //
                if (selector == "arrange") {
                    view.m2.apply {
                        layoutManager =
                            GridLayoutManager(context, 5, GridLayoutManager.HORIZONTAL, false)
                        adapter = GridAdapter(list, context) { animal -> }
                    }
                }
                //
            } else {
                toggleButton1.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_grid_2))
                tabLayoutInsectList.setVisibility(View.VISIBLE)
                m2.setVisibility(View.GONE)
                //
                if (selector == "arrange") {
                    view.tabLayoutInsectList.apply {
                        layoutManager = LinearLayoutManager(activity)
                        adapter = CurrentAdapter(list, context, view) { animal -> }
                    }
                }
                //
            }
        }

        view.bsort_wrap.setVisibility(View.GONE)

        /*view.sButton.setOnClickListener {
            view.bsort_wrap.setVisibility(View.VISIBLE)
            view.sButton.setOnClickListener {
                view.bsort_wrap.setVisibility(View.GONE)
                view.sButton.setOnClickListener {
                    view.bsort_wrap.setVisibility(View.VISIBLE)
                    view.sButton.setOnClickListener {
                        view.bsort_wrap.setVisibility(View.GONE)
                    }
                }
            }
        }*/

        view.sButton.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) view.bsort_wrap.setVisibility(View.VISIBLE)
            else view.bsort_wrap.setVisibility(View.GONE)
        }

        view.bPriceBtn.setOnCheckedChangeListener { _, isChecked ->

            if (!isChecked) {
                var plist = sortByPriceDown(dbList)
                bPriceBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_baseline_arrow_drop_down_24))
                view.tabLayoutInsectList.apply {
                    layoutManager = LinearLayoutManager(activity)
                    adapter = CurrentAdapter(plist, context, view) { animal -> }
                }
                view.m2.apply {
                    layoutManager =
                        GridLayoutManager(context, 5, GridLayoutManager.HORIZONTAL, false)
                    adapter = GridAdapter(dbList, context) { animal -> }
                }
            } else {
                var plist = sortByPriceUp(dbList)
                bPriceBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_baseline_arrow_drop_up_24))
                view.tabLayoutInsectList.apply {
                    layoutManager = LinearLayoutManager(activity)
                    adapter = CurrentAdapter(plist, context, view) { animal -> }
                }
                view.m2.apply {
                    layoutManager =
                        GridLayoutManager(context, 5, GridLayoutManager.HORIZONTAL, false)
                    adapter = GridAdapter(dbList, context) { animal -> }
                }
            }


            var plist = sortByPrice(dbList)

            view.tabLayoutInsectList.apply {
                layoutManager = LinearLayoutManager(activity)
                adapter = CurrentAdapter(plist, context, view) { animal -> }
            }
        }

        view.bNameBtn.setOnCheckedChangeListener { _, isChecked ->

            if (!isChecked) {
                var plist = sortByNameDown(dbList)
                bNameBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_baseline_arrow_drop_down_24))
                view.tabLayoutInsectList.apply {
                    layoutManager = LinearLayoutManager(activity)
                    adapter = CurrentAdapter(plist, context, view) { animal -> }
                }
                view.m2.apply {
                    layoutManager =
                        GridLayoutManager(context, 5, GridLayoutManager.HORIZONTAL, false)
                    adapter = GridAdapter(dbList, context) { animal -> }
                }
            } else {
                var plist = sortByNameUp(dbList)
                bNameBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_baseline_arrow_drop_up_24))
                view.tabLayoutInsectList.apply {
                    layoutManager = LinearLayoutManager(activity)
                    adapter = CurrentAdapter(plist, context, view) { animal -> }
                }
                view.m2.apply {
                    layoutManager =
                        GridLayoutManager(context, 5, GridLayoutManager.HORIZONTAL, false)
                    adapter = GridAdapter(dbList, context) { animal -> }
                }
            }


            var plist = sortByName(dbList)

            view.tabLayoutInsectList.apply {
                layoutManager = LinearLayoutManager(activity)
                adapter = CurrentAdapter(plist, context, view) { animal -> }
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
                if (plist[i].name.toString()[0].toInt() > plist[j].name.toString()[0].toInt()) {
                    var temp: Current = plist[i]
                    plist[i] = plist[j]
                    plist[j] = temp
                }
            }
        }
        return plist
    }

    fun sortByNameUp(klist: ArrayList<Current>): ArrayList<Current> {
        var plist: ArrayList<Current> = klist
        for (i in 0 until plist.size) {
            for (j in i until plist.size - 1) {
                if (plist[i].name.toString()[0].toInt() < plist[j].name.toString()[0].toInt()) {
                    var temp: Current = plist[i]
                    plist[i] = plist[j]
                    plist[j] = temp
                }
            }
        }
        return plist
    }
}
package com.example.animalcrossingapp.tab

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.animalcrossingapp.R
import com.example.animalcrossingapp.controller.CurrentAdapter
import com.example.animalcrossingapp.database.AnimalCrossingDB
import com.example.animalcrossingapp.database.Current
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.blank_fragment.*
import kotlinx.android.synthetic.main.blank_fragment.view.tab_layout

class BlankFragment : Fragment() {

    private lateinit var demoCollectionAdapter: DemoCollectionAdapter
    private lateinit var viewPager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.blank_fragment, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        demoCollectionAdapter = DemoCollectionAdapter(this, requireContext())
        viewPager = view.findViewById(R.id.pager)
        viewPager.adapter = demoCollectionAdapter
        TabLayoutMediator(tab_layout, pager) { tab, position ->
            tab.text = "OBJECT ${(position + 1)}"
        }.attach()
    }


}

class DemoCollectionAdapter(fragment: Fragment, contenxt: Context) : FragmentStateAdapter(fragment) {

    val db = AnimalCrossingDB.getInstance(contenxt)!!

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        val fragment = TabListFragment()
        var list = arrayListOf<Current>()

        if (position == 0) {
        } else if (position == 1) {
            var alist = db.animalCrossingDao().selectFishes()
            alist.forEach { list.add(it) }
            fragment.arguments = Bundle().apply {
                putParcelableArrayList("list", list)
            }
        } else if (position == 2 ) {
            var alist = db.animalCrossingDao().selectBugs()
            alist.forEach { list.add(it) }
            fragment.arguments = Bundle().apply {
                putParcelableArrayList("list", list)
            }
        }
        /*fragment.arguments = Bundle().apply {
            putInt(ARG_OBJECT, position + 1)
        }*/
        return fragment
    }
}

private const val ARG_OBJECT = "object"

/*
class DemoObjectFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_collection_object, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey(ARG_OBJECT) }?.apply {
            val textView: TextView = view.findViewById(android.R.id.text1)
            textView.text = getInt(ARG_OBJECT).toString()
        }
    }
}*/

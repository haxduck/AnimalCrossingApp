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
import androidx.viewpager.widget.PagerAdapter
import com.example.animalcrossingapp.R
import com.example.animalcrossingapp.controller.CurrentAdapter
import com.example.animalcrossingapp.controller.TabLayoutAdapter
import com.example.animalcrossingapp.database.AnimalCrossingDB
import com.example.animalcrossingapp.database.Current
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.fragment_second.view.*
import java.io.Serializable
import java.lang.Exception

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
        var plist = arguments?.getParcelableArrayList<Current>("list")
        var selector: String = ""
        var keyword: String = ""
        var searchMap = hashMapOf<String, Any>()
        try {
            selector = arguments?.getString("selector")!!
        } catch (e: Exception) {
            selector = "all"
        }
        try {
            keyword = arguments?.getString("keyword")!!
        } catch (e: Exception) {
            keyword = "%%"
        }
        try {
            searchMap = arguments?.getSerializable("searchMap")!! as HashMap<String, Any>
        } catch (e: Exception) {
            searchMap = hashMapOf<String, Any>()
        }
        if (plist != null) {
            plist.forEach { list.add(it) }
        }

        val pageAdapter : PagerAdapter = TabLayoutAdapter(childFragmentManager, list, selector, keyword, searchMap, context)

        view.tabLayoutViewPager.adapter = pageAdapter
        view.tabLayout.setupWithViewPager(view.tabLayoutViewPager)
        view.tabLayout.getTabAt(0)?.setIcon(R.drawable.fish_copy)
        view.tabLayout.getTabAt(1)?.setIcon(R.drawable.bug_copy)

        return view
    }

}

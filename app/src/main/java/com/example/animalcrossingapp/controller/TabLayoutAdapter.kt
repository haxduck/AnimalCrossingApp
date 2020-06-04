package com.example.animalcrossingapp.controller

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.animalcrossingapp.R
import com.example.animalcrossingapp.database.AnimalCrossingDB
import com.example.animalcrossingapp.database.Current
import com.example.animalcrossingapp.tabLayout.TabLayoutAllListFragment
import com.example.animalcrossingapp.tabLayout.TabLayoutFishListFragment
import com.example.animalcrossingapp.tabLayout.TabLayoutInsectListFragment
import com.example.animalcrossingapp.toolbar.SecondFragment
import com.example.animalcrossingapp.view.MainActivity
import java.util.concurrent.ConcurrentNavigableMap

class TabLayoutAdapter(fm : FragmentManager, list : ArrayList<Current>, context: Context) : FragmentPagerAdapter(fm){

    private val bundle: Bundle = Bundle()
    private var alist: ArrayList<Current> = list
    private val context = context

    override fun getItem(position: Int): Fragment {
        /*when(position){
            0 -> {return TabLayoutAllListFragment()}
            1 -> {return TabLayoutFishListFragment()}
            2 -> {return TabLayoutInsectListFragment()}
            else -> {return TabLayoutAllListFragment()}
        }*/
        val db = AnimalCrossingDB.getInstance(context)
        if (alist.size == 0){
            alist.addAll(db?.animalCrossingDao()?.selectAll()!!)
        }
        var flist = arrayListOf<Current>()
        Log.d("logg", alist.toString())
        alist.forEach{
            if (it.sortation == "魚") flist.add(it)
        }
        var blist = arrayListOf<Current>()
        alist.forEach{
            if (it.sortation == "虫") blist.add(it)
        }
        var frg = TabLayoutAllListFragment()
        when(position){
            0 -> {
//                val frg = TabLayoutAllListFragment()
                bundle.putParcelableArrayList("list", alist)
                frg.arguments = bundle
//                return frg
            }
            1 -> {
//                val frg = TabLayoutFishListFragment()
                bundle.putParcelableArrayList("list", flist)
                frg.arguments = bundle
//                return frg
            }
            2 -> {
//                val frg = TabLayoutInsectListFragment()
                bundle.putParcelableArrayList("list", blist)
                frg.arguments = bundle
//                return frg
            }
            else -> {return TabLayoutAllListFragment()}
        }

        return frg

    }

    override fun getPageTitle(position: Int): CharSequence? {
        when(position){
            0 -> {return ""}
            1 -> {return ""}
            2 -> {return ""}
        }

     return super.getPageTitle(position)
    }

    override fun getCount(): Int {
       return 3

    }

}
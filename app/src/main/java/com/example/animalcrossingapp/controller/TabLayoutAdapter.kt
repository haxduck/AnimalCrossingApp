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
import kotlinx.coroutines.selects.select
import java.util.concurrent.ConcurrentNavigableMap

class TabLayoutAdapter(fm : FragmentManager, list : ArrayList<Current>, selector: String, keyword: String, context: Context) : FragmentPagerAdapter(fm){

    private val bundle: Bundle = Bundle()
    private var alist: ArrayList<Current> = list
    private val context = context
    private val selector = selector
    private val keyword = keyword

    override fun getItem(position: Int): Fragment {
        /*when(position){
            0 -> {return TabLayoutAllListFragment()}
            1 -> {return TabLayoutFishListFragment()}
            2 -> {return TabLayoutInsectListFragment()}
            else -> {return TabLayoutAllListFragment()}
        }*/
        val db = AnimalCrossingDB.getInstance(context)
        if (alist.size == 0){
//            alist.addAll(db?.animalCrossingDao()?.selectAll()!!)
        }
        var flist = arrayListOf<Current>()
        alist.forEach{
            if (it.sortation == "魚") flist.add(it)

        }

        fun sortByName(klist : ArrayList<Current>): ArrayList<Current>{
            var plist : ArrayList<Current> = klist
            for(i in 0 until plist.size-1){
                for(j in i until plist.size-1){
                    if(plist[i].name.toString()[0].toInt() > plist[j].name.toString()[0].toInt()){
                        val temp : Current = plist[j+1]
                        plist[j+1] = plist[j]
                        plist[j] = temp
                    }
                }
            }
            return plist
        }



        fun sortByPrice(klist : ArrayList<Current>): ArrayList<Current>{
            var plist : ArrayList<Current> = klist
            for(i in 0 until plist.size-1){
                for(j in i until plist.size-1){
                    if(plist[i].price > plist[j].price){
                        val temp : Current = plist[j+1]
                        plist[j+1] = plist[j]
                        plist[j] = temp
                    }
                }
            }
            return plist
        }

        var blist = arrayListOf<Current>()
        alist.forEach{
            if (it.sortation == "虫") blist.add(it)
        }
        var frg = TabLayoutAllListFragment()
        when(position){
            0 -> {
                val frg = TabLayoutFishListFragment()
                bundle.putParcelableArrayList("flist", flist)
                bundle.putString("selector", selector)
                bundle.putString("keyword", keyword)
                frg.arguments = bundle
                return frg
            }
            1 -> {
                val frg = TabLayoutInsectListFragment()
                bundle.putParcelableArrayList("blist", blist)
                bundle.putString("selector", selector)
                bundle.putString("keyword", keyword)
                frg.arguments = bundle
                return frg
            }
            else -> {return TabLayoutAllListFragment()}
        }

    }

    override fun getPageTitle(position: Int): CharSequence? {
        when(position){
            0 -> {return ""}
            1 -> {return ""}
        }

     return super.getPageTitle(position)
    }

    override fun getCount(): Int {
       return 2
    }

}
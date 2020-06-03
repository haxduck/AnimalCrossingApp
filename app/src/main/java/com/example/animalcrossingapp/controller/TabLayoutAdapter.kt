package com.example.animalcrossingapp.controller

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.animalcrossingapp.tabLayout.TabLayoutAllListFragment
import com.example.animalcrossingapp.tabLayout.TabLayoutFishListFragment
import com.example.animalcrossingapp.tabLayout.TabLayoutInsectListFragment

class TabLayoutAdapter(fm : FragmentManager) : FragmentPagerAdapter(fm){
    override fun getItem(position: Int): Fragment {
        when(position){
            0 -> {return TabLayoutAllListFragment()}
            1 -> {return TabLayoutInsectListFragment()}
            2 -> {return TabLayoutFishListFragment()}
            else -> {return TabLayoutAllListFragment()}
        }

    }

    override fun getPageTitle(position: Int): CharSequence? {
        when(position){
            0 -> {return "All"}
            1 -> {return "Insect"}
            2 -> {return "Fish"}
        }

     return super.getPageTitle(position)
    }

    override fun getCount(): Int {
       return 3

    }

}
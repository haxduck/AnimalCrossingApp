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
            1 -> {return TabLayoutFishListFragment()}
            2 -> {return TabLayoutInsectListFragment()}
            else -> {return TabLayoutAllListFragment()}
        }

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
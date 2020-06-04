package com.example.animalcrossingapp.view

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.animalcrossingapp.R
import com.example.animalcrossingapp.controller.App
import com.example.animalcrossingapp.database.AnimalCrossingDB
import com.example.animalcrossingapp.database.Current
import com.example.animalcrossingapp.toolbar.FirstFragment
import com.example.animalcrossingapp.toolbar.SecondFragment
import com.example.animalcrossingapp.toolbar.ThirdFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.blank_fragment.*
import me.ibrahimsn.lib.OnItemReselectedListener

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navController = findNavController(R.id.main_fragment)
//      setupActionBarWithNavController(navController)
        setSupportActionBar(menu_top)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_bottom,menu)
        menuInflater.inflate(R.menu.menu_top,menu)
        bottomBar.setupWithNavController(menu!!,navController)
        var db = AnimalCrossingDB.getInstance(this)!!

        /*val manager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchItem = menu?.findItem(R.id.search)
        val searchView = searchItem?.actionView as SearchView

        bottomBar.onItemReselected = {
            when (it) {
                0 -> supportFragmentManager.beginTransaction()
                    .replace(R.id.main_fragment, FirstFragment()).addToBackStack(null).commit()
                1 -> supportFragmentManager.beginTransaction()
                    .replace(R.id.main_fragment, SecondFragment()).addToBackStack(null).commit()
                2 -> supportFragmentManager.beginTransaction()
                    .replace(R.id.main_fragment, ThirdFragment()).addToBackStack(null).commit()
            }
        }

        searchView.setSearchableInfo(manager.getSearchableInfo(componentName))

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                searchView.setQuery("", false)
                searchItem.collapseActionView()
                Toast.makeText(this@MainActivity, "Looking for $query", Toast.LENGTH_LONG).show()

                val searchList = db.animalCrossingDao().selectSearch("%" + query!! + "%")
                Log.d("searList", query + "/" +searchList.toString())
                var list = arrayListOf<Current>()

                val bundle: Bundle = Bundle()
                searchList.forEach {
                    list.add(it)
                }
                bundle.putParcelableArrayList("list", list)
                val frg = SecondFragment()
                frg.arguments = bundle
                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_fragment, frg).addToBackStack(null).commit()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }


        })

        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        navController.navigateUp()
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menu?.getItem(2)?.setVisible(false)
        return super.onPrepareOptionsMenu(menu)
    }

    /*fun replaceFragment(fragment: Fragment, tag: String) {
        val fragmentManager = supportFragmentManager
        val fragmentTranceaction = fragmentManager.beginTransaction()
        fragmentTranceaction.replace(R.id.main_fragment, fragment).commit()
    }*/



}


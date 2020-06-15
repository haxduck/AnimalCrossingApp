package com.example.animalcrossingapp.view

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.animalcrossingapp.R
import com.example.animalcrossingapp.database.AnimalCrossingDB
import com.example.animalcrossingapp.model.KeywordViewModel
import com.example.animalcrossingapp.popup.SearchPopDialogFragment
import com.example.animalcrossingapp.toolbar.FirstFragment
import com.example.animalcrossingapp.toolbar.SecondFragment
import com.example.animalcrossingapp.toolbar.ThirdFragment
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navController = findNavController(R.id.main_fragment)
//      setupActionBarWithNavController(navController)
        setSupportActionBar(menu_top)
        val db = AnimalCrossingDB.getInstance(this)!!
        val model: KeywordViewModel by viewModels()


        //검색로직
       /* try {
            var searchMap = intent.getSerializableExtra("searchMap")
            if(searchMap != null) {
                val bundle: Bundle = Bundle()
                bundle.putSerializable("searchMap", searchMap)
                bundle.putString("selector", "detail")
                val frg = SecondFragment()
                frg.arguments = bundle
                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_fragment, frg).addToBackStack(null).commit()
            }
        } catch (e : Exception) {
            Log.d("searchMap", "null")
        }*/


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.btn_filter) {
           /* var intent = Intent(this, PopActivity::class.java)
            startActivityForResult(intent, 1)*/
            //
            var frg = SearchPopDialogFragment()
            frg.show(supportFragmentManager, "search")
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_bottom,menu)
        menuInflater.inflate(R.menu.menu_top,menu)
        bottomBar.setupWithNavController(menu!!,navController)
        val db = AnimalCrossingDB.getInstance(this)!!

        val manager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchItem = menu.findItem(R.id.search)
        val searchView = searchItem?.actionView as SearchView

//        Log.d("mm", menu.getItem(0).toString())

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

               /* val searchList = db.animalCrossingDao().selectSearch("%" + query!! + "%")
//                Log.d("searList", query + "/" +searchList.toString())
                var list = arrayListOf<Current>()*/

                val bundle: Bundle = Bundle()
                /*searchList.forEach {
                    list.add(it)
                }
                bundle.putParcelableArrayList("list", list)*/
                bundle.putString("selector", "search")
                bundle.putString("keyword", "%" + query!! + "%")
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

}


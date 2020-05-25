package com.example.animalcrossingapp.view

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import com.example.animalcrossingapp.R
import com.example.animalcrossingapp.controller.App
import com.example.animalcrossingapp.controller.MainController
import com.example.animalcrossingapp.dao.FishDBHelper
import com.example.animalcrossingapp.vo.BugVO
import com.example.animalcrossingapp.vo.FishVO
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.collections.ArrayList
import androidx.appcompat.widget.SearchView



class MainActivity : AppCompatActivity() {

    lateinit var fishDBHelper: FishDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fishDBHelper = FishDBHelper(this)
        var fishes = fishDBHelper.readAllFishes()

        //첫 실행 판단 prefs.xml 저장
        val iniFlag = App.prefs.initialFlag
        Toast.makeText(this, "플래그: $iniFlag", Toast.LENGTH_LONG).show()

        if(iniFlag == "1") {
            setContentView(R.layout.activity_main)
        } else {
            setContentView(R.layout.activity_main)
            val nextIntent = Intent(this, InitialActivity::class.java)
            startActivity(nextIntent)
        }

        textView2.setText(
                    "リアルタイム情報" + "\n" +
                    MainController.currentTime()
        )

        textView5.setText(
            MainController.currentFishList().toString() + "\n"
            + MainController.currentBugList().toString() + "\n"
            + fishes.toString()
        )

        var flist:ArrayList<FishVO> = MainController.currentFishList()
        var blist:ArrayList<BugVO> = MainController.currentBugList()
        textView5.setOnClickListener{
            val nextIntent = Intent(this, RealtimeListActivity::class.java)
            nextIntent.putExtra("flist", flist)
            nextIntent.putExtra("blist", blist)
            startActivity(nextIntent)
        }

        textView3.setText("" + MainController.catchFishList().size + "/" + MainController.currentFishList().size)
        textView4.setText("" + MainController.catchBugList().size + "/" + MainController.currentBugList().size)

        settingBtn.setOnClickListener{
            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
        }

        if(intent.hasExtra("msg")){
            hankyu.setText(intent.getStringExtra("msg"))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val inflater = menuInflater
        inflater.inflate(R.menu.menu_top, menu)
//        menuInflater.inflate(R.menu.menu_bottom, menu)
//        bottomBar.setupWithNavController(menu!!, navController)

        val manager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchItem = menu?.findItem(R.id.search)
        val searchView = searchItem?.actionView as SearchView

        searchView.setSearchableInfo(manager.getSearchableInfo(componentName))


        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                searchView.setQuery("", false)
                searchItem.collapseActionView()
                Toast.makeText(this@MainActivity, "Looking for $query", Toast.LENGTH_LONG).show()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })

      return true
    }


    //test
    fun clearXml(view: View) {
        App.prefs.initialFlag = ""
        App.prefs.hemisphere = ""
    }
//

}



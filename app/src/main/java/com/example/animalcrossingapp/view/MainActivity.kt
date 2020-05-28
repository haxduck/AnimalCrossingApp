package com.example.animalcrossingapp.view

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.animalcrossingapp.R
import com.example.animalcrossingapp.controller.App
import com.example.animalcrossingapp.controller.MainController
import kotlinx.android.synthetic.main.activity_main.*
import androidx.appcompat.widget.SearchView
import androidx.room.Room
import com.example.animalcrossingapp.room.AnimalDB
import com.example.animalcrossingapp.room.AnimalVO
import com.example.animalcrossingapp.table.TestDB
import kotlinx.android.synthetic.main.item_animal.*
import me.ibrahimsn.lib.OnItemSelectedListener
import me.ibrahimsn.lib.SmoothBottomBar
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = AnimalDB.getInstance(this)!!

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
        if(App.prefs.language == "ko"){
            textView2.setText(
                "실시간 정보 " + "\n" + MainController.currentTime()
            )
        }else {
            textView2.setText(
                "リアルタイム情報" + "\n" + MainController.currentTime()
            )
        }

        bottomBar.onItemSelected = {
            if(it == 1){
                val intent = Intent(this, ListActivity::class.java)
                startActivity(intent)
            }
            if(it == 2){
                val intent = Intent(this, SettingActivity::class.java)
                startActivity(intent)
            }
        }

        textView2.setOnClickListener {
            val intent = Intent(this, ListActivity::class.java)
            val list = arrayListOf<AnimalVO>()
            searchRealTimeList().forEach{
                list.add(it)
            }
            intent.putParcelableArrayListExtra("list", list)
            startActivity(intent)
        }

        val img = arrayOf(
            R.drawable.icon_ray,
            R.drawable.icon_redsnapper,
            R.drawable.icon_ribboneel,
            R.drawable.icon_saddledbichir,
            R.drawable.icon_salmon,
            R.drawable.icon_sawshark,
            R.drawable.icon_seabass,
            R.drawable.icon_seabutterfly,
            R.drawable.icon_seahorse,
            R.drawable.icon_snappingturtle,
            R.drawable.icon_softshelledturtle,
            R.drawable.icon_anchovy,
            R.drawable.icon_angelfish,
            R.drawable.icon_arapaima,
            R.drawable.icon_arowana,
            R.drawable.icon_barredknifejaw,
            R.drawable.icon_barreleye,
            R.drawable.icon_betta,
            R.drawable.icon_bitterling,
            R.drawable.icon_blackbass,
            R.drawable.icon_blowfish,
            R.drawable.icon_bluegill,
            R.drawable.icon_bluemarlin,
            R.drawable.icon_butterflyfish,
            R.drawable.icon_carp,
            R.drawable.icon_catfish

        )

        val realTimeList = searchRealTimeList()
        var imgArr = Array(realTimeList.size, {0})
        var idx = 0
        realTimeList.forEach {
            var id = "a" + it.aid
            imgArr[idx] = this.getResources().getIdentifier(id, "drawable", this.getPackageName())
            idx++
        }
        val griviewAdapter = GridviewAdapter(this, imgArr)
        gridView1.adapter = griviewAdapter

        val catchFishes = db.animalDao().selectCatchFish().size
        val catchBugs = db.animalDao().selectCatchBug().size
//        fish_progress.progress = catchFishes
//        bug_progress.progress = catchBugs
//        catch_fish_text.text = "" + "" + catchFishes + "/80"
//        catch_bug_text.text = "" + catchBugs + "/80"
    }





    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val inflater = menuInflater
       inflater.inflate(R.menu.menu_top, menu)
       menuInflater.inflate(R.menu.menu_bottom, menu)
        //bottomBar.setupWithNavController(menu!!, navController)

//        bottomBar.setOnItemSelectedListener{ id ->
//            when (id){
//                R.id.first_fragment -> intent(this, SettingActivity::class.java)
//            }
//        }

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



    @SuppressLint("SetTextI18n")
    private fun showTab(i: Int) {
        Log.d("MainActi  vity", "onTabSelected: $i")
    }

    //test
    fun clearXml(view: View) {
        App.prefs.initialFlag = ""
        App.prefs.hemisphere = ""
    }
    //

    fun searchRealTimeList(): ArrayList<AnimalVO> {
        //실시간 현재 시간 / 반구 /
        val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        val hemisphere = App.prefs.hemisphere
        val db = AnimalDB.getInstance(this)!!
        val allList = db.animalDao().selectAll()
        val realTimeList = mutableSetOf<AnimalVO>()
        val thisMonthList = mutableSetOf<AnimalVO>()
        allList.forEach { animal ->
            //월
            val monthList =
                animal.nm!!.replace("[", "").replace("]", "").split(", ").map { it.toInt() }
            monthList.forEach {
                if (it == Calendar.getInstance().get(Calendar.MONTH) + 1) {
                    thisMonthList.add(animal)
                }
            }

            //시간
            thisMonthList.forEach { thisMonthAnimal ->
                val timeList = arrayListOf<String>()
                var intList: List<Int>
                timeList.add(thisMonthAnimal.time!!)
                timeList.forEach {
                    intList = it.replace("[", "").replace("]", "").split(", ").map { it.toInt() }
                    intList.forEach {
                        if (it == currentHour) {
                            realTimeList.add(thisMonthAnimal)
                        }
                    }
                }
            }
        }
        val list: ArrayList<AnimalVO> = arrayListOf()
        realTimeList.forEach {
            list.add(it)
        }
        return list
    }

}

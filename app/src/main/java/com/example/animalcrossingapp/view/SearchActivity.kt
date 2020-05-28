package com.example.animalcrossingapp.view

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import com.example.animalcrossingapp.R
import com.example.animalcrossingapp.cache.Member
import com.example.animalcrossingapp.controller.App
import com.example.animalcrossingapp.room.AnimalDB
import com.example.animalcrossingapp.room.AnimalVO
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_search.*
import org.json.JSONArray
import java.io.*

class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        //캐쉬
        val sharedPreference = getSharedPreferences("caches", 0)
        val editor = sharedPreference.edit()
        editor.putString("test", "ttt").apply()
        val test = sharedPreference.getString("test", "")
        editor.clear().apply()
        val tmp = Uri.parse("cache")?.lastPathSegment?.let { filename ->
            File.createTempFile(filename, null, this.cacheDir)
        }!!
        val fos = FileOutputStream(tmp)
        val oos = ObjectOutputStream(fos)
        val member = Member("td", listOf("1", "2"))
        oos.writeObject(member)
        oos.flush()

        val fis = FileInputStream(tmp)
        val ois = BufferedInputStream(fis)
        val oin = ObjectInputStream(ois)
        val mem = oin.readObject() as Member

        Log.d("ttt", mem.id)
        tmp.delete()
        //

        searchFB()

        FS.visibility = View.VISIBLE
        BS.visibility = View.INVISIBLE

        // 라디오 버튼 클릭시 이벤트
        sortRG.setOnCheckedChangeListener { _, checkedId ->
            val radioBtn: RadioButton = findViewById(checkedId)
            val sort = radioBtn.text.toString()
            FS.visibility = View.INVISIBLE
            when (sort) {
                "Fish" -> {
                    FS.visibility = View.VISIBLE
                    BS.visibility = View.INVISIBLE
                }
                "Bug" -> {
                    BS.visibility = View.VISIBLE
                    FS.visibility = View.INVISIBLE
                }
                else -> {
                    FS.visibility = View.INVISIBLE
                    BS.visibility = View.INVISIBLE
                }
            }
        }

        searchBTN.setOnClickListener {
            val list = searchFB()
            val slist = arrayListOf<AnimalVO>()
            list.forEach{
                slist.add(it)
            }
            val intent = Intent(this, ListActivity::class.java)
            intent.putExtra("list", slist)
            startActivity(intent)
        }



    }

    fun searchFB(vararg keywords: Any): MutableSet<AnimalVO> {
        val testdb = AnimalDB.getInstance(this)!!

        val name: String
        val min: Int
        val max: Int
        var sort: String = ""
        val month: ArrayList<String> = arrayListOf()
        val habitat: ArrayList<String> = arrayListOf()

        name = searchET.text.toString()

        //월 체크박스
        if (monthCB1.isChecked) month.add(monthCB1.text.toString())
        if (monthCB2.isChecked) month.add(monthCB2.text.toString())
        if (monthCB3.isChecked) month.add(monthCB3.text.toString())
        if (monthCB4.isChecked) month.add(monthCB4.text.toString())
        if (monthCB5.isChecked) month.add(monthCB5.text.toString())
        if (monthCB6.isChecked) month.add(monthCB6.text.toString())
        if (monthCB7.isChecked) month.add(monthCB7.text.toString())
        if (monthCB8.isChecked) month.add(monthCB8.text.toString())
        if (monthCB9.isChecked) month.add(monthCB9.text.toString())
        if (monthCB10.isChecked) month.add(monthCB10.text.toString())
        if (monthCB11.isChecked) month.add(monthCB11.text.toString())
        if (monthCB12.isChecked) month.add(monthCB12.text.toString())

        //서식자 투글버튼
        if (searchTgBtn1.isChecked) habitat.add(searchTgBtn1.text.toString())
        if (searchTgBtn2.isChecked) habitat.add(searchTgBtn2.text.toString())
        if (searchTgBtn3.isChecked) habitat.add(searchTgBtn3.text.toString())
        if (searchTgBtn4.isChecked) habitat.add(searchTgBtn4.text.toString())
        if (searchTgBtn5.isChecked) habitat.add(searchTgBtn5.text.toString())
        if (searchTgBtn6.isChecked) habitat.add(searchTgBtn6.text.toString())


        if (bugTgBtn4.isChecked) habitat.add(bugTgBtn4.text.toString())

        if (bugTgBtn6.isChecked) habitat.add(bugTgBtn6.text.toString())

        if (bugTgBtn9.isChecked) habitat.add(bugTgBtn9.text.toString())

        if (bugTgBtn14.isChecked) habitat.add(bugTgBtn14.text.toString())
        if (bugTgBtn15.isChecked) habitat.add(bugTgBtn15.text.toString())

        if (bugTgBtn18.isChecked) habitat.add(bugTgBtn18.text.toString())

        if (bugTgBtn22.isChecked) habitat.add(bugTgBtn22.text.toString())
        if (bugTgBtn23.isChecked) habitat.add(bugTgBtn23.text.toString())


        //초기 price 값
        min = 0

//            if (priceMinET.text.toString() == "") {
//            0
//        } else {
//            priceMinET.text.toString().toInt()
//        }
        max =  999999


//            if (priceMaxET.text.toString() == "") {
//            999999
//        } else {
//            priceMaxET.text.toString().toInt()
//        }

        //종류초기값
        val arrSort: ArrayList<String> = arrayListOf()
        if (fishRB.isChecked) sort = "Fish"
        if (bugRB.isChecked) sort = "Bug"
        if (sort == "Fish") arrSort.add("F")
        else if (sort == "Bug") arrSort.add("B")
        else {
            arrSort.add("F")
            arrSort.add("B")
        }

        //월 초기값
        if (month.size == 0) {
            for (i in 1..12) {
                val str: String = "" + i + "月"
                month.add(str)
            }
        }

        //서식지 초기값
        if (habitat.size == 0 && arrSort.size == 1 && arrSort.get(0) == "F") {
            habitat.add("崖の上")
            habitat.add("川")
            habitat.add("桟橋")
            habitat.add("池")
            habitat.add("河口")
            habitat.add("海")
        } else if (habitat.size == 0 && arrSort.size == 1 && arrSort.get(0) == "B") {
            habitat.add("カブ")
            habitat.add("ヤシの木")
            habitat.add("住人")
            habitat.add("切り株")
            habitat.add("土の中")
            habitat.add("岩")
            habitat.add("岩場")
            habitat.add("川の近くの空中")
            habitat.add("木")
            habitat.add("木、ヤシの木")
            habitat.add("木の根元")
            habitat.add("木揺")
            habitat.add("砂浜")
            habitat.add("空中")
            habitat.add("花")
            habitat.add("花の近く")
            habitat.add("街灯")
            habitat.add("長靴、缶、タイヤ、腐ったカブ")
            habitat.add("陸")
            habitat.add("雨の日の切り株、岩")
            habitat.add("雪玉")
            habitat.add("池")
            habitat.add("川")
            habitat.add("木、ヤシの木")
            habitat.add("ヤシの木、木")
        } else if (habitat.size == 0 && arrSort.size == 2) {
            habitat.add("崖の上")
            habitat.add("川")
            habitat.add("桟橋")
            habitat.add("池")
            habitat.add("河口")
            habitat.add("海")
            habitat.add("カブ")
            habitat.add("ヤシの木")
            habitat.add("住人")
            habitat.add("切り株")
            habitat.add("土の中")
            habitat.add("岩")
            habitat.add("岩場")
            habitat.add("川の近くの空中")
            habitat.add("木")
            habitat.add("木、ヤシの木")
            habitat.add("木の根元")
            habitat.add("木揺")
            habitat.add("砂浜")
            habitat.add("空中")
            habitat.add("花")
            habitat.add("花の近く")
            habitat.add("街灯")
            habitat.add("長靴、缶、タイヤ、腐ったカブ")
            habitat.add("陸")
            habitat.add("雨の日の切り株、岩")
            habitat.add("雪玉")
            habitat.add("池")
            habitat.add("川")
            habitat.add("木、ヤシの木")
            habitat.add("ヤシの木、木")
        }

        //1차 검색
        val testList = testdb.animalDao().select("%" + name + "%", arrSort, min, max, habitat)
        Log.d("list", testList.size.toString())

        //월 배열화 후 검색
        val nlist = mutableSetOf<Int>()
        val alist = arrayListOf<AnimalVO>()
        val mlist = month.map { it.replace("月", "").toInt() }

        testList.forEach {
            val arr = it.nm!!.replace("[", "").replace("]", "").split(", ")
            val ints = arr.map { it.toInt() }
            for (i in mlist) {
                if (ints.indexOf(i) >= 0) nlist.add(it.aid)
            }
        }
        nlist.forEach {
            alist.add(testdb.animalDao().selectId(it))
        }

        //시간 배열화 후 검색
        val ns = "0"
//        minTime.text.toString()
        var n = 0
        if (ns != "") n = ns.toInt()
        val ms = "24"
//        maxTime.text.toString()
        var m = 24
        if (ms != "") m = ms.toInt()
        val rarr: ArrayList<Int> = arrayListOf()
        val aarr = mutableSetOf<AnimalVO>()
        for (i in n..m) {
            rarr.add(i)
        }
        alist.forEach {
            val narr = it.time!!.replace("[", "").replace("]", "").split(", ").map { it.toInt() }
            for (i in rarr) {
                if (narr.indexOf(i) >= 0) {
                    aarr.add(testdb.animalDao().selectId(it.aid))
                }
            }
        }

        var str = ""
        aarr.forEach {
            str += "[ " + it.aid + ", " + it.name + ", " + it.sort + ", " + it.price + " ]" + "\n"
        }

        Log.d(
            "kw",
            name + arrSort.toString() + min.toString() + max.toString() + mlist + habitat + rarr
        )
        Log.d("total", aarr.size.toString())
        val tmp = "show"
        val id = this.getResources().getIdentifier(tmp, "drawable", this.getPackageName())
        Log.d("id", id.toString())


        return aarr
    }

    fun setArr(values: ArrayList<String>): String? {
        val arr = JSONArray()
        values.forEach {
            arr.put(it)
        }
        return arr.toString()
    }

    fun getArr(key: String): List<String> {
        var json: String? = ""
        val list: List<String>
        if (key == "month") {
            json = App.prefs.smonth
        } else if (key == "habitant") {
            json = App.prefs.shabit
        }
        // Gradle -> implementation 'com.google.code.gson:gson:2.8.5
        val gson = GsonBuilder().create()
        list = gson.fromJson(json, Array<String>::class.java).toList()
        return list
    }
}

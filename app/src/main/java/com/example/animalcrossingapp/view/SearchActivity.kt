package com.example.animalcrossingapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.room.Room
import com.example.animalcrossingapp.R
import com.example.animalcrossingapp.dao.TestDBHelper
import com.example.animalcrossingapp.room.AppDatabase
import com.example.animalcrossingapp.roomtest.Animal
import com.example.animalcrossingapp.roomtest.AnimalDB
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity() {

    lateinit var testDBHelper: TestDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val db = Room.databaseBuilder(
            applicationContext,
            AnimalDB::class.java, "animal.db"
        ).allowMainThreadQueries().build()

        testBtn.setOnClickListener {
            db.anmalDao().insert(
                Animal(name = "test", price = 100, catch_flag = "0", sort = "f", month = "1月", habitant = "海")
            )
            testTV.setText(
                db.anmalDao().selectAll().toString()
            )
        }


        testDBHelper = TestDBHelper(this)

        var view: View
        FS.visibility = View.VISIBLE
        BS.visibility = View.INVISIBLE

        var sort:String = ""
        sortRG.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                val radioBtn: RadioButton = findViewById(checkedId)
                sort = radioBtn.text.toString()
                FS.visibility = View.INVISIBLE
                if (sort == "Fish") {
                    FS.visibility = View.VISIBLE
                    BS.visibility = View.INVISIBLE
                } else if (sort == "Bug") {
                    BS.visibility = View.VISIBLE
                    FS.visibility = View.INVISIBLE
                } else {
                    FS.visibility = View.INVISIBLE
                    BS.visibility = View.INVISIBLE
                }
            }
        )


        searchBTN.setOnClickListener {
            var result: String = ""
            var month: ArrayList<String> = arrayListOf()
            if(monthCB1.isChecked) month.add(monthCB1.text.toString())
            if(monthCB2.isChecked) month.add(monthCB2.text.toString())
            if(monthCB3.isChecked) month.add(monthCB3.text.toString())
            if(monthCB4.isChecked) month.add(monthCB4.text.toString())
            if(monthCB5.isChecked) month.add(monthCB5.text.toString())
            if(monthCB6.isChecked) month.add(monthCB6.text.toString())
            if(monthCB7.isChecked) month.add(monthCB7.text.toString())
            if(monthCB8.isChecked) month.add(monthCB8.text.toString())
            if(monthCB9.isChecked) month.add(monthCB9.text.toString())
            if(monthCB10.isChecked) month.add(monthCB10.text.toString())
            if(monthCB11.isChecked) month.add(monthCB11.text.toString())
            if(monthCB12.isChecked) month.add(monthCB12.text.toString())
            var resultH: String = ""
            var habitant: ArrayList<String> = arrayListOf()
            if(searchTgBtn1.isChecked) habitant.add(searchTgBtn1.text.toString())
            if(searchTgBtn2.isChecked) habitant.add(searchTgBtn2.text.toString())
            if(searchTgBtn3.isChecked) habitant.add(searchTgBtn3.text.toString())
            if(searchTgBtn4.isChecked) habitant.add(searchTgBtn4.text.toString())
            if(searchTgBtn5.isChecked) habitant.add(searchTgBtn5.text.toString())
            if(searchTgBtn6.isChecked) habitant.add(searchTgBtn6.text.toString())
            var min:Int = 0
            var max:Int = 0
            if(priceMinET.text.toString() == "") {
                min = 0
            } else {
                min = priceMinET.text.toString().toInt()
            }
            if(priceMaxET.text.toString() == "") {
                max = 9999
            } else {
                max = priceMaxET.text.toString().toInt()
            }

            var name = searchET.text.toString()

            var price: ArrayList<Int> = arrayListOf()
            price.add(min)
            price.add(max)

            if(name == "") name = "%%"

            if(fishRB.isChecked) sort = "Fish"
            if(bugRB.isChecked) sort = "Bug"
            if(sort == "Fish") sort = "f"
            else if (sort == "Bug") sort = "b"
            else sort = "%%"

            if(month.size == 0) {
                for (i in 1..12){
                    var str:String ="" + i + "月"
                    month.add(str)
                }
            }

            if(habitant.size == 0){
                habitant.add("崖の上")
                habitant.add("川")
                habitant.add("桟橋")
                habitant.add("池")
                habitant.add("河口")
                habitant.add("海")
            }

            //var list: ArrayList<Any> = testDBHelper.readTest(name, price, sort, month, habitant)
            var roomlist: List<Animal> = db.anmalDao().select(name, min, max, sort, month, habitant)
            //Log.d("SearchActivity", list.size.toString())
            testTV.setText(
                name + "\t"
                + price + "\t"
                + sort + "\t"
                + month + "\t"
                + habitant + "\n"
                + roomlist
            )


        }

    }
}

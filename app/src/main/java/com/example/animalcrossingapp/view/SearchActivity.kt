package com.example.animalcrossingapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import com.example.animalcrossingapp.R
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        var sort:String = ""
        sortRG.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                val radioBtn: RadioButton = findViewById(checkedId)
                sort = radioBtn.text.toString()
            }
        )


        searchBTN.setOnClickListener {
            var result: String = ""
            if(monthCB1.isChecked) result += monthCB1.text.toString()
            if(monthCB2.isChecked) result += monthCB2.text.toString()
            if(monthCB3.isChecked) result += monthCB3.text.toString()
            if(monthCB4.isChecked) result += monthCB4.text.toString()
            if(monthCB5.isChecked) result += monthCB5.text.toString()
            if(monthCB6.isChecked) result += monthCB6.text.toString()
            if(monthCB7.isChecked) result += monthCB7.text.toString()
            if(monthCB8.isChecked) result += monthCB8.text.toString()
            if(monthCB9.isChecked) result += monthCB9.text.toString()
            if(monthCB10.isChecked) result += monthCB10.text.toString()
            if(monthCB11.isChecked) result += monthCB11.text.toString()
            if(monthCB12.isChecked) result += monthCB12.text.toString()
            var resultH: String = ""
            if(searchTgBtn1.isChecked) resultH += searchTgBtn1.text.toString()
            if(searchTgBtn2.isChecked) resultH += searchTgBtn2.text.toString()
            if(searchTgBtn3.isChecked) resultH += searchTgBtn3.text.toString()
            if(searchTgBtn4.isChecked) resultH += searchTgBtn4.text.toString()
            if(searchTgBtn5.isChecked) resultH += searchTgBtn5.text.toString()
            if(searchTgBtn6.isChecked) resultH += searchTgBtn6.text.toString()
            testTV.setText(
                searchET.text.toString() + "\n"
                + sort + "\n"
                + result + "\n"
                + resultH
            )
        }

    }
}

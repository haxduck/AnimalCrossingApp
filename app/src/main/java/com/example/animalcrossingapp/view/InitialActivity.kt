package com.example.animalcrossingapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.example.animalcrossingapp.R
import com.example.animalcrossingapp.controller.App
import kotlinx.android.synthetic.main.activity_initial.*

class InitialActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_initial)

        TextView.setText("구동횟수유무 : " + App.prefs.initialFlag + "\n" +
                         "반구설정 : " + App.prefs.hemisphere
        )

        // 반구설정
        var hemi: String = ""
        radioGroup.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                val radio: RadioButton = findViewById(checkedId)
                Toast.makeText(applicationContext, " On checked change :" +
                    " ${radio.text}",
                    Toast.LENGTH_LONG).show()
                hemi = radio.text.toString()
            }
        )

        confBtn.setOnClickListener {
            App.prefs.initialFlag = "1"
            App.prefs.hemisphere = hemi
            val nextIntent = Intent(this, MainActivity::class.java)
            startActivity(nextIntent)
        }
        //

        val iniflag = App.prefs.initialFlag
        if (iniflag == "") {
            Toast.makeText(this, "텍스트가 초기화되었습니다.", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "저장됨: $iniflag", Toast.LENGTH_LONG).show()
        }

    }


}

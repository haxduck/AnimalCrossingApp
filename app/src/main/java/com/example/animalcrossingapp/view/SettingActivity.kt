package com.example.animalcrossingapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.get
import com.example.animalcrossingapp.R
import com.example.animalcrossingapp.controller.App
import com.example.animalcrossingapp.language.SettingLocales
import kotlinx.android.synthetic.main.activity_initial.confBtn
import kotlinx.android.synthetic.main.activity_setting.*
import java.util.*

class SettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        var hemi = ""
        var mLanguageCode = ""

        hankyu.setOnCheckedChangeListener{group, isChecked ->
           if(minami.isChecked == true){
               hemi = "minami"
               Toast.makeText(this@SettingActivity, hemi.toString(), Toast.LENGTH_SHORT).show()
           }else if (kita.isChecked == true){
               hemi = "kita"
               Toast.makeText(this@SettingActivity, hemi.toString(), Toast.LENGTH_SHORT).show()
           }
       }
        language.setOnCheckedChangeListener{group, checkedId ->
            if(japanese.isChecked == true){
                mLanguageCode = "ja"
                Toast.makeText(this@SettingActivity, mLanguageCode.toString(), Toast.LENGTH_SHORT).show()
            }else if (korean.isChecked == true){
                mLanguageCode = "ko"
                Toast.makeText(this@SettingActivity, mLanguageCode.toString(), Toast.LENGTH_SHORT).show()
            }
        }

        confBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("msg", hemi.toString())
            //xml 저장
            App.prefs.hemisphere = hemi
            App.prefs.language = mLanguageCode
            Toast.makeText(this@SettingActivity, "環境設定が適用されました", Toast.LENGTH_SHORT).show()
            startActivity(intent)
        }
    }
}

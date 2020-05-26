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
import kotlin.math.min

class SettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        var hemi = App.prefs.hemisphere
        var mLanguageCode = App.prefs.language

        if(mLanguageCode == "ko"){
            Kankyo.setText("환경설정")
            confBtn.setText("적용")
            minami.setText("남반구")
            kita.setText("북반구")
        }else{
            Kankyo.setText("環境設定")
            confBtn.setText("適用")
            minami.setText("南半球")
            kita.setText("北半球")
        }

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
            if(mLanguageCode == "ko"){
                Toast.makeText(this@SettingActivity, "환경설정이 적용되었습니다", Toast.LENGTH_SHORT).show()
            }else {
                Toast.makeText(this@SettingActivity, "環境設定が適用されました", Toast.LENGTH_SHORT).show()
            }
            startActivity(intent)
        }
    }
}

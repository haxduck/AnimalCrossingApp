package com.example.animalcrossingapp.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.widget.Toast
import androidx.annotation.MenuRes
import androidx.core.view.get
import com.example.animalcrossingapp.R
import com.example.animalcrossingapp.controller.App
import com.example.animalcrossingapp.language.SettingLocales
import kotlinx.android.synthetic.main.activity_initial.confBtn
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.activity_setting.bottomBar
import me.ibrahimsn.lib.BottomBarItem
import me.ibrahimsn.lib.BottomBarParser
import java.util.*
import kotlin.math.min

class SettingActivity : AppCompatActivity() {
    private var activeItemIndex: Int = 0
    private var items = listOf<BottomBarItem>()

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
            val intent = Intent(this, SettingActivity::class.java)

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

        bottomBar.setActiveItem(2)
        bottomBar.onItemSelected = {
            if(it == 0){
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }

            if(it == 1){
                val intent = Intent(this, ListActivity::class.java)
                startActivity(intent)
            }
            if(it == 2){
                val intent = Intent(this, SettingActivity::class.java)
                startActivity(intent)
            }
        }

    }
    var lastTimeBackPressed : Long = 0
    override fun onBackPressed() {
        if(System.currentTimeMillis() - lastTimeBackPressed >= 1500){
            lastTimeBackPressed = System.currentTimeMillis()
           if(App.prefs.language =="ko") {
               Toast.makeText(this, "'뒤로' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_LONG).show()
           }else{
               Toast.makeText(this, "戻るボタンをもう一度押せば終了します。", Toast.LENGTH_LONG).show()
           }
        } else {
            finish()
        }
    }
}

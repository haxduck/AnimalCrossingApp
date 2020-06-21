package com.example.animalcrossingapp.view

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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

        var hemi = App.prefs.hemisphere
        var mLanguageCode = App.prefs.language

        /*if (mLanguageCode == "ko") {
            kankyo1.setText("초기설정")
            confBtn.setText("적용")
            minami.setText("남반구")
            kita.setText("북반구")
            textView1.text = "반구설정"
            textView2.text = "언어설정"
            japanese.text = "일본어"
            korean.text = "한국어"
            language.check(R.id.korean)
        } else {
            kankyo1.setText("初期設定")
            confBtn.setText("適用")
            minami.setText("南半球")
            kita.setText("北半球")
            japanese.text = "日本語"
            korean.text = "韓国語"
            language.check(R.id.japanese)
        }*/

        hemiSphere.setOnCheckedChangeListener { group, isChecked ->
            if (minami.isChecked == true) {
                minami.setTextColor(Color.BLACK)
                kita.setTextColor(Color.WHITE)
                hemi = "南半球"
            } else if (kita.isChecked == true) {
                minami.setTextColor(Color.WHITE)
                kita.setTextColor(Color.BLACK)
                hemi = "北半球"
            }
        }
        language.setOnCheckedChangeListener { group, checkedId ->
            if (japanese.isChecked == true) {
                korean.setTextColor(Color.WHITE)
                japanese.setTextColor(Color.BLACK)
                mLanguageCode = "ja"
                //
                Log.d("zzz","jj")
                kankyo1.setText("初期設定")
                confBtn.setText("適用")
                minami.setText("南半球")
                kita.setText("北半球")
                textView1.text = "半球設定"
                textView2.text = "言語設定"
                japanese.text = "日本語"
                korean.text = "韓国語"
                language.check(R.id.japanese)
            } else if (korean.isChecked == true) {
                japanese.setTextColor(Color.WHITE)
                korean.setTextColor(Color.BLACK)
                mLanguageCode = "ko"
                //
                Log.d("zzz","kk")
                kankyo1.setText("초기설정")
                confBtn.setText("적용")
                minami.setText("남반구")
                kita.setText("북반구")
                textView1.text = "반구설정"
                textView2.text = "언어설정"
                japanese.text = "일본어"
                korean.text = "한국어"
                language.check(R.id.korean)
            }

        }

        confBtn.setOnClickListener {

            //xml 저장
            App.prefs.hemisphere = hemi
            App.prefs.language = mLanguageCode
            if (mLanguageCode == "ko") {
                Toast.makeText(this, "환경설정이 적용되었습니다", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "環境設定が適用されました", Toast.LENGTH_SHORT).show()
            }
            App.prefs.initialFlag = "1"
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }


    }
}

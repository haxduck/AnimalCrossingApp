package com.example.animalcrossingapp.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.animalcrossingapp.R
import com.example.animalcrossingapp.language.LocaleHelper.setLocale

class LanguageActivity : AppCompatActivity() {
    private val mLanguageCode = "ko-rKR"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        findViewById<View>(R.id.btnChangeLangView).setOnClickListener {
            setLocale(this@LanguageActivity, mLanguageCode)

            recreate()
        }
    }
}
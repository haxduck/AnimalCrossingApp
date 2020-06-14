package com.example.animalcrossingapp.alarm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.animalcrossingapp.R
import kotlinx.android.synthetic.main.activity_test.*
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;
import com.example.animalcrossingapp.toolbar.FirstFragment
import it.mirko.rangeseekbar.OnRangeSeekBarListener
import it.mirko.rangeseekbar.RangeSeekBar
import kotlinx.android.synthetic.main.fragment_first.*

class TestActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        /*tbtn.setOnClickListener {
            val bundle: Bundle = Bundle()
            bundle.putString("test", tbtn.text.toString())
            val frg = FireMissilesDialogFragment()
            frg.arguments = bundle

            frg.show(supportFragmentManager, "FireMissileDiaolgFragment")
        }*/

    }



}
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
import it.mirko.rangeseekbar.OnRangeSeekBarListener
import it.mirko.rangeseekbar.RangeSeekBar

class TestActivity : AppCompatActivity(), OnRangeSeekBarListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        declareViews()
        setupViews()

    }

    fun declareViews() {

    }

    fun setupViews() {
        rangeSeekBar.startProgress = 20
        rangeSeekBar.endProgress = 80
        rangeSeekBar.setMinDifference(15)
        startValue.text = rangeSeekBar.startProgress.toString()
        endValue.text = rangeSeekBar.endProgress.toString()

        rangeSeekBar1.startProgress = 20
        rangeSeekBar1.endProgress = 80
        rangeSeekBar1.setMinDifference(15)
        startValue1.text = rangeSeekBar1.startProgress.toString()
        endValue1.text = rangeSeekBar1.endProgress.toString()

        Log.d("this", this.toString())

        rangeSeekBar.setOnRangeSeekBarListener(this)
        rangeSeekBar1.setOnRangeSeekBarListener(this)
        /*enable.setOnCheckedChangeListener(this)*/
    }

    override fun onRangeValues(rangeSeekBar: RangeSeekBar?, start: Int, end: Int) {
        if (rangeSeekBar!!.id == R.id.rangeSeekBar) {
            startValue.text = start.toString()
            endValue.text = end.toString()
        } else {
            startValue1.text = start.toString()
            endValue1.text = end.toString()
        }
    }

    /*override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        rangeSeekBar.isEnabled = isChecked
        seekBar.isEnabled = isChecked
    }*/


}
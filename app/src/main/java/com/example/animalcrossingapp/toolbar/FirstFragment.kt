package com.example.animalcrossingapp.toolbar

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import com.example.animalcrossingapp.R
import com.example.animalcrossingapp.controller.App
import com.example.animalcrossingapp.database.AnimalCrossingDB
import com.example.animalcrossingapp.database.Current
import com.example.animalcrossingapp.database.MainController
import com.example.animalcrossingapp.tab.BlankFragment
import com.example.animalcrossingapp.tab.DemoObjectFragment
import com.example.animalcrossingapp.view.GridviewAdapter
import com.example.animalcrossingapp.view.InitialActivity
import com.example.animalcrossingapp.view.MainActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.fragment_first.*
import kotlinx.android.synthetic.main.fragment_first.view.*
import kotlinx.android.synthetic.main.fragment_first.view.first_fragment
import java.util.*
import kotlin.collections.ArrayList

/**
 * A simple [Fragment] subclass.
 */
class FirstFragment : Fragment() {

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_first, container, false)
        val context: Context = requireContext()
        val db = AnimalCrossingDB.getInstance(context)!!
        val hemishpere = App.prefs.hemisphere!!
        val currentTime: String = Calendar.getInstance().get(Calendar.HOUR_OF_DAY).toString()
        val thisMonth = Calendar.getInstance().get(Calendar.MONTH) + 1
        val currentMonth = "" + thisMonth + "月"
        val realTimeList = db.animalCrossingDao().selectCurrentAnimal(hemishpere, currentTime, currentMonth)
        //첫 실행 판단 prefs.xml 저장
        val iniFlag = App.prefs.initialFlag
        Toast.makeText(context, "플래그: $iniFlag", Toast.LENGTH_LONG).show()

//        view.real_time_wrap.apply {
//            measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
//            clipToOutline= true
//        }


        if(iniFlag == "1") {
        } else {
            val nextIntent = Intent(context, InitialActivity::class.java)
            startActivity(nextIntent)
        }

        view.textView2.setText(
            MainController.currentTime()
        )

        view.real_time_wrap.setOnClickListener {
            val list = arrayListOf<Current>()
            val bundle: Bundle = Bundle()
            realTimeList.forEach {
                list.add(it)
            }
            bundle.putParcelableArrayList("list", list)
            bundle.putString("selector", "current")
            val frg = SecondFragment()
            frg.arguments = bundle
            (activity as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_fragment, frg).addToBackStack(null).commit()
            (activity as MainActivity).bottomBar.setActiveItem(1)
//            (activity as MainActivity).supportActionBar?.setTitle("Realtime List")
        }



        var imgArr = Array(realTimeList.size, {0})
        var idx = 0
        realTimeList.forEach {
            var id = it.information_code
            imgArr[idx] = this.getResources().getIdentifier(id, "drawable", context.getPackageName())
            idx++
        }
        val griviewAdapter = GridviewAdapter(context, imgArr)
        view.gridView1.adapter = griviewAdapter

        val catchFishes = db.animalCrossingDao().viewCatchFish().size
        val catchBugs = db.animalCrossingDao().viewCatchBug().size
        view.contentLoadingProgressBar.progress = catchFishes
        view.contentLoadingProgressBar2.progress = catchBugs
        view.textView3.text = "" + catchFishes + "/80"
        view.textView4.text = "" + catchBugs + "/80"
        view.frameLayout2.setOnClickListener {
            val list = arrayListOf<Current>()
            val plist = db.animalCrossingDao().selectArrange(hemishpere)
            val bundle: Bundle = bundleOf()
            plist.forEach{
                list.add(it)
            }
            bundle.putParcelableArrayList("list", list)
            bundle.putString("selector", "arrange")
            val frg = SecondFragment()
            frg.arguments = bundle
            (activity as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_fragment, frg).addToBackStack(null).commit()
            (activity as MainActivity).bottomBar.setActiveItem(1)
        }

        return view
    }


}

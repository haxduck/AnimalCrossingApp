package com.example.animalcrossingapp.toolbar

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.MutableLiveData
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.GridLayoutManager
import com.example.animalcrossingapp.R
import com.example.animalcrossingapp.alarm.AlarmReceiver
import com.example.animalcrossingapp.controller.App
import com.example.animalcrossingapp.controller.GridAdapter
import com.example.animalcrossingapp.database.AnimalCrossingDB
import com.example.animalcrossingapp.database.Current
import com.example.animalcrossingapp.view.GridviewAdapter
import com.example.animalcrossingapp.view.InitialActivity
import com.example.animalcrossingapp.view.MainActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_first.view.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class FirstFragment : Fragment() {

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

        if(iniFlag == "1") {
        } else {
            val nextIntent = Intent(context, InitialActivity::class.java)
            startActivity(nextIntent)
        }

        /*view.reBtn.setOnClickListener {
            this.refreshList()
        }*/
        //알람
        /*var alarmMgr: AlarmManager? = null
        var alarmIntent: PendingIntent
        alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmIntent = Intent(context, AlarmReceiver::class.java).let { intent ->
            PendingIntent.getBroadcast(context, 0, intent, 0)
        }
        val calendar: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, Calendar.HOUR_OF_DAY - 1)
            set(Calendar.MINUTE, 0)
        }
        alarmMgr?.setInexactRepeating(
            AlarmManager.RTC,
            calendar.timeInMillis,
            1000 * 60 * 1,
            alarmIntent
        )*/
        //

        /*view.textView2.setText(
            MainController.currentTime()
        )*/

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
        var list = arrayListOf<Current>()
        list.addAll(realTimeList)
        val griviewAdapter = GridviewAdapter(context, imgArr)
        view.gridView1.adapter = griviewAdapter
        /*view.gridView1.apply{
            layoutManager = GridLayoutManager(context, 5, GridLayoutManager.HORIZONTAL, false)
            adapter = GridAdapter(list, context) { animal -> }
        }*/

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

    fun refreshList() {
        val ft = fragmentManager?.beginTransaction()
        ft?.detach(this)?.attach(this)?.commit()
        Log.d("실행", this.toString())
    }


}

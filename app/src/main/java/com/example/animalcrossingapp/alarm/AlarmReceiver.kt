package com.example.animalcrossingapp.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.example.animalcrossingapp.R
import com.example.animalcrossingapp.toolbar.FirstFragment
import com.example.animalcrossingapp.toolbar.SecondFragment
import com.example.animalcrossingapp.view.MainActivity

class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        FirstFragment().refreshList()
        /*var i = Intent(context, MainActivity::class.java)
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context?.startActivity(i)*/
//        TODO("Not yet implemented")
    }
}
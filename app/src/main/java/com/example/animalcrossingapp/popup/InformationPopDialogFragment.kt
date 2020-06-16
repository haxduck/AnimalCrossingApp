package com.example.animalcrossingapp.popup

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.animalcrossingapp.R
import com.example.animalcrossingapp.controller.App
import com.example.animalcrossingapp.database.AnimalCrossingDB
import com.example.animalcrossingapp.database.Current
import com.example.animalcrossingapp.databinding.ListviewitemBinding
import com.example.animalcrossingapp.model.AnimalViewModel
import com.example.animalcrossingapp.model.KeywordViewModel
import com.example.animalcrossingapp.view.MainActivity
import kotlinx.android.synthetic.main.gridviewitem2.view.*
import kotlinx.android.synthetic.main.listviewitem.view.*
import java.lang.IllegalStateException

class InformationPopDialogFragment : DialogFragment() {

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val animal = arguments?.getParcelable<Current>("animal")
        val model: AnimalViewModel = ViewModelProvider(this).get(AnimalViewModel::class.java)
        val dao = AnimalCrossingDB.getInstance(requireContext())?.animalCrossingDao()!!
        val lang = App.prefs.language
        val month = if (lang == "ko") {
            animal?.month?.replace("月", "월")
        } else {
            animal?.month
        }
        val time = if (lang == "ko") {
            animal?.time?.replace("時", "시")
        } else {
            animal?.time
        }


//        val view = layoutInflater.inflate(R.layout.listviewitem, null)
        val binding= DataBindingUtil.inflate<ListviewitemBinding>(
            LayoutInflater.from(context), R.layout.listviewitem, null, false)
        val view = binding.root
       /* var button: Button = Button(context)
        button.text = if (lang == "ko") "잡기" else "捕える"
        button.setBackgroundResource(R.drawable.radiobutton_hankyusetting)
        view.grid_wrap.addView(button)*/
//        view.grid_wrap.background = context?.getDrawable(R.drawable.list_wrap)
        binding.current = animal
        binding.month = month
        binding.time = time
        binding.lang = lang

        var flag = animal?.flag
        var info = animal?.information_code!!
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setView(view)
                .setPositiveButton("Catch",
                DialogInterface.OnClickListener { dialog, id ->
                    if (flag == "1") {
                        dao.updateCatchFlag(info , "0")
//                        view.grid_wrap2.setBackgroundResource(R.drawable.grid_wrap2)
                        flag = "0"
                    } else {
                        dao.updateCatchFlag(info , "1")
//                        view.grid_wrap2.setBackgroundResource(R.drawable.grid_wrap2_r)
                        flag = "1"
                    }
                })
                .setNegativeButton("",
                DialogInterface.OnClickListener { dialog, id ->

                })
            builder.create()
        }?: throw IllegalStateException("Activity cannot be null")
    }

}
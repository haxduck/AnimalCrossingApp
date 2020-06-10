package com.example.animalcrossingapp.toolbar

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.animalcrossingapp.R
import com.example.animalcrossingapp.controller.App
import com.example.animalcrossingapp.view.MainActivity
import kotlinx.android.synthetic.main.activity_initial.confBtn
import kotlinx.android.synthetic.main.fragment_third.*
import kotlinx.android.synthetic.main.fragment_third.view.*

/**
 * A simple [Fragment] subclass.
 */
class ThirdFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_third, container, false)
        val context: Context = requireContext()

        var hemi = App.prefs.hemisphere
        var mLanguageCode = App.prefs.language

        if (mLanguageCode == "ko") {
            view.kankyo1.setText("환경설정")
            view.confBtn.setText("적용")
            view.minami.setText("남반구")
            view.kita.setText("북반구")
        } else {
            view.kankyo1.setText("環境設定")
            view.confBtn.setText("適用")
            view.minami.setText("南半球")
            view.kita.setText("北半球")
        }

        view.hankyu.setOnCheckedChangeListener { group, isChecked ->
            if (minami.isChecked == true) {
                hemi = "南半球"
            } else if (kita.isChecked == true) {
                hemi = "北半球"
            }
        }
        view.language.setOnCheckedChangeListener { group, checkedId ->
            if (japanese.isChecked == true) {
                mLanguageCode = "ja"
            } else if (korean.isChecked == true) {
                mLanguageCode = "ko"
            }
        }

        view.confBtn.setOnClickListener {

            //xml 저장
            App.prefs.hemisphere = hemi
            App.prefs.language = mLanguageCode
            if (mLanguageCode == "ko") {
                Toast.makeText(context, "환경설정이 적용되었습니다", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "環境設定が適用されました", Toast.LENGTH_SHORT).show()
            }

            val frg = ThirdFragment()
            (activity as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_fragment, frg).addToBackStack(null).commit()


        }

//        bottomBar.setActiveItem(2)
//        bottomBar.onItemSelected = {
//            if(it == 0){
//                val intent = Intent(this, MainActivity::class.java)
//                startActivity(intent)
//            }
//
//            if(it == 1){
//                val intent = Intent(this, ListActivity::class.java)
//                startActivity(intent)
//            }
//            if(it == 2){
//                val intent = Intent(this, SettingActivity::class.java)
//                startActivity(intent)
//            }
//        }
//
//    }
        //두번클릭시 앱종료
//    var lastTimeBackPressed : Long = 0
//    override fun onBackPressed() {
//        if(System.currentTimeMillis() - lastTimeBackPressed >= 1500){
//            lastTimeBackPressed = System.currentTimeMillis()
//            if(App.prefs.language =="ko") {
//                Toast.makeText(this, "'뒤로' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_LONG).show()
//            }else{
//                Toast.makeText(this, "戻るボタンをもう一度押せば終了します。", Toast.LENGTH_LONG).show()
//            }
//        } else {
//            finish()
//        }
//
        return view
    }



}

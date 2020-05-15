package com.example.animalcrossingapp.dao

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.animalcrossingapp.R
import android.widget.TextView
import com.example.animalcrossingapp.vo.AllVO
import com.example.animalcrossingapp.vo.FishVO
import kotlinx.android.synthetic.main.activity_db.*

class db : AppCompatActivity() {

    lateinit var usersDBHelper : UsersDBHelper
    lateinit var fishDBHelper: FishDBHelper
    lateinit var allDBHelper: AllDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_db)

        usersDBHelper = UsersDBHelper(this)
        fishDBHelper = FishDBHelper(this)
        allDBHelper = AllDBHelper(this)
    }

    fun addUser(v:View){
        var userid = this.edittext_userid.text.toString()
        var name = this.edittext_name.text.toString()
        var age = this.edittext_age.text.toString()
        var result = usersDBHelper.insertUser(UserModel(userid = userid,name = name,age = age))
        //clear all edittext s
        this.edittext_age.setText("")
        this.edittext_name.setText("")
        this.edittext_userid.setText("")
        this.textview_result.text = "Added user : "+result
        this.ll_entries.removeAllViews()
    }

    fun deleteUser(v:View){
        var userid = this.edittext_userid.text.toString()
        val result = usersDBHelper.deleteUser(userid)
        this.textview_result.text = "Deleted user : "+result
        this.ll_entries.removeAllViews()
    }

    fun showAllUsers(v:View){
        allDBHelper.insertAll(AllVO("fish1",100, "c","f"))
        allDBHelper.insertAll(AllVO("fish2",200, "","f"))
        allDBHelper.insertAll(AllVO("fish3",300, "c","f"))
        allDBHelper.insertAll(AllVO("bug1",400, "","b"))
        allDBHelper.insertAll(AllVO("bug2",500, "c","b"))
        allDBHelper.insertAll(AllVO("bug3",600, "","b"))
        allDBHelper.insertAll(AllVO("fish4",700, "","f"))
        allDBHelper.insertAll(AllVO("bug4",800, "c","b"))
        allDBHelper.insertAll(AllVO("fish5",900, "","f"))

        var users = usersDBHelper.readAllUsers()
        this.ll_entries.removeAllViews()
        users.forEach {
            var tv_user = TextView(this)
            tv_user.textSize = 30F
            tv_user.text = it.name.toString() + " - " + it.age.toString()
            this.ll_entries.addView(tv_user)
        }
        this.textview_result.text = "Fetched " + users.size + " users"
    }
}

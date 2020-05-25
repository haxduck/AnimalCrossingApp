package com.example.animalcrossingapp.dao

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.animalcrossingapp.R
import android.widget.TextView
import com.example.animalcrossingapp.vo.BugVO
import com.example.animalcrossingapp.vo.FishVO
import kotlinx.android.synthetic.main.activity_db.*

class db : AppCompatActivity() {

    lateinit var usersDBHelper : UsersDBHelper
    lateinit var fishDBHelper: FishDBHelper
    lateinit var bugDBHelper: BugDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_db)

        usersDBHelper = UsersDBHelper(this)
        fishDBHelper = FishDBHelper(this)
        bugDBHelper = BugDBHelper(this)
    }

    fun addUser(v:View){
        fishDBHelper.insertFish(FishVO("fish1", 100, "c", ""))
        bugDBHelper.insertBug(BugVO("bb1", 100, "1", ""))

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
        var fishes = fishDBHelper.readAllFishes()
        var bugs =bugDBHelper.readAllBugs()
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

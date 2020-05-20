package com.example.animalcrossingapp.room

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import com.example.animalcrossingapp.R
import kotlinx.android.synthetic.main.activity_room_test.*

class RoomTestActivity : AppCompatActivity() {
    private var userDb: AppDatabase? = null
    private var userList = listOf<User>()
    private var user:User = User()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_test)

        userDb = AppDatabase.getInstance(this)

        addBtn.setOnClickListener {
            user.firstName = firstNameET.text.toString()
            user.lastName = lastNameET.text.toString()
            userDb?.userDao()?.insert(user)
            userList = userDb?.userDao()?.getAll()!!
            roomTV.setText(userList.toString())
        }

        deleteBtn.setOnClickListener {
            user.uid = uidET.text.toString().toInt()
            userDb?.userDao()?.delete(user)
            userList = userDb?.userDao()?.getAll()!!
            roomTV.setText(userList.toString())
        }

        updateBtn.setOnClickListener {
            user.uid = uidET.text.toString().toInt()
            user.firstName = firstNameET.text.toString()
            user.lastName = lastNameET.text.toString()
            userDb?.userDao()?.update(user)
            userList = userDb?.userDao()?.getAll()!!
            roomTV.setText(userList.toString())
        }

        selectBtn.setOnClickListener {
            /*var list: IntArray = intArrayOf(1,2,3,4)
            userList = userDb?.userDao()?.loadAllByIds(list)!!*/

            user = userDb?.userDao()?.findByName(firstNameET.text.toString(), lastNameET.text.toString())!!

            roomTV.setText(user.toString())
        }

        selectAllBtn.setOnClickListener {
            userList = userDb?.userDao()?.getAll()!!
            roomTV.setText(userList.toString())
        }

    }
}

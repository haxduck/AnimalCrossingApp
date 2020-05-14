package com.example.animalcrossingapp.controller

import android.content.Context
import android.content.SharedPreferences

class MySharedPreferences(context: Context) {

    val PREFS_FILENAME = "prefs"
    val PREF_KEY_MY_EDITTEXT = "myEditText"
    val PREF_KET_INITIAL_FLAG = "initialFlag"
    val PREF_KET_HEMISPHERE = "hemisphere"
    val PREF_KEY_LANGUAGE = "language"
    val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, 0)

    var myEditText: String?
        get() = prefs.getString(PREF_KEY_MY_EDITTEXT, "")
        set(value) = prefs.edit().putString(PREF_KEY_MY_EDITTEXT, value).apply()
    var initialFlag: String?
        get() = prefs.getString(PREF_KET_INITIAL_FLAG, "")
        set(value) = prefs.edit().putString(PREF_KET_INITIAL_FLAG, value).apply()
    var hemisphere: String?
        get() = prefs.getString(PREF_KET_HEMISPHERE, "")
        set(value) = prefs.edit().putString(PREF_KET_HEMISPHERE, value).apply()
    var language: String?
        get() = prefs.getString(PREF_KEY_LANGUAGE, "")
        set(value) = prefs.edit().putString(PREF_KEY_LANGUAGE, value).apply()

}
package com.example.animalcrossingapp.controller

import android.content.Context
import android.content.SharedPreferences
import kotlin.concurrent.timer

class MySharedPreferences(context: Context) {

    val PREFS_FILENAME = "prefs"
    val PREF_KEY_MY_EDITTEXT = "myEditText"
    val PREF_KET_INITIAL_FLAG = "initialFlag"
    val PREF_KET_HEMISPHERE = "hemi"
    val PREF_KET_LANGUAGE = "language"
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
        get() = prefs.getString(PREF_KET_LANGUAGE, "")
        set(value) = prefs.edit().putString(PREF_KET_LANGUAGE, value).apply()


    //검색저장용
    var sname: String?
        get() = prefs.getString("sname", "")
        set(value) = prefs.edit().putString("sname", value).apply()
    var ssort: String?
        get() = prefs.getString("ssort", "")
        set(value) = prefs.edit().putString("ssort", value).apply()
    var siprice: Int
        get() = prefs.getInt("siprice", 0)
        set(value) = prefs.edit().putInt("sprice", value).apply()
    var smprice: Int
        get() = prefs.getInt("smprice", 0)
        set(value) = prefs.edit().putInt("smprice", value).apply()
    var smonth: String?
        get() = prefs.getString("smonth", "")
        set(value) = prefs.edit().putString("smonth", value).apply()
    var shabit: String?
        get() = prefs.getString("shabit", "")
        set(value) = prefs.edit().putString("shabit", value).apply()




}
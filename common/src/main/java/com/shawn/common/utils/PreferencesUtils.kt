package com.shawn.common.utils

import android.content.Context

object PreferencesUtils {

    private const val LANG_KEY = "lang_key"

    fun getString(context: Context?, key: String): String? {
        context?.run {
            val pref = getSharedPreferences(LANG_KEY, Context.MODE_PRIVATE)
            return pref.getString(key, null)
        } ?: kotlin.run {
            return null
        }
    }

    fun saveString(context: Context?, key: String, value: String) {
        context?.run {
            val pref = getSharedPreferences(LANG_KEY, Context.MODE_PRIVATE)
            val editor = pref.edit()
            editor.putString(key, value).apply()
        }
    }


}
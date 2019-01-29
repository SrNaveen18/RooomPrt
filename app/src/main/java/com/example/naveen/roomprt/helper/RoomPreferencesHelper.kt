package com.example.naveen.roomprt.helper

import android.content.Context
import android.util.Log

class RoomPreferencesHelper {

    companion object {
        val KEY : String = "RoomPreference"
        fun getStringValue(context: Context?, key: String): String? {
            var user_id: String? = ""
            if (context != null && context.getSharedPreferences(KEY, Context.MODE_PRIVATE) != null) {
                val user_pref = context.getSharedPreferences(KEY, Context.MODE_PRIVATE)
                user_id = user_pref.getString(key, "")
            }
            return user_id
        }

        fun setStringValue(ctx: Context?, key: String, value: String) {
            if (ctx != null) {
                val edt = ctx.getSharedPreferences(KEY, Context.MODE_PRIVATE).edit()
                edt.putString(key, value)
                edt.commit()
            }
        }

        fun getBooleanValue(context: Context?, key: String): Boolean {
            var isVal = false
            if (context != null && context.getSharedPreferences(KEY, Context.MODE_PRIVATE) != null) {
                val user_pref = context.getSharedPreferences(KEY, Context.MODE_PRIVATE)
                isVal = user_pref.getBoolean(key, false)
            }
            return isVal
        }

        fun setBooleanValue(ctx: Context?, key: String, value: Boolean) {
            if (ctx != null) {
                val edt = ctx.getSharedPreferences(KEY, Context.MODE_PRIVATE).edit()
                edt.putBoolean(key, value)
                edt.commit()
            }
        }

        private fun clearPreferences(ctx: Context?) {
//        if (ctx != null) {
//            val edt = ctx.getSharedPreferences(KEY, Context.MODE_PRIVATE).edit()
//            edt.remove(KEY)
//            edt.commit()
//        }
        }

        fun getIntValue(context: Context?, key: String): Int {
            var value = 0
            if (context != null && context.getSharedPreferences(KEY, Context.MODE_PRIVATE) != null) {
                val user_pref = context.getSharedPreferences(KEY, Context.MODE_PRIVATE)
                value = user_pref.getInt(key, 0)
                Log.d("Prefs", "getStringValue() value = [$value]")
            }
            return value
        }

        fun setIntValue(ctx: Context?, key: String, value: Int) {
            if (ctx != null) {
                val edt = ctx.getSharedPreferences(KEY, Context.MODE_PRIVATE).edit()
                edt.putInt(key, value)
                Log.d("Prefs", "setStringValue() called with: key = [$key], value = [$value]")
                edt.commit()
            }
        }

        fun clearAllPreferences(context: Context) {
            val editor = context.getSharedPreferences(KEY, Context.MODE_PRIVATE).edit()
            editor.clear()
            editor.commit()
        }
    }
}
package c.bmartinez.yelpclone.utils

import android.content.Context
import android.content.SharedPreferences
import android.text.TextUtils
import androidx.core.text.TextUtilsCompat

class SharedPreferencesUtils {

    //Shared Preferences Files
    val COORDINATES_SPF = "COORDINATES_SPF"
    val LOCATION_PERMISSION_SPF = "LOCATION_PERMISSION_SPF"

    //Shared Preferences Key Names
    val COORDINATES_LAT = "COORDINATES_LAT"
    val COORDINATES_LONG = "COORDINATES_LONG"
    val LOCATION_GRANTED = "LOCATION_GRANTED"

    companion object {
        fun setStringPref(context: Context, spf: String, key: String, value: String): Boolean {
            val sharedPreferences: SharedPreferences? = context.getSharedPreferences(spf, Context.MODE_PRIVATE)
            if(sharedPreferences != null && !TextUtils.isEmpty(key)) {
                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                editor.clear()
                editor.putString(key, value)
                return editor.commit()
            }
            return false
        }

        fun getStringPref(context: Context, spf: String, key: String): String? {
            var value: String? = null
            val sharedPreferences: SharedPreferences? = context.getSharedPreferences(spf, Context.MODE_PRIVATE)
            if(sharedPreferences != null){
                value = sharedPreferences.getString(key, null)
            }
            return value
        }

        fun setIntegerPref(context: Context, spf: String, key: String, value: Int): Boolean {
            val sharedPreferences: SharedPreferences? = context.getSharedPreferences(spf, Context.MODE_PRIVATE)
            if(sharedPreferences != null && !TextUtils.isEmpty(key)) {
                val editor: SharedPreferences.Editor = sharedPreferences.edit().clear()
                editor.clear()
                editor.putInt(key, value)
                return editor.commit()
            }
            return false
        }

        fun getIntegerPref(context: Context, spf: String, key: String, defaultValue: Int): Int? {
            var value: Int? = null
            val sharedPreferences: SharedPreferences? = context.getSharedPreferences(spf, Context.MODE_PRIVATE)
            if(sharedPreferences != null){
                value = sharedPreferences.getInt(key, defaultValue)
            }
            return value
        }

        fun setFloatPref(context: Context, spf: String, key: String, value: Float): Boolean {
            val sharedPreferences: SharedPreferences? = context.getSharedPreferences(spf, Context.MODE_PRIVATE)
            if(sharedPreferences != null && !TextUtils.isEmpty(key)) {
                val editor: SharedPreferences.Editor = sharedPreferences.edit().clear()
                editor.clear()
                editor.putFloat(key, value)
                return editor.commit()
            }
            return false
        }

        fun getFloatPref(context: Context, spf: String, key: String, defaultValue: Float): Float? {
            var value: Float? = null
            val sharedPreferences: SharedPreferences? = context.getSharedPreferences(spf, Context.MODE_PRIVATE)
            if(sharedPreferences != null){
                value = sharedPreferences.getFloat(key, defaultValue)
            }
            return value
        }
    }
}
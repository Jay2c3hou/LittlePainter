package com.example.littlepainter.utils

import android.content.Context
import android.content.SharedPreferences
import java.lang.ref.WeakReference

class SPUtils private constructor() {
    private lateinit var weakContext: WeakReference<Context>
    private val sp: SharedPreferences by lazy {
        weakContext.get()!!.getSharedPreferences(Constants.SharedFileName, Context.MODE_PRIVATE)
    }

    companion object {
        private var instance: SPUtils? = null
        fun defaultUtils(context: Context): SPUtils {
            return instance ?: SPUtils().also {
                instance = it
                it.weakContext = WeakReference(context)
            }
        }
    }

    var isFirst: Boolean = true
        set(value) {
            field = value
            sp.edit().also {
                it.putBoolean(Constants.IsFirstKey, value)
                it.apply()
            }
        }
        get() {
            return sp.getBoolean(Constants.IsFirstKey, true)
        }
}
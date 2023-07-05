package com.task.foody.ui.pref

import android.content.Context

object FoodyPref {

    private fun getPref(context: Context) =
        context.getSharedPreferences("foody",Context.MODE_PRIVATE)


    fun saveAccountId(id: Long, context: Context) {
        getPref(context).edit().putLong("accountId", id).apply()
    }

    fun getAccountId(context: Context) =
        getPref(context).getLong("accountId", 0)

    fun saveAccountType(type: String, context: Context){
        getPref(context).edit().putString("accountType", type).apply()
    }

    fun getAccountType(context: Context): String? {
        return getPref(context).getString("accountType", null)
    }

}
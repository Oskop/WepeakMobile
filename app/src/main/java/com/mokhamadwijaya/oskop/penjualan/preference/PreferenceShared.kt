package com.mokhamadwijaya.oskop.penjualan.preference

import android.content.Context
import android.content.SharedPreferences

object PreferenceShared {
    val USERNAME = "USERNAME"
    val EMAIL = "EMAIL"
    val PASSWORD = "PASSWORD"
    val ACCESS_TOKEN = "ACCESS_TOKEN"
    val REFRESH_TOKEN = "REFRESH_TOKEN"
    val TOKEN_TYPE = "TOKEN_TYPE"
    val LOGGED = "LOGGED"
    val OMSET = "OMSET"
    val PENAGIHAN = "PENAGIHAN"
    val JUMLAH_PESANAN = "JUMLAH_PESANAN"

    fun customPreference(context: Context, name: String): SharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE)

    inline fun SharedPreferences.editMe(operation: (SharedPreferences.Editor) -> Unit) {
        val editMe = edit()
        operation(editMe)
        editMe.apply()
    }

    var SharedPreferences.username
        get() = getString(USERNAME, "")
        set(value) { editMe { it.putString(USERNAME, value) } }

    var SharedPreferences.email
        get() = getString(EMAIL, "")
        set(value) { editMe { it.putString(EMAIL, value) } }

    var SharedPreferences.password
        get() = getString(PASSWORD, "")
        set(value) { editMe { it.putString(PASSWORD, value) } }

    var SharedPreferences.accesstoken
        get() = getString(ACCESS_TOKEN, "")
        set(value) { editMe { it.putString(ACCESS_TOKEN, value) } }

    var SharedPreferences.refreshtoken
        get() = getString(REFRESH_TOKEN, "")
        set(value) { editMe { it.putString(REFRESH_TOKEN, value) } }

    var SharedPreferences.tokentype
        get() = getString(TOKEN_TYPE, "")
        set(value) { editMe { it.putString(TOKEN_TYPE, value) } }

    var SharedPreferences.logged
        get() = getString(LOGGED, "")
        set(value) { editMe { it.putString(LOGGED, value) } }

    var SharedPreferences.omset
        get() = getString(OMSET, "")
        set(value) { editMe { it.putString(OMSET, value) } }

    var SharedPreferences.penagihan
        get() = getString(PENAGIHAN, "")
        set(value) { editMe { it.putString(PENAGIHAN, value) } }

    var SharedPreferences.jumlahpesanan
        get() = getString(JUMLAH_PESANAN, "")
        set(value) { editMe { it.putString(JUMLAH_PESANAN, value) } }

//    var SharedPreferences.clearValues
//        get() = { }
//        set(value) { editMe { it.clear() } }
}
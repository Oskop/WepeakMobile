package com.mokhamadwijaya.oskop.penjualan.retrofit

import com.mokhamadwijaya.oskop.penjualan.LoginActivity
import com.mokhamadwijaya.oskop.penjualan.model.DashboardResponse
import com.mokhamadwijaya.oskop.penjualan.model.LoginBody
import com.mokhamadwijaya.oskop.penjualan.model.LoginResponse
import com.mokhamadwijaya.oskop.penjualan.preference.PreferenceShared
import retrofit2.Call
import retrofit2.http.*

interface DashboardInterface {
    @GET("api/dashboard")
    fun getDashboardData(@HeaderMap headers: Map<String, String>): Call<DashboardResponse>

    @Headers("Accept: application/json")
    @POST("api/login")
    fun postLogin(@Body body: LoginBody): Call<LoginResponse>
}
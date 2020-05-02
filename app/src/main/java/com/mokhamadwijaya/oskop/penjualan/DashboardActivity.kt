package com.mokhamadwijaya.oskop.penjualan

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mokhamadwijaya.oskop.penjualan.model.DashboardResponse
import com.mokhamadwijaya.oskop.penjualan.preference.PreferenceShared
import com.mokhamadwijaya.oskop.penjualan.preference.PreferenceShared.accesstoken
import com.mokhamadwijaya.oskop.penjualan.preference.PreferenceShared.jumlahpesanan
import com.mokhamadwijaya.oskop.penjualan.preference.PreferenceShared.logged
import com.mokhamadwijaya.oskop.penjualan.preference.PreferenceShared.omset
import com.mokhamadwijaya.oskop.penjualan.preference.PreferenceShared.penagihan
import com.mokhamadwijaya.oskop.penjualan.preference.PreferenceShared.username
import com.mokhamadwijaya.oskop.penjualan.retrofit.DashboardInterface
import kotlinx.android.synthetic.main.activity_dashboard.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DashboardActivity : AppCompatActivity() {
    val CUSTOM_PREF_NAME = "User_data"

    override fun onCreate(savedInstanceState: Bundle?) {
        val prefs = PreferenceShared.customPreference(this, CUSTOM_PREF_NAME)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        supportActionBar?.hide()
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }

        if (prefs.penagihan == "") {
        val retrofit = Retrofit.Builder()
            .baseUrl(LoginActivity.BaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(DashboardInterface::class.java)
        val call = service.getDashboardData(getHeaderMap())
        call.enqueue(object : Callback<DashboardResponse> {
            override fun onResponse(
                call: Call<DashboardResponse>,
                response: Response<DashboardResponse>
            ) {
                var iss = false
                if (response.code() == 200) {
                    val resp = response.body()!!
                    val omset = "Rp " + resp.omset
                    val jumlahpesanan = resp.jumlahPesanan
                    val totalpesanan = "Rp " + resp.totalPesanan
                    prefs.username = resp.userName
                    prefs.jumlahpesanan = jumlahpesanan.toString()
                    prefs.omset = omset
                    prefs.penagihan = totalpesanan
                    totalPenjualan.setText(prefs.omset)
                    txtTotalPenagihan.setText(prefs.penagihan)
                    txtJumlahPesanan.setText(prefs.jumlahpesanan)
                } else iss = true

                if (iss) Toast.makeText(this@DashboardActivity
                    , response.code().toString() + " " + response.message(), Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<DashboardResponse>, t: Throwable) {
                Toast.makeText(this@DashboardActivity, t.message, Toast.LENGTH_LONG).show()
            }
        })
        } else {
            totalPenjualan.setText(prefs.omset)
            txtTotalPenagihan.setText(prefs.penagihan)
            txtJumlahPesanan.setText(prefs.jumlahpesanan)
        }



        btnProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
//            Toast.makeText(this,"Ye",Toast.LENGTH_LONG).show()
        }
    }

    private fun getHeaderMap(): Map<String, String> {
        val prefs = PreferenceShared.customPreference(this, CUSTOM_PREF_NAME)
        val headerMap = mutableMapOf<String, String>()
        headerMap["Authorization"] = "Bearer " + prefs.accesstoken
        headerMap["Accept"] = "application/json"
        return headerMap
    }
}

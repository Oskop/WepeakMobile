package com.mokhamadwijaya.oskop.penjualan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.mokhamadwijaya.oskop.penjualan.model.LoginBody
import com.mokhamadwijaya.oskop.penjualan.model.LoginResponse
import com.mokhamadwijaya.oskop.penjualan.preference.PreferenceShared
import com.mokhamadwijaya.oskop.penjualan.preference.PreferenceShared.accesstoken
import com.mokhamadwijaya.oskop.penjualan.preference.PreferenceShared.email
import com.mokhamadwijaya.oskop.penjualan.preference.PreferenceShared.logged
import com.mokhamadwijaya.oskop.penjualan.preference.PreferenceShared.password
import com.mokhamadwijaya.oskop.penjualan.preference.PreferenceShared.refreshtoken
import com.mokhamadwijaya.oskop.penjualan.preference.PreferenceShared.tokentype
import com.mokhamadwijaya.oskop.penjualan.retrofit.DashboardInterface
import kotlinx.android.synthetic.main.layout_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    val CUSTOM_PREF_NAME = "User_data"

    override fun onCreate(savedInstanceState: Bundle?) {
        val prefs = PreferenceShared.customPreference(this, CUSTOM_PREF_NAME)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)

        if (prefs.logged == "t")
            startActivity(Intent(this, DashboardActivity::class.java))

        progress.visibility = View.GONE

        btnLogin.setOnClickListener(this);btnClear.setOnClickListener(this)
        btnShow.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val prefs = PreferenceShared.customPreference(this, CUSTOM_PREF_NAME)
        when (v?.id) {
            R.id.btnLogin -> {
                prefs.email = edtEmail.text.toString()
//                prefs.password = edtPassword.text.toString()
                val retrofit = Retrofit.Builder()
                    .baseUrl(BaseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                val service = retrofit.create(DashboardInterface::class.java)
                val body = LoginBody(edtEmail.text.toString(),
                    edtPassword.text.toString())
                val call = service.postLogin(body)
                progress.visibility = View.VISIBLE
                btnLogin.visibility = View.GONE
                call.enqueue(object : Callback<LoginResponse> {
                    override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                        if (response.code() == 200) {
                            val loginResponse = response.body()!!
                            prefs.accesstoken = loginResponse.accessToken
                            prefs.refreshtoken = loginResponse.refreshToken
                            prefs.tokentype = loginResponse.tokenType
                            prefs.logged = "t"
//                            edtAccessToken.setText(prefs.accesstoken)
                            btnLogin.visibility = View.VISIBLE
                            val intent = Intent(this@LoginActivity, DashboardActivity::class.java)
                            startActivity(intent)
                        }
                        progress.visibility = View.GONE
                        Toast.makeText(this@LoginActivity, response.code().toString(), Toast.LENGTH_LONG).show()
                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        Toast.makeText(this@LoginActivity, t.message, Toast.LENGTH_LONG).show()
                        edtAccessToken.setText(t.message)
                        progress.visibility = View.GONE
                    }
                } )
            }
            R.id.btnClear -> { edtEmail.setText("");edtPassword.setText("") }
            R.id.btnShow -> { edtEmail.setText(prefs.email.toString())
                              edtPassword.setText(prefs.password) }
        }
    }

    companion object {
        var BaseUrl = "http://calm-temple-58425.herokuapp.com/"
    }
}



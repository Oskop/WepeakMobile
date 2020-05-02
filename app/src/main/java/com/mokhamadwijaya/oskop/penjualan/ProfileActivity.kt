package com.mokhamadwijaya.oskop.penjualan

import android.content.Intent
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.OnSuccessListener
import com.mokhamadwijaya.oskop.penjualan.preference.PreferenceShared
import com.mokhamadwijaya.oskop.penjualan.preference.PreferenceShared.email
import com.mokhamadwijaya.oskop.penjualan.preference.PreferenceShared.username
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.content_profile.*

class ProfileActivity : AppCompatActivity() {
    val CUSTOM_PREF_NAME = "User_data"
    var latitude: Double = 0.0; var longitude: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        val prefs = PreferenceShared.customPreference(this, CUSTOM_PREF_NAME)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        supportActionBar?.hide()
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        btnProfBack.setOnClickListener {
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
        }
        txtProfName.setText(prefs.username)
        txtProfEmail.setText(prefs.email)
        val mFusedLocation = LocationServices.getFusedLocationProviderClient(this)
        mFusedLocation.lastLocation.addOnSuccessListener(this, object :
            OnSuccessListener<Location> {
            override fun onSuccess(location: Location?) {
//                // Do it all with location
                Log.d("My Current location", "Lat : ${location?.latitude} Long : ${location?.longitude}")
//                // Display in Toast
                Toast.makeText(this@ProfileActivity,
                    "Lat : ${location?.latitude} Long : ${location?.longitude}",
                    Toast.LENGTH_LONG).show()
                latitude = location!!.latitude
                longitude = location.longitude
            }

        })
        btnGetLocation.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java)
            intent.putExtra("lat", latitude)
            intent.putExtra("long", longitude)
            startActivity(intent)
        }
    }
}

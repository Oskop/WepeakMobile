package com.mokhamadwijaya.oskop.penjualan


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    var lat: Double = 0.0; var long: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        val map: SupportMapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        map.getMapAsync(this)

        lat = intent.getDoubleExtra("lat", 0.0)
        long = intent.getDoubleExtra("long", 0.0)
    }

    override fun onMapReady(map: GoogleMap?) {
        val latlong = LatLng(lat, long)
        val update = CameraUpdateFactory.newLatLng(latlong)
        val zoom = CameraUpdateFactory.zoomTo(17f)
        map?.moveCamera(update)
        map?.animateCamera(zoom)
        map?.addMarker(MarkerOptions().position(latlong)
            .title("Depot KanGen Water").snippet("Alamat Depot"))
    }
}

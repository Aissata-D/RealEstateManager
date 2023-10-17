package com.sitadigi.realestatemanager.ui


import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.sitadigi.realestatemanager.BuildConfig
import com.sitadigi.realestatemanager.R


class DetailsPropertyActivity : AppCompatActivity(), OnMapReadyCallback {
   // lateinit var imgDetailMap: ImageView
   // lateinit var  mapFragment:SupportMapFragment?
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_property)
      //  imgDetailMap = findViewById(R.id.map_detail_imv)

        // Get the SupportMapFragment and request notification when the map is ready to be used.
        // Get the SupportMapFragment and request notification when the map is ready to be used.
        // Get the SupportMapFragment and request notification when the map is ready to be used.

         val mapFragment = supportFragmentManager.findFragmentById(R.id.detail_map_fragment) as? SupportMapFragment
        mapFragment?.getMapAsync(this)


       // configureDetailView()
    }
    override fun onMapReady(map: GoogleMap) {
        val sydney = LatLng(45.763420, 4.834277)
        val cameraPosition = CameraPosition.Builder()
            .target(sydney)
            .zoom(15f)
            //.bearing(-30f)
            //.tilt(15f)
            .build()


        map.addMarker(
            MarkerOptions()
                .position(sydney)
                .title("Property location")
        )
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
       // map.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        //map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15f))
       // mapsetMaxZoomPreference()

     /*   map.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                sydney, 15f
            )
        )
        map.addMarker(
            MarkerOptions()
                .position(sydney)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
        )*/

    }

    fun configureDetailView() {
        val GOOGLE_MAP_KEY = BuildConfig.GOOGLE_MAPS_API_KEY
        val urlPart1: String = "https://maps.googleapis.com/maps/api/staticmap?zoom=13&size=500x500&maptype=roadmap%20&markers=color:red%7Clabel:C%7C"
        val urlPart2: String = "40.718217,-73.998284"
        val urlPart3: String = "%20&key=$GOOGLE_MAP_KEY"
        val urlConcat = urlPart1 + urlPart2 + urlPart3
        //val urlConcat:String = "https://maps.googleapis.com/maps/api/staticmap?center=40.714728,-73.998672&zoom=12&size=400x400&maptype=roadmap&key=AIzaSyCRwilK4p9DLKw5ZH86oGSKb8MR8W5jaHE"
        //val urlConcat = urlConcat1
        //GLIDE TO SHOW IMAGE OF THE RESTAURANT
    /*    Glide.with(this)
                .load(getUrl(urlConcat))
                .apply(RequestOptions.noTransformation())
                .centerCrop()
               // .placeholder(R.drawable.img_property_placeholder)
                .into(imgDetailMap)

*/
    }

    // Build a url to get restaurant image
    fun getUrl(base: String?): Uri? {
        return Uri.parse(base)
    }
}
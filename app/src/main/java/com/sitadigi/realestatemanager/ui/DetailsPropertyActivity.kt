package com.sitadigi.realestatemanager.ui

import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sitadigi.realestatemanager.BuildConfig
import com.sitadigi.realestatemanager.R

class DetailsPropertyActivity : AppCompatActivity() {
    lateinit var imgDetailMap: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_property)

        imgDetailMap = findViewById(R.id.detail_map)
    }

    fun configureDetailView() {
        val GOOGLE_MAP_KEY = BuildConfig.GOOGLE_MAPS_API_KEY
        val urlPart1: String = "https://maps.googleapis.com/maps/api/staticmap?zoom=13&size=300x300&maptype=roadmap%20&markers=color:red%7Clabel:C%7C"
        val urlPart2: String = "40.718217,-73.998284"
        val urlPart3: String = "%20&key=$GOOGLE_MAP_KEY"
        val urlConcat = urlPart1 + urlPart2 + urlPart3

        //GLIDE TO SHOW IMAGE OF THE RESTAURANT
        Glide.with(this)
                .load(getUrl(urlConcat))
                .apply(RequestOptions.noTransformation())
                .centerCrop()
                //.placeholder()
                .into(imgDetailMap)


    }

    // Build a url to get restaurant image
    fun getUrl(base: String?): Uri? {
        return Uri.parse(base)
    }
}
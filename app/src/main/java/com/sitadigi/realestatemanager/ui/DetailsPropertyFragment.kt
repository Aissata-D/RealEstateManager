package com.sitadigi.realestatemanager.ui


import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapFragment
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.sitadigi.realestatemanager.BuildConfig
import com.sitadigi.realestatemanager.R


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DetailsPropertyFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailsPropertyFragment : Fragment(), OnMapReadyCallback {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    //lateinit var imgDetailMap: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
       val view= inflater.inflate(R.layout.fragment_details_property, container, false)
    //    imgDetailMap = view.findViewById(R.id.map_detail_imv)

       //val mapFragment1 = supportFragmentManager.findFragmentById(R.id.detail_map_fragment) as? SupportMapFragment
       // mapFragment?.getMapAsync(this)

        val mapFragment = fragmentManager?.findFragmentById(R.id.detail_map_fragment) as MapFragment?
        mapFragment?.getMapAsync(this)

        configureDetailView()
        return view
    }
    fun configureDetailView() {
        val GOOGLE_MAP_KEY = BuildConfig.GOOGLE_MAPS_API_KEY
        val urlPart1: String = "https://maps.googleapis.com/maps/api/staticmap?zoom=13&size=300x300&maptype=roadmap%20&markers=color:red%7Clabel:C%7C"
        val urlPart2: String = "40.718217,-73.998284"
        val urlPart3: String = "%20&key=$GOOGLE_MAP_KEY"
        val urlConcat = urlPart1 + urlPart2 + urlPart3

        //GLIDE TO SHOW IMAGE OF THE RESTAURANT
    /*    Glide.with(this)
                .load(getUrl(urlConcat))
                .apply(RequestOptions.noTransformation())
                .centerCrop()
                //.placeholder()
                .into(imgDetailMap)
            */

    }

    // Build a url to get restaurant image
    fun getUrl(base: String?): Uri? {
        return Uri.parse(base)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DetailsPropertyFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                DetailsPropertyFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }

    override fun onMapReady(map: GoogleMap?) {
        val sydney = LatLng(45.763420, 4.834277)
        val cameraPosition = CameraPosition.Builder()
            .target(sydney)
            .zoom(15f)
            //.bearing(-30f)
            //.tilt(15f)
            .build()


        map?.addMarker(
            MarkerOptions()
                .position(sydney)
                .title("Property location")
        )
        map?.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
}
package com.sitadigi.realestatemanager.ui


import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapFragment
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.textview.MaterialTextView
import com.sitadigi.realestatemanager.BuildConfig
import com.sitadigi.realestatemanager.R
import com.sitadigi.realestatemanager.dao.PictureDao
import com.sitadigi.realestatemanager.database.UserDatabase
import com.sitadigi.realestatemanager.model.Picture
import com.sitadigi.realestatemanager.utils.PropertyRecyclerViewCustom
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch


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
    lateinit var description_title_tv: TextView
    lateinit var description_tv: TextView
    lateinit var property_nb_room_detail_tv: MaterialTextView
    lateinit var property_surface_detail_tv: MaterialTextView
    lateinit var property_nb_bathroom_tv : MaterialTextView
    lateinit var property_nb_bedroom_tv: MaterialTextView
    lateinit var recyclerView : RecyclerView


    var description =""
    var numberOfRooms = 0
    var numberOfBathRooms = 0
    var numberOfBedRooms = 0
    var surface = 0
    var property_id =0

    val DESCRIPTION = "DESCRIPTION"
    val NUMBER_OF_ROOMS = "NUMBER_OF_ROOMS"
    val NUMBER_OF_BATH_ROOMS = "NUMBER_OF_BATH_ROOMS"
    val NUMBER_OF_BED_ROOMS = "NUMBER_OF_BED_ROOMS"
    val SURFACE = "SURFACE"
    var  listOfPhoto: ArrayList<String>? = null
    val PROPERTY_ID = "PROPERTY_ID"

    private lateinit var pictureDao: PictureDao
    lateinit var mPictureList : List<Picture>

    //lateinit var imgDetailMap: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // listOfPhoto = ArrayList()
        arguments?.let {

            surface = it.getInt(SURFACE)
            description = it.getString(DESCRIPTION, "NO DESCRIPTION")
            numberOfRooms = it.getInt(NUMBER_OF_ROOMS)
            numberOfBathRooms = it.getInt(NUMBER_OF_BATH_ROOMS)
            numberOfBedRooms = it.getInt(NUMBER_OF_BED_ROOMS)

            listOfPhoto = it.getStringArrayList("PHOTO")
            property_id = it.getInt(PROPERTY_ID)

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
       val view= inflater.inflate(R.layout.fragment_details_property, container, false)


       // val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        // Set callback on the fragment
        //mapFragment.getMapAsync(this)

        val mapFragment = childFragmentManager.findFragmentById(R.id.detail_map_fragment) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

        description_title_tv = view.findViewById(R.id.description_title_tv)
        property_nb_room_detail_tv = view.findViewById(R.id.property_nb_room_detail_tv)
        description_tv = view.findViewById(R.id.description_content_tv)
        property_surface_detail_tv = view.findViewById(R.id.property_surface_detail_tv)
        property_nb_bathroom_tv = view.findViewById(R.id.property_nb_bathroom_tv)
        property_nb_bedroom_tv = view.findViewById(R.id.property_nb_bedroom_tv)
        recyclerView = view.findViewById(R.id.recyclerview_detail)
        pictureDao = UserDatabase.getInstance(context)?.pictureDao!!
        val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())
        val custom = PropertyRecyclerViewCustom(this.requireContext(),null,0)
        recyclerView.layoutManager =  LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL,false)

        scope.launch {
            mPictureList = pictureDao.getListOfPictureByFkId(property_id)

        val adapter = DetailsPropertyImageRecylerviewAdapter(mPictureList,custom)
        // adapter.setClickListener(this)
        recyclerView.adapter = adapter
        }

     //   configureDetailView()
        return view
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
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }
    fun configureDetailView() {

        ////////////////////////////////////////////////////////////////////////////////
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


}
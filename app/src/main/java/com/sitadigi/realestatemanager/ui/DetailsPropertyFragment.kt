package com.sitadigi.realestatemanager.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.textview.MaterialTextView
import com.sitadigi.realestatemanager.R
import com.sitadigi.realestatemanager.dao.PictureDao
import com.sitadigi.realestatemanager.database.UserDatabase
import com.sitadigi.realestatemanager.model.Picture
import com.sitadigi.realestatemanager.model.Property
import com.sitadigi.realestatemanager.utils.PropertyRecyclerViewCustom
import com.sitadigi.realestatemanager.viewModel.PropertyViewModel
import com.sitadigi.realestatemanager.viewModelFactory.PropertyViewModelFactory
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
    private lateinit var propertyViewModel: PropertyViewModel
    lateinit var property:Property
     var propertyLatLng: LatLng? = null
   var propertyAdresseComplete : String? = null

    lateinit var description_title_tv: TextView
    lateinit var description_tv: TextView
    lateinit var propertyLocation: TextView
    lateinit var property_nb_room_detail_tv: MaterialTextView
    lateinit var property_surface_detail_tv: MaterialTextView
    lateinit var property_nb_bathroom_tv : MaterialTextView
    lateinit var property_nb_bedroom_tv: MaterialTextView
    lateinit var recyclerView : RecyclerView

    val POSITION = "POSITION"
    var mPosition = 0
    var description =""
    var numberOfRooms = 0
    var numberOfBathRooms = 0
    var numberOfBedRooms = 0
    var surface = 0
    var property_id = 0
    var property_locality :String? = "Locality"

    val DESCRIPTION = "DESCRIPTION"
    val NUMBER_OF_ROOMS = "NUMBER_OF_ROOMS"
    val NUMBER_OF_BATH_ROOMS = "NUMBER_OF_BATH_ROOMS"
    val NUMBER_OF_BED_ROOMS = "NUMBER_OF_BED_ROOMS"
    val SURFACE = "SURFACE"
    var listOfPhoto: ArrayList<String>? = null
    val PROPERTY_ID = "PROPERTY_ID"
    val PROPERTY_LOCALITY: String = "PROPERTY_LOCALITY"

    private lateinit var pictureDao: PictureDao
    private lateinit var propertyDao: PictureDao
    lateinit var mPictureList : List<Picture>
     var lat : Double = 0.0
     var lng : Double = 0.0

    //lateinit var imgDetailMap: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // listOfPhoto = ArrayList()
        arguments?.let {

            mPosition = it.getInt(POSITION)
            surface = it.getInt(SURFACE)
            description = it.getString(DESCRIPTION, "NO DESCRIPTION")
            numberOfRooms = it.getInt(NUMBER_OF_ROOMS)
            numberOfBathRooms = it.getInt(NUMBER_OF_BATH_ROOMS)
            numberOfBedRooms = it.getInt(NUMBER_OF_BED_ROOMS)

            listOfPhoto = it.getStringArrayList("PHOTO")
            property_id = it.getInt(PROPERTY_ID)
            property_locality = it.getString("PROPERTY_LOCALITY")

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

        val factory = PropertyViewModelFactory(this.activity as FragmentActivity)
        propertyViewModel = ViewModelProvider(this, factory).get(PropertyViewModel::class.java)

        description_title_tv = view.findViewById(R.id.description_title_tv)
        property_nb_room_detail_tv = view.findViewById(R.id.number_of_room_content_tv)
        description_tv = view.findViewById(R.id.description_content_tv)
        property_surface_detail_tv = view.findViewById(R.id.surface_content_tv)
        property_nb_bathroom_tv = view.findViewById(R.id.bathroom_content_tv)
        property_nb_bedroom_tv = view.findViewById(R.id.bedroom_content_tv)
        propertyLocation = view.findViewById(R.id.location_content_tv)
        recyclerView = view.findViewById(R.id.recyclerview_detail)
        pictureDao = UserDatabase.getInstance(context)?.pictureDao!!


        val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())
        val custom = PropertyRecyclerViewCustom(this.requireContext(),null,0)
        recyclerView.layoutManager =  LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL,false)

        scope.launch {

            property = propertyViewModel.getPropertyById(property_id)

            description_tv.setText(property.propertyDescription)
            property_nb_room_detail_tv.setText(property.propertyNumberOfRooms.toString())
            property_surface_detail_tv.setText(property.propertySurface.toString())
            property_nb_bathroom_tv.setText(property.propertyNumberOfBathRooms.toString())
            property_nb_bedroom_tv.setText(property.propertyNumberOfBedRooms.toString())
            propertyLocation.setText(property.propertyAdresseComplete)
            propertyLatLng = property.propertyLatLng


            mPictureList = pictureDao.getListOfPictureByFkId(property_id)
            val adapter = DetailsPropertyImageRecylerviewAdapter(mPictureList,custom)
            // adapter.setClickListener(this)
            recyclerView.adapter = adapter


            val propertyIdToTransfert = property_id
            val propertylocality:String? = property_locality

            // Use the Kotlin extension in the fragment-ktx artifact.
            setFragmentResult("requestKey", bundleOf("PROPERTY_ID" to propertyIdToTransfert,
                "PROPERTY_LOCALITY" to propertylocality ))
        }



       // configureDetailView()
        return view
    }
    override fun onMapReady(map: GoogleMap) {
        val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())
        scope.launch {
            property = propertyViewModel.getPropertyById(property_id)
            lat = property.propertyLatLng?.latitude?.toDouble() ?: 0.0
            lng = property.propertyLatLng?.longitude?.toDouble() ?: 0.0

            val propertyLatLngForMap = LatLng(lat, lng)
            val cameraPosition = CameraPosition.Builder()
                .target(propertyLatLngForMap)
                .zoom(15f)
                //.bearing(-30f)
                //.tilt(15f)
                .build()
            map.addMarker(
                MarkerOptions()
                    .position(propertyLatLngForMap)
                    .title("Property location")
            )
            map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        }
    }
    fun configureDetailView() {
        description_tv.setText(property?.propertyDescription)
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
package com.sitadigi.realestatemanager.ui
import android.app.Activity
import com.sitadigi.realestatemanager.R

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.RectangularBounds
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.android.material.chip.Chip
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import com.sitadigi.realestatemanager.BuildConfig

import com.sitadigi.realestatemanager.utils.AddPropertyUtils
import com.sitadigi.realestatemanager.viewModel.PictureViewModel
import com.sitadigi.realestatemanager.viewModel.PropertyViewModel
import com.sitadigi.realestatemanager.viewModelFactory.PictureViewModelFactory
import com.sitadigi.realestatemanager.viewModelFactory.PropertyViewModelFactory


import java.util.Date

/*

lateinit  var propertyLocalityUpdate: String

private lateinit var propertyViewModelUpdate: PropertyViewModel
private lateinit var pictureViewModelUpdate: PictureViewModel
var userEmailUpdate  = ""
private val OPEN_GALLERY_UP = "OPEN_GALLERY"
private val OPEN_CAMERA_UP = "OPEN_CAMERA"
lateinit var fabAddPictureFromGalleryUpdate: ExtendedFloatingActionButton
lateinit var fabTakePhotoWithCameraUpdate: FloatingActionButton
lateinit var btnAddPropertyUpdate: Button
lateinit var editPropertyTypeUpdate: TextInputEditText
lateinit var editPropertyPriceUpdate: TextInputEditText
lateinit var editPropertySurfaceUpdate: TextInputEditText
lateinit var editNumberOfBathRoomsUpdate: TextInputEditText
lateinit var editNumberOfBedRoomsUpdate: TextInputEditText
lateinit var editNumberOfRoomsUpdate: TextInputEditText
lateinit var editDescriptionUpdate: TextInputEditText
lateinit var tvAddressUpdateUpdate: MaterialTextView
lateinit var tvNearbyPointOfInterestUpdate: MaterialTextView
lateinit var tvEmailOfRealEstateAgentUpdate: MaterialTextView
lateinit var tvAddressUpdate: MaterialTextView
//lateinit var recyclerViewUpdate: RecyclerView
val propertyNearbyPointOfInterestsUpdate =  mutableListOf<String>()
lateinit var chipSchoolUpdate: Chip
lateinit var chipShopsUpdate: Chip
lateinit var chipParkUpdate: Chip
val AUTOCOMPLETE_REQUEST_CODE_UP = 3

var mRequestCodeUpdate = 0
var mResultCodeUpdate = 0
var  mDataUpdate: Intent? = null
*/

// Declare callback
//var mCallbackUpdate: UpdatePropertyFragment.interfaceClicOnButtonUpdateImage? = null

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
/**
 * A simple [Fragment] subclass.
 * Use the [UpdatePropertyFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
// TODO: Rename and change types of parameters
private var param1: String? = null
private var param2: String? = null
class UpdatePropertyFragment : Fragment() {
    lateinit var addPropertyUtils: AddPropertyUtils
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_update_property, container, false)
        /*

                val bundle = this.arguments// intent.extras

                if (bundle != null) {
                    val s = bundle["USER_EMAIL"] as String?
                    if (s != null) {
                        userEmailUpdate = s
                        Log.e("TAG", "onCreateAddActivity: email: ${userEmailUpdate} bundle: $bundle" )

                    }
                }

                chipSchoolUpdate = v.findViewById(R.id.chip_school_update)
                chipParkUpdate = v.findViewById(R.id.chip_park_update)
                chipShopsUpdate = v.findViewById(R.id.chip_shops_update)
                fabAddPictureFromGalleryUpdate = v.findViewById(R.id.fab_property_update)
                fabTakePhotoWithCameraUpdate = v.findViewById(R.id.fab_take_photo_update)
                btnAddPropertyUpdate = v.findViewById(R.id.property_btn_update_property)
                editPropertyTypeUpdate = v.findViewById(R.id.property_type_input_update)
                editPropertyPriceUpdate = v.findViewById(R.id.property_price_input_update)
                editPropertySurfaceUpdate = v.findViewById(R.id.property_surface_input_update)
                editNumberOfBathRoomsUpdate = v.findViewById(R.id.property_number_bathroom_input_update)
                editNumberOfBedRoomsUpdate = v.findViewById(R.id.property_number_bedroom_input_update)
                editNumberOfRoomsUpdate = v.findViewById(R.id.property_number_of_rooms_input_update)
                editDescriptionUpdate = v.findViewById(R.id.property_description_input_update)
                tvAddressUpdate = v.findViewById(R.id.tv_property_address_update)
                tvNearbyPointOfInterestUpdate = v.findViewById(R.id.property_nearby_points_of_interest_tv_update)
                tvEmailOfRealEstateAgentUpdate = v.findViewById(R.id.property_email_of_real_estate_agent_tv_update)
                val recyclerViewUpdate : RecyclerView = v.findViewById(R.id.recyclerview_update)

                val factory = PropertyViewModelFactory(this.activity as FragmentActivity)

                propertyViewModelUpdate = ViewModelProvider(this, factory).get(PropertyViewModel::class.java)

                val factoryPicture = PictureViewModelFactory(this.activity as FragmentActivity)
                pictureViewModelUpdate = ViewModelProvider(this, factoryPicture).get(PictureViewModel::class.java)

                tvEmailOfRealEstateAgentUpdate.text= "Email of agent : ${com.sitadigi.realestatemanager.ui.userEmailUpdate}"
                addPropertyUtils = AddPropertyUtils(
                    propertyViewModelUpdate,
                    pictureViewModelUpdate, this.activity as FragmentActivity, recyclerViewUpdate)

                addPropertyUtils.verifyStoragePermissions(this.activity)

                fabTakePhotoWithCameraUpdate.setOnClickListener {
                    addPropertyUtils.showAddImageDialog(OPEN_CAMERA_UP,this.context!!) }

                fabAddPictureFromGalleryUpdate.setOnClickListener {addPropertyUtils.showAddImageDialog(
                    OPEN_GALLERY_UP,this.context!!) }

                btnAddPropertyUpdate.setOnClickListener {
                    if( chipSchoolUpdate.isChecked) {
                        propertyNearbyPointOfInterestsUpdate.add("School")
                        for (y in propertyNearbyPointOfInterestsUpdate){
                            Log.e("TAG", "onCreate:ship " + y)

                        }}
                    if(chipParkUpdate.isChecked){
                        propertyNearbyPointOfInterestsUpdate.add("Park")
                        for (y in propertyNearbyPointOfInterestsUpdate){
                            Log.e("TAG", "onCreate:ship " + y)

                        }}
                    if(chipShopsUpdate.isChecked){
                        propertyNearbyPointOfInterestsUpdate.add("Shops")
                        for (y in propertyNearbyPointOfInterestsUpdate){
                            Log.e("TAG", "onCreate:ship " + y)

                        }}

                    addPropertyUtils.clickOnAddPropertyBtn(
                        editPropertyTypeUpdate,
                        editPropertyPriceUpdate,
                        editPropertySurfaceUpdate,
                        editNumberOfRoomsUpdate,
                        editNumberOfBedRoomsUpdate,
                        editNumberOfBathRoomsUpdate,
                        editDescriptionUpdate,
                        propertyLocalityUpdate,
                        propertyNearbyPointOfInterestsUpdate,
                        userEmailUpdate
                    )

                    Log.e("TAG", "onCreate: DATE : "+ Date() )
                }



                tvAddressUpdate.setOnClickListener {


                    // Initialize the SDK
                    val API_KEY: String = BuildConfig.GOOGLE_MAPS_API_KEY

                    if (!Places.isInitialized()) {
                        Places.initialize(this.context, API_KEY)
                    }
                    // Places.initialize(applicationContext, API_KEY)

                    // Create a new PlacesClient instance
                    val placesClient = Places.createClient(this.context)


                    // Create a new token for the autocomplete session. Pass this to FindAutocompletePredictionsRequest,
                    // and once again when the user makes a selection (for example when calling fetchPlace()).
                    val token = AutocompleteSessionToken.newInstance()

                    // Create a RectangularBounds object.
                    val bounds = RectangularBounds.newInstance(
                        LatLng(-33.880490, 151.184363),
                        LatLng(-33.858754, 151.229596)
                    )
                    // Use the builder to create a FindAutocompletePredictionsRequest.
                    val request =
                        FindAutocompletePredictionsRequest.builder()
                            // Call either setLocationBias() OR setLocationRestriction().
                            .setLocationBias(bounds)
                            //.setLocationRestriction(bounds)
                            .setOrigin(LatLng(-33.8749937, 151.2041382))
                            .setCountries("FR")
                            .setTypeFilter(TypeFilter.ADDRESS)
                            .setSessionToken(token)
                            //.setQuery(query)
                            .build()
                    placesClient.findAutocompletePredictions(request)
                        .addOnSuccessListener { response: FindAutocompletePredictionsResponse ->
                            for (prediction in response.autocompletePredictions) {
                                Log.e("TAG", prediction.placeId)
                                Log.e("TAG", prediction.getPrimaryText(null).toString())
                            }
                        }.addOnFailureListener { exception: Exception? ->
                            if (exception is ApiException) {
                                Log.e("TAG", "Place not found: " + exception.statusCode)
                            }
                        }

                    // Set the fields to specify which types of place data to
                    // return after the user has made a selection.
                    val fields = listOf(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.ADDRESS_COMPONENTS, Place.Field.LAT_LNG)

                    // Start the autocomplete intent.
                    val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                        .setCountry("FR")
                        .setTypeFilter(TypeFilter.ADDRESS)
                        .build(this.context)
                    startActivityForResult(intent,
                        AUTOCOMPLETE_REQUEST_CODE_UP
                    )
                }*/

        return v
    }
  /*  fun returnActivityResult(requestCode: Int, resultCode: Int, data: Intent?){

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // returnActivityResult(requestCode, resultCode, data)
        mRequestCodeUpdate = requestCode
        mResultCodeUpdate = resultCode
        mDataUpdate = data


        /////////////////////////////////////////////////////////////////////////////
      /*  if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    data?.let {
                        val place = Autocomplete.getPlaceFromIntent(data)
                        propertyLocality =  place.addressComponents.asList().get(2).name
                        Log.e("TAG", "Place: ${place.name}, ${place.id}," +
                                place.addressComponents.asList().get(2).name)
                        //Autocomplete
                        // place.latLng

                        tvAddressUpdate?.text = place.address
                        propertyAdresseComplete = place.address
                        propertyLatLng = place.latLng
                    }
                }
                AutocompleteActivity.RESULT_ERROR -> {
                    // TODO: Handle the error.
                    data?.let {
                        val status = Autocomplete.getStatusFromIntent(data)
                        Log.e("TAG ERROR", status.statusMessage ?: "")
                    }
                }
                Activity.RESULT_CANCELED -> {
                    Log.e("TAG", "onActivityResult: CANCELED" )
                    // The user canceled the operation.
                }
            }
            // mCallback?.OnButtonClickedListener(view)
            return
        }
        ////////////////////////////////////////////////////////////////////////////
        */

        // Call callback to close addMeeting fragment and open ListMeeting Fragment
        mCallbackUpdate?.OnButtonClickedListenerUpdate(view)
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    data?.let {
                        val place = Autocomplete.getPlaceFromIntent(data)
                        propertyLocalityUpdate =  place.addressComponents.asList().get(2).name
                        Log.e("TAG", "Place: ${place.name}, ${place.id}," +
                                place.addressComponents.asList().get(2).name)
                        tvAddressUpdate.text=place.address
                    }
                }
                AutocompleteActivity.RESULT_ERROR -> {
                    // TODO: Handle the error.
                    data?.let {
                        val status = Autocomplete.getStatusFromIntent(data)
                        Log.e("TAG ERROR", status.statusMessage ?: "")
                    }
                }
                Activity.RESULT_CANCELED -> {
                    Log.e("TAG", "onActivityResult: CANCELED" )
                    // The user canceled the operation.
                }
            }
            mCallback?.OnButtonClickedListener(view)
            return
        }
        addPropertyUtils.checkActivityResult(requestCode,resultCode, data)

        super.onActivityResult(requestCode, resultCode, data)
    }

    fun createCallbackToParentActivity() {
        //this.requestCode = mRequestCode
        //mResultCode = resultCode
        //mData = data
        mCallbackUpdate = activity as interfaceClicOnButtonUpdateImage?
        return returnActivityResult(
            mRequestCodeUpdate,
            mResultCodeUpdate,
            mDataUpdate
        )

        // mCallback = activity as fr.sitadigi.mareu.ui.AddMeetingFragment.onButtonAddReunionListener?
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        createCallbackToParentActivity()
    }

    /*  override fun  onAttach(context: Context?) {
          super.onAttach(context!!)
          // Create a call back

      }*/


    // interface to use callback
    interface interfaceClicOnButtonUpdateImage {
        fun OnButtonClickedListenerUpdate(view: View?)
    }*/
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddPropertyFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddPropertyFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}



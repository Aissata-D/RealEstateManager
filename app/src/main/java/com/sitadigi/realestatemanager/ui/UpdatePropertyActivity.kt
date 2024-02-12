package com.sitadigi.realestatemanager.ui

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
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
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import com.sitadigi.realestatemanager.BuildConfig
import com.sitadigi.realestatemanager.R
import com.sitadigi.realestatemanager.model.PictureInter
import com.sitadigi.realestatemanager.model.Property
import com.sitadigi.realestatemanager.utils.AddPropertyUtils
import com.sitadigi.realestatemanager.utils.UpdatePropertyUtils
import com.sitadigi.realestatemanager.viewModel.PictureViewModel
import com.sitadigi.realestatemanager.viewModel.PropertyViewModel
import com.sitadigi.realestatemanager.viewModelFactory.PictureViewModelFactory
import com.sitadigi.realestatemanager.viewModelFactory.PropertyViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.util.Date

 var propertyLocalityUpdate: String? = null

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
lateinit var propertyNearbyPointOfInterestsUpdate :  MutableList<String>
lateinit var chipSchoolUpdate: Chip
lateinit var chipShopsUpdate: Chip
lateinit var chipParkUpdate: Chip

var propertyLatLng: LatLng? = null
var propertyAdresseComplete: String? = null

lateinit var updatePropertyUtilsUpdate: UpdatePropertyUtils
val AUTOCOMPLETE_REQUEST_CODE_UP = 3
var mRequestCodeUpdate = 0
var mResultCodeUpdate = 0
var  mDataUpdate: Intent? = null
var property_id: Int =0
val PROPERTY_LOCALITY : String ="PROPERTY_LOCALITY"

private val viewModelJob = SupervisorJob()
private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    lateinit var propertyToUpdate : Property


class UpdatePropertyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_property)

        val bundle = intent.extras

        if (bundle != null) {
            val s: Int = bundle.getInt("PROPERTY_ID")
            val locality :String? = bundle.getString(PROPERTY_LOCALITY)
            if (s != null) {
                 property_id = s
                propertyLocalityUpdate = locality
                Log.e("TAG", "UpdatePropertyActivity: property_id: $property_id bundle: $bundle" )

            }
        }
        val factoryProperty = PropertyViewModelFactory(this as FragmentActivity)
        val factoryPicture = PictureViewModelFactory(this as FragmentActivity)


        chipSchoolUpdate = findViewById(R.id.chip_school_update)
        chipParkUpdate = findViewById(R.id.chip_park_update)
        chipShopsUpdate = findViewById(R.id.chip_shops_update)
        fabAddPictureFromGalleryUpdate = findViewById(R.id.fab_property_update)
        fabTakePhotoWithCameraUpdate = findViewById(R.id.fab_take_photo_update)
        btnAddPropertyUpdate = findViewById(R.id.property_btn_update_property)
        editPropertyTypeUpdate = findViewById(R.id.property_type_input_update)
        editPropertyPriceUpdate = findViewById(R.id.property_price_input_update)
        editPropertySurfaceUpdate = findViewById(R.id.property_surface_input_update)
        editNumberOfBathRoomsUpdate = findViewById(R.id.property_number_bathroom_input_update)
        editNumberOfBedRoomsUpdate = findViewById(R.id.property_number_bedroom_input_update)
        editNumberOfRoomsUpdate = findViewById(R.id.property_number_of_rooms_input_update)
        editDescriptionUpdate = findViewById(R.id.property_description_input_update)
        tvAddressUpdate = findViewById(R.id.tv_property_address_update)
        tvNearbyPointOfInterestUpdate = findViewById(R.id.property_nearby_points_of_interest_tv_update)
        tvEmailOfRealEstateAgentUpdate = findViewById(R.id.property_email_of_real_estate_agent_tv_update)
        val recyclerViewUpdate : RecyclerView = findViewById(R.id.recyclerview_update)



        propertyViewModelUpdate = ViewModelProvider(this, factoryProperty).get(PropertyViewModel::class.java)


        pictureViewModelUpdate = ViewModelProvider(this, factoryPicture).get(PictureViewModel::class.java)

        propertyViewModel = ViewModelProvider(this, factoryProperty).get(PropertyViewModel::class.java)

        uiScope.launch {
             propertyToUpdate = propertyViewModel.getPropertyById(property_id)
            propertyNearbyPointOfInterestsUpdate = propertyToUpdate.propertyNearbyPointsOfInterest
           // propertyNearbyPointOfInterestsUpdate.addAll(pointOfInterests)

            /*   for(p in pointOfInterests){
               // if(!propertyNearbyPointOfInterestsUpdate.contains(p.toString())){
                    propertyNearbyPointOfInterestsUpdate.add(p)
               // }
            }*/

            val propertyType =propertyToUpdate?.propertyType
            editPropertyTypeUpdate.setText(propertyType)// POURQUOI C'ESTR NULL ALORS QU'IL DEBUG NON-NULL

           val propertyPrice = propertyToUpdate.propertyPrice.toString()
            editPropertyPriceUpdate.setText(propertyPrice)
            val propertySurface = propertyToUpdate.propertySurface.toString()
            editPropertySurfaceUpdate.setText(propertySurface)
            val propertyNumberOfBathRooms = propertyToUpdate.propertyNumberOfBathRooms
            editNumberOfBathRoomsUpdate.setText(propertyNumberOfBathRooms.toString())
           val propertyNumberOfBedRooms = propertyToUpdate.propertyNumberOfBedRooms
            editNumberOfBedRoomsUpdate.setText(propertyNumberOfBedRooms.toString())
            val propertyNumberOfRooms = propertyToUpdate.propertyNumberOfRooms
            editNumberOfRoomsUpdate.setText(propertyNumberOfRooms.toString())
            val propertyDescription = propertyToUpdate.propertyDescription
            editDescriptionUpdate.setText(propertyDescription.toString())
           val propertyAddress = propertyToUpdate.propertyAdresseComplete
            tvAddressUpdate.setText(propertyAddress.toString())

            propertyLocalityUpdate = propertyToUpdate.propertyLocality
           // tvNearbyPointOfInterestUpdate = findViewById(R.id.property_nearby_points_of_interest_tv_update)
           // tvEmailOfRealEstateAgentUpdate

            if(propertyToUpdate.propertyNearbyPointsOfInterest.contains("School")){
                chipSchoolUpdate.isChecked = true
            }
            if(propertyToUpdate.propertyNearbyPointsOfInterest.contains("Park")){
                chipParkUpdate.isChecked = true
            }
            if(propertyToUpdate.propertyNearbyPointsOfInterest.contains("Shops")){
                chipShopsUpdate.isChecked = true
            }
            propertyNearbyPointOfInterestsUpdate.clear()
        }
        tvEmailOfRealEstateAgentUpdate.text= "Email of agent : ${userEmailUpdate}"
        updatePropertyUtilsUpdate = UpdatePropertyUtils(
            propertyViewModelUpdate,
            pictureViewModelUpdate, this as FragmentActivity, recyclerViewUpdate, tvAddressUpdate,property_id)

        updatePropertyUtilsUpdate.initListOfRecyclerViewInUpdateActivity(property_id)

        updatePropertyUtilsUpdate.verifyStoragePermissions(this)

        fabTakePhotoWithCameraUpdate.setOnClickListener {
            updatePropertyUtilsUpdate.showAddImageDialog(OPEN_CAMERA_UP,this) }

        fabAddPictureFromGalleryUpdate.setOnClickListener {updatePropertyUtilsUpdate.showAddImageDialog(
            OPEN_GALLERY_UP,this) }


        btnAddPropertyUpdate.setOnClickListener {
            if( chipSchoolUpdate.isChecked) {
                propertyNearbyPointOfInterestsUpdate.add("School")
                for (y in propertyNearbyPointOfInterestsUpdate){
                    Log.e("TAG", "onCreate:ship " + y)

                }}
            if(chipParkUpdate.isChecked ){
                propertyNearbyPointOfInterestsUpdate.add("Park")
                for (y in propertyNearbyPointOfInterestsUpdate){
                    Log.e("TAG", "onCreate:ship " + y)

                }}
            if(chipShopsUpdate.isChecked){
                propertyNearbyPointOfInterestsUpdate.add("Shops")
                for (y in propertyNearbyPointOfInterestsUpdate){
                    Log.e("TAG", "onCreate:ship " + y)

                }}

            updatePropertyUtilsUpdate.clickOnAddPropertyBtn(
                property_id,
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
                Places.initialize(this, API_KEY)
            }
            // Places.initialize(applicationContext, API_KEY)

            // Create a new PlacesClient instance
            val placesClient = Places.createClient(this)


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
                .build(this)
            startActivityForResult(intent,
                AUTOCOMPLETE_REQUEST_CODE_UP
            )
        }

    }
  /*  fun initRecyclerView(){
        // set up the RecyclerView

        val numberOfColumns = 3
        recyclerView.layoutManager = GridLayoutManager(activity?.applicationContext, numberOfColumns)
        val adapter = AddImageAdapter(pictureInters)
        recyclerView.adapter = adapter

    }*/

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // returnActivityResult(requestCode, resultCode, data)
        mRequestCode = requestCode
        mResultCode = resultCode
        mData = data
        updatePropertyUtilsUpdate.checkActivityResult(requestCode,resultCode, data)



        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    data?.let {
                        val place = Autocomplete.getPlaceFromIntent(data)
                        propertyLocalityUpdate =  place.addressComponents.asList().get(2).name
                        Log.e("TAG", "Place: ${place.name}, ${place.id}," +
                                place.addressComponents.asList().get(2).name + "LATLNG :${place.latLng}")
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
       // addPropertyUtilsUpdate.initRecyclerView()

        // Call callback to close addMeeting fragment and open ListMeeting Fragment
      //  mCallback?.OnButtonClickedListener(view)


       /* if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    data?.let {
                        val place = Autocomplete.getPlaceFromIntent(data)
                        propertyLocality =  place.addressComponents.asList().get(2).name
                        Log.e("TAG", "Place: ${place.name}, ${place.id}," +
                                place.addressComponents.asList().get(2).name)
                        tvAddress.text=place.address
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
        }*/
      //  addPropertyUtilsUpdate.checkActivityResult(requestCode,resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }
}
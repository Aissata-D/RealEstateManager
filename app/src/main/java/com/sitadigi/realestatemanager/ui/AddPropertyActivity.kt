package com.sitadigi.realestatemanager.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
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
import com.sitadigi.realestatemanager.R
import com.sitadigi.realestatemanager.utils.AddPropertyUtils
import com.sitadigi.realestatemanager.viewModelFactory.PropertyViewModelFactory
import java.util.*


class AddPropertyActivity : FragmentActivity() {

    var userEmail = ""
    lateinit  var propertyLocality: String
    lateinit var addPropertyUtils: AddPropertyUtils
    private lateinit var propertyViewModel: PropertyViewModel

    private val OPEN_GALLERY = "OPEN_GALLERY"
    private val OPEN_CAMERA = "OPEN_CAMERA"
    lateinit var fabAddPictureFromGallery: ExtendedFloatingActionButton
    lateinit var fabTakePhotoWithCamera: FloatingActionButton
    lateinit var btnAddProperty: Button
    lateinit var editPropertyType: TextInputEditText
    lateinit var editPropertyPrice: TextInputEditText
    lateinit var editPropertySurface: TextInputEditText
    lateinit var editNumberOfBathRooms: TextInputEditText
    lateinit var editNumberOfBedRooms: TextInputEditText
    lateinit var editNumberOfRooms: TextInputEditText
    private lateinit var editDescription: TextInputEditText
    lateinit var tvAddress: MaterialTextView
    lateinit var tvNearbyPointOfInterest: MaterialTextView
    lateinit var tvEmailOfRealEstateAgent: MaterialTextView
    lateinit var recyclerView: RecyclerView
    val propertyNearbyPointOfInterests =  mutableListOf<String>()
    lateinit var chipSchool: Chip
    lateinit var chipShops: Chip
    lateinit var chipPark: Chip
    val AUTOCOMPLETE_REQUEST_CODE = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_property)

        val bundle = intent.extras

        if (bundle != null) {
            val s = bundle["USER_EMAIL"] as String?
            if (s != null) {
                userEmail = s
                Log.e("TAG", "onCreateAddActivity: email: $userEmail bundle: $bundle" )

            }
        }

        chipSchool = findViewById(R.id.chip_school)
        chipPark = findViewById(R.id.chip_park)
        chipShops = findViewById(R.id.chip_shops)
        fabAddPictureFromGallery = findViewById(R.id.fab_property_add_img)
        fabTakePhotoWithCamera = findViewById(R.id.fab_take_photo)
        btnAddProperty = findViewById(R.id.property_btn_add_property)
        editPropertyType = findViewById(R.id.property_type_input)
        editPropertyPrice = findViewById(R.id.property_price_input)
        editPropertySurface = findViewById(R.id.property_surface_input)
        editNumberOfBathRooms = findViewById(R.id.property_number_bathroom_input)
        editNumberOfBedRooms = findViewById(R.id.property_number_bedroom_input)
        editNumberOfRooms = findViewById(R.id.property_number_of_rooms_input)
        editDescription = findViewById(R.id.property_description_input)
        tvAddress = findViewById(R.id.tv_property_address)
        tvNearbyPointOfInterest = findViewById(R.id.property_nearby_points_of_interest_tv)
        tvEmailOfRealEstateAgent = findViewById(R.id.property_email_of_real_estate_agent_tv)
        recyclerView = findViewById(R.id.recyclerview)

        val factory = PropertyViewModelFactory(this)
        propertyViewModel = ViewModelProvider(this, factory).get(PropertyViewModel::class.java)

        tvEmailOfRealEstateAgent.text= "Email of agent : $userEmail"
        addPropertyUtils = AddPropertyUtils(propertyViewModel, this, recyclerView)

        addPropertyUtils.verifyStoragePermissions(this)

        fabTakePhotoWithCamera.setOnClickListener { addPropertyUtils.showAddImageDialog(OPEN_CAMERA,this) }

        fabAddPictureFromGallery.setOnClickListener { addPropertyUtils.showAddImageDialog(OPEN_GALLERY,this) }

        btnAddProperty.setOnClickListener {
           if( chipSchool.isChecked) {
                   propertyNearbyPointOfInterests.add("School")
               for (y in propertyNearbyPointOfInterests){
                   Log.e("TAG", "onCreate:ship " + y)

               }}
            if(chipPark.isChecked){
               propertyNearbyPointOfInterests.add("Park")
               for (y in propertyNearbyPointOfInterests){
                   Log.e("TAG", "onCreate:ship " + y)

               }}
            if(chipShops.isChecked){
            propertyNearbyPointOfInterests.add("Shops")
            for (y in propertyNearbyPointOfInterests){
                Log.e("TAG", "onCreate:ship " + y)

            }}

            addPropertyUtils.clickOnAddPropertyBtn(editPropertyType, editPropertyPrice, editPropertySurface, editNumberOfRooms,editNumberOfBedRooms,
                editNumberOfBathRooms,editDescription, propertyLocality, propertyNearbyPointOfInterests, userEmail)

            Log.e("TAG", "onCreate: DATE : "+Date() )
        }

       /* chipSchool.setOnCheckedChangeListener { chip, isChecked ->
            // Responds to chip checked/unchecked
            if(isChecked){
                propertyNearbyPointOfInterests.add("School")
                var i = propertyNearbyPointOfInterests.size
                for (y in propertyNearbyPointOfInterests){
                    Log.e("TAG", "onCreate:ship " + y)

                }
                var s = propertyNearbyPointOfInterests.get(i-1)
                Log.e("TAG", "onCreate:ship " + s)
            }
        }
        chipPark.setOnCheckedChangeListener { chip, isChecked ->
            // Responds to chip checked/unchecked
            if(isChecked){
                propertyNearbyPointOfInterests.add("Park")

                for (y in propertyNearbyPointOfInterests){
                    Log.e("TAG", "onCreate:ship " + y)

                }
                var i = propertyNearbyPointOfInterests.size

                var s = propertyNearbyPointOfInterests.get(i-1)
                Log.e("TAG", "onCreate:ship " + s)
            }
        }
        chipShops.setOnCheckedChangeListener { chip, isChecked ->
            // Responds to chip checked/unchecked
            if(isChecked){
                propertyNearbyPointOfInterests.add("Shops")
                for (y in propertyNearbyPointOfInterests){
                    Log.e("TAG", "onCreate:ship " + y)

                }

                var i = propertyNearbyPointOfInterests.size
                var s = propertyNearbyPointOfInterests.get(i-1)
                Log.e("TAG", "onCreate:ship " + s)
            }
        }*/

        tvAddress.setOnClickListener {

            
                // Initialize the SDK
                val API_KEY: String = BuildConfig.GOOGLE_MAPS_API_KEY

                if (!Places.isInitialized()) {
                    Places.initialize(applicationContext, API_KEY)
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
            val fields = listOf(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.ADDRESS_COMPONENTS)

            // Start the autocomplete intent.
            val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                    .setCountry("FR")
                    .setTypeFilter(TypeFilter.ADDRESS)
                    .build(this)
            startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE)

        }
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        addPropertyUtils.checkActivityResult(requestCode,resultCode, data)


        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
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
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}
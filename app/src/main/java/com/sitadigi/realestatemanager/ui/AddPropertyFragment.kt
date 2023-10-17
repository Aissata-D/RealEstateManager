package com.sitadigi.realestatemanager.ui

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
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.android.material.chip.Chip
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import com.sitadigi.realestatemanager.BuildConfig
import com.sitadigi.realestatemanager.R
import com.sitadigi.realestatemanager.utils.AddPropertyUtils
import com.sitadigi.realestatemanager.viewModel.PictureViewModel
import com.sitadigi.realestatemanager.viewModel.PropertyViewModel
import com.sitadigi.realestatemanager.viewModelFactory.PictureViewModelFactory
import com.sitadigi.realestatemanager.viewModelFactory.PropertyViewModelFactory
import java.util.Date

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

var userEmail = ""
lateinit  var propertyLocality: String
lateinit var addPropertyUtils: AddPropertyUtils
private lateinit var propertyViewModel: PropertyViewModel
private lateinit var pictureViewModel: PictureViewModel

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
//lateinit var recyclerView: RecyclerView
val propertyNearbyPointOfInterests =  mutableListOf<String>()
lateinit var chipSchool: Chip
lateinit var chipShops: Chip
lateinit var chipPark: Chip
val AUTOCOMPLETE_REQUEST_CODE = 3

 var mRequestCode = 0
 var mResultCode = 0
 var  mData: Intent? = null

 // Declare callback
 var mCallback: AddPropertyFragment.interfaceClicOnButtonAddImage? = null


/**
 * A simple [Fragment] subclass.
 * Use the [AddPropertyFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddPropertyFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
       val v = inflater.inflate(R.layout.fragment_add_property, container, false)




        val bundle = this.arguments// intent.extras

        if (bundle != null) {
            val s = bundle["USER_EMAIL"] as String?
            if (s != null) {
                userEmail = s
                Log.e("TAG", "onCreateAddActivity: email: $userEmail bundle: $bundle" )

            }
        }

        chipSchool = v.findViewById(R.id.chip_school)
        chipPark = v.findViewById(R.id.chip_park)
        chipShops = v.findViewById(R.id.chip_shops)
        fabAddPictureFromGallery = v.findViewById(R.id.fab_property_add_img)
        fabTakePhotoWithCamera = v.findViewById(R.id.fab_take_photo)
        btnAddProperty = v.findViewById(R.id.property_btn_add_property)
        editPropertyType = v.findViewById(R.id.property_type_input)
        editPropertyPrice = v.findViewById(R.id.property_price_input)
        editPropertySurface = v.findViewById(R.id.property_surface_input)
        editNumberOfBathRooms = v.findViewById(R.id.property_number_bathroom_input)
        editNumberOfBedRooms = v.findViewById(R.id.property_number_bedroom_input)
        editNumberOfRooms = v.findViewById(R.id.property_number_of_rooms_input)
        editDescription = v.findViewById(R.id.property_description_input)
        tvAddress = v.findViewById(R.id.tv_property_address)
        tvNearbyPointOfInterest = v.findViewById(R.id.property_nearby_points_of_interest_tv)
        tvEmailOfRealEstateAgent = v.findViewById(R.id.property_email_of_real_estate_agent_tv)
       val recyclerView : RecyclerView = v.findViewById(R.id.recyclerview_add)

        val factory = PropertyViewModelFactory(this.activity as FragmentActivity)
        propertyViewModel = ViewModelProvider(this, factory).get(PropertyViewModel::class.java)

        val factoryPicture = PictureViewModelFactory(this.activity as FragmentActivity)
        pictureViewModel = ViewModelProvider(this, factoryPicture).get(PictureViewModel::class.java)

        tvEmailOfRealEstateAgent.text= "Email of agent : $userEmail"
        addPropertyUtils = AddPropertyUtils(propertyViewModel,
            pictureViewModel, this.activity as FragmentActivity, recyclerView)

        addPropertyUtils.verifyStoragePermissions(this.activity)

        fabTakePhotoWithCamera.setOnClickListener {
            addPropertyUtils.showAddImageDialog(OPEN_CAMERA,this.context!!) }

        fabAddPictureFromGallery.setOnClickListener { addPropertyUtils.showAddImageDialog(OPEN_GALLERY,this.context!!) }

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

            Log.e("TAG", "onCreate: DATE : "+ Date() )
        }



        tvAddress.setOnClickListener {


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
            val fields = listOf(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.ADDRESS_COMPONENTS)

            // Start the autocomplete intent.
            val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                .setCountry("FR")
                .setTypeFilter(TypeFilter.ADDRESS)
                .build(this.context)
            startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE)

        }
        return v
    }
    fun returnActivityResult(requestCode: Int, resultCode: Int, data: Intent?){

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
       // returnActivityResult(requestCode, resultCode, data)
        mRequestCode = requestCode
        mResultCode = resultCode
        mData = data

        addPropertyUtils.checkActivityResult(requestCode,resultCode, data)

        // Call callback to close addMeeting fragment and open ListMeeting Fragment
        mCallback?.OnButtonClickedListener(view)


      /*  if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
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
            mCallback?.OnButtonClickedListener(view)
            return
        }*/
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun createCallbackToParentActivity() {
        //this.requestCode = mRequestCode
        //mResultCode = resultCode
        //mData = data
        mCallback = activity as interfaceClicOnButtonAddImage?
        return returnActivityResult( mRequestCode , mResultCode , mData )

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
    interface interfaceClicOnButtonAddImage {
        fun OnButtonClickedListener(view: View?)
    }
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
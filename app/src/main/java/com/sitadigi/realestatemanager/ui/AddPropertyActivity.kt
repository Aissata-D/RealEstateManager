package com.sitadigi.realestatemanager.ui

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.text.InputType
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import com.sitadigi.realestatemanager.R
import com.sitadigi.realestatemanager.dao.PictureDao
import com.sitadigi.realestatemanager.dao.PropertyDao
import com.sitadigi.realestatemanager.database.UserDatabase
import com.sitadigi.realestatemanager.model.Picture
import com.sitadigi.realestatemanager.model.PictureInter
import com.sitadigi.realestatemanager.model.Property
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream


class AddPropertyActivity : AppCompatActivity() {
    val REQUEST_IMAGE_CAPTURE = 1
    var photoDescription = ""
    val OPEN_GALLERY = "OPEN_GALLERY"
    val OPEN_CAMERA = "OPEN_CAMERA"

    //lateinit var recyclerView
    lateinit var fabAddPictureFromGallery: ExtendedFloatingActionButton
    lateinit var fabTakePhotoWithCamera :FloatingActionButton
    lateinit var btnAddProperty: Button
//    lateinit var imgProperty: ImageView
   // lateinit var editImgDescription: TextInputEditText
    lateinit var editPropertyType: TextInputEditText
    lateinit var editPropertyPrice: TextInputEditText
    lateinit var editPropertySurface: TextInputEditText
    lateinit var editNumberOfRooms: TextInputEditText
    private lateinit var editDescription: TextInputEditText
    lateinit var editAddress: TextInputEditText
    lateinit var tvNearbyPointOfInterest: MaterialTextView
    lateinit var tvEmailOfRealEstateAgent: MaterialTextView


    var bitmap: Bitmap? = null
    private val viewModelJob = SupervisorJob()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    lateinit var pictureDao: PictureDao
    private lateinit var propertyDao: PropertyDao
    private  var pictures = mutableListOf<Picture>()
    private var fkProperty:Int = 0
    private var pictureInters = mutableListOf<PictureInter>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_property)

        pictureDao = UserDatabase.getInstance(this).pictureDao
        propertyDao = UserDatabase.getInstance(this).propertyDao
        uiScope.launch {
           /* if (propertyDao.getLastId() == null) {
                fkProperty = 1
            } else {
                fkProperty = propertyDao.getLastId() + 1
            }*/
        }
        fabAddPictureFromGallery = findViewById(R.id.fab_property_add_img)
        fabTakePhotoWithCamera = findViewById(R.id.fab_take_photo)
        btnAddProperty = findViewById(R.id.property_btn_add_property)
       // imgProperty = findViewById(R.id.property_img)
      //  editImgDescription = findViewById(R.id.property_img_description_input)
        editPropertyType = findViewById(R.id.property_type_input)
        editPropertyPrice = findViewById(R.id.property_price_input)
        editPropertySurface = findViewById(R.id.property_surface_input)
        editNumberOfRooms = findViewById(R.id.property_number_of_rooms_input)
        editDescription = findViewById(R.id.property_description_input)
        editAddress = findViewById(R.id.property_address_input)
        tvNearbyPointOfInterest = findViewById(R.id.property_nearby_points_of_interest_tv)
        tvEmailOfRealEstateAgent = findViewById(R.id.property_email_of_real_estate_agent_tv)


        //pictureInters = mutableListOf()

        fabTakePhotoWithCamera.setOnClickListener { showAddImageDialog(OPEN_CAMERA) }
        fabAddPictureFromGallery.setOnClickListener { showAddImageDialog(OPEN_GALLERY)}
        btnAddProperty.setOnClickListener { v ->
            uiScope.launch {

                val propertyType = editPropertyType.text.toString()
                val propertyPrice = (editPropertyPrice.text.toString()).toDouble()
                val propertySurface = (editPropertySurface.text.toString()).toInt()
                val propertyNumberOfRooms = (editNumberOfRooms.text.toString()).toInt()
                val propertyDescription = editDescription.text.toString()
                val propertyAddress = editAddress.text.toString()
                val propertySpinnerNearbyPointOfInterest = tvNearbyPointOfInterest.text.toString()
                val propertySpinnerEmailOfRealEstateAgent = tvEmailOfRealEstateAgent.text.toString()

                val property = Property(0,propertyType,propertyPrice,propertySurface
                        ,propertyNumberOfRooms,propertyDescription, propertyAddress,
                        propertySpinnerNearbyPointOfInterest,null,null,
                        1, propertySpinnerEmailOfRealEstateAgent,pictureInters)

                propertyDao.insert(property)
                fkProperty = propertyDao.getLastId()
                for (item in pictureInters) {
                    pictureDao.insert(Picture(0,item.description,item.image,fkProperty))
                }

            }
        }
       initRecyclerView()
    }
    private fun initRecyclerView(){
        // set up the RecyclerView
        // set up the RecyclerView
       val  recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val numberOfColumns = 3
        recyclerView.layoutManager = GridLayoutManager(this, numberOfColumns)
        val adapter = AddImageAdapter(pictureInters)
        // adapter.setClickListener(this)
        recyclerView.adapter = adapter

    }
    private fun getPictureBitmap(image: ByteArray): Bitmap {
        val bitmap = BitmapFactory.decodeByteArray(image,0,image.size)
        return bitmap
    }

    private fun getBytes(bitmap: Bitmap?): ByteArray {
        val stream = ByteArrayOutputStream()
        if (bitmap != null) {
            bitmap.compress(Bitmap.CompressFormat.PNG,0,stream)
        }
        return stream.toByteArray()

    }

    /* private fun registerForActivityresult(getContent: ActivityResultContracts.GetContent, any: Any): Any {

     }*/
    private fun importImageFromGallery(){
        val intentImg = Intent(Intent.ACTION_GET_CONTENT)
        intentImg.type = "image/*"
        startActivityForResult(intentImg, 100)

    }
    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        } catch (e: ActivityNotFoundException) {
            // display error state to the user
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 100 && resultCode == RESULT_OK){
            val uri = data?.data
// Transform image to bitmap to show as image in imageView
            val inputStream = contentResolver.openInputStream(uri!!)
            bitmap = BitmapFactory.decodeStream(inputStream)
            //imgProperty.setImageBitmap(bitmap)

            //val description = editImgDescription.text.toString()
            val imageBytArray: ByteArray = getBytes(bitmap)

            pictureInters.add(PictureInter(photoDescription,imageBytArray))
            initRecyclerView()
            val numberOfImage =pictureInters.size
            Toast.makeText(this.applicationContext,"$numberOfImage image added",Toast.LENGTH_SHORT).show()

           // editImgDescription.setText("")

        }
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
           // val imageBitmap = data?.extras?.get("data") as Bitmap
            val imageBitmap = data?.extras?.get("data") as Bitmap
           // imageView.setImageBitmap(imageBitmap)

          //  val uri = data?.data
// Transform image to bitmap to show as image in imageView
          //  val inputStream = contentResolver.openInputStream(uri!!)
            bitmap = imageBitmap //BitmapFactory.decodeStream(inputStream)
            //imgProperty.setImageBitmap(bitmap)

            //val description = editImgDescription.text.toString()
            val imageBytArray: ByteArray = getBytes(bitmap)
            pictureInters.add(PictureInter(photoDescription,imageBytArray))
            initRecyclerView()
            val numberOfImage =pictureInters.size
            Toast.makeText(this.applicationContext,"$numberOfImage image added",Toast.LENGTH_SHORT).show()

        }
    }

    fun showAddImageDialog(openCameraOrGallery:String){
        val builder: AlertDialog.Builder = android.app.AlertDialog.Builder(this)
        builder.setTitle("Set photo description")

// Set up the input
        val input = EditText(this)
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setHint("Enter photo description")
        input.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView(input)

// Set up the buttons
        builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
            // Here you get get input text from the Edittext
            photoDescription = input.text.toString()
            if(photoDescription.isNotEmpty()){
                if (openCameraOrGallery == OPEN_CAMERA){
                dispatchTakePictureIntent()
                }else if (openCameraOrGallery == OPEN_GALLERY){
                    importImageFromGallery()
                }
            }else{
                Toast.makeText(this.applicationContext,"Write photo description please",Toast.LENGTH_SHORT).show()

            }

        })
        builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

        builder.show()
    }
}
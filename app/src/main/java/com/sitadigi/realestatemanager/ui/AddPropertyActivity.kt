package com.sitadigi.realestatemanager.ui

//import android.R
import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.InputType
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import com.sitadigi.realestatemanager.BuildConfig
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
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class AddPropertyActivity : FragmentActivity() {
    lateinit var currentPhotoPath: String
    private val REQUEST_EXTERNAL_STORAGE = 1
    private val PERMISSIONS_STORAGE = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
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
    private var pictureOfProperty = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_property)

        verifyStoragePermissions(this)
        pictureDao = UserDatabase.getInstance(this)?.pictureDao !!
        propertyDao = UserDatabase.getInstance(this)?.propertyDao!!
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
                        1, propertySpinnerEmailOfRealEstateAgent,pictureOfProperty)

                propertyDao.insert(property)
                Log.e("TAG", "onCreate: INSERT $property" )

              startActivity()

            }
        }
       initRecyclerView()
    }
    private fun startActivity(){
        val intent = Intent(this, MainActivity::class.java)
        this.startActivity(intent)
    }
    private fun initRecyclerView(){
        // set up the RecyclerView
        // set up the RecyclerView
       val  recyclerView = findViewById<RecyclerView>(com.sitadigi.realestatemanager.R.id.recyclerview)
        val numberOfColumns = 3
        recyclerView.layoutManager = GridLayoutManager(this, numberOfColumns)
        val adapter = AddImageAdapter(pictureInters)
        // adapter.setClickListener(this)
        recyclerView.adapter = adapter

    }
  /*  private fun comeBackToHomeFragment(fragment: Fragment?, activity: Activity?) {

        if (fragment == null) return else {
            val fm = activity?.fragmentManager//activity.fragmentManager
            val transaction = fm?.beginTransaction()
            transaction?.replace(R.id.framLayout_main_login, fragment)
            transaction?.commit()
        }
    }*/
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


    private fun importImageFromGallery(){
        val intentImg = Intent(Intent.ACTION_GET_CONTENT)
        intentImg.type = "image/*"
        intentImg.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

        val photoURI = FileProvider.getUriForFile(Objects.requireNonNull(getApplicationContext()),
                BuildConfig.APPLICATION_ID + ".fileprovider", createImageFile())
       // intentImg.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)

      //  intentImg.putExtra("outputFormat",Bitmap.CompressFormat.PNG.name)
       // intentImg.putExtra("return-data", true);
        try {
            startActivityForResult(intentImg, 100)
        } catch (e: ActivityNotFoundException) {
            // display error state to the user
        }

    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

        val photoURI = FileProvider.getUriForFile(Objects.requireNonNull(getApplicationContext()),
                BuildConfig.APPLICATION_ID + ".fileprovider", createImageFile())


        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        } catch (e: ActivityNotFoundException) {
            // display error state to the user
        }
    }
    fun verifyStoragePermissions(activity: Activity?) {
        // Check if we have write permission
        val permission = ActivityCompat.checkSelfPermission(activity!!, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            )
        }
    }


    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
                "JPEG_${timeStamp}_", /* prefix */
                ".jpg", /* suffix */
                storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
            Log.e("TAG", "createImageFile: $currentPhotoPath", )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 100 && resultCode == RESULT_OK){
            val uri = data?.data
            // Transform image to bitmap to show as image in imageView
            val inputStream = contentResolver.openInputStream(uri!!)
            bitmap = BitmapFactory.decodeStream(inputStream)

            val imageBytArray: ByteArray = getBytes(bitmap)
            val file: File = File(currentPhotoPath)
            val fOut = FileOutputStream(file)
            getPictureBitmap(imageBytArray).compress(Bitmap.CompressFormat.PNG, 85, fOut)
            fOut.flush()
            fOut.close()

            pictureInters.add(PictureInter(photoDescription, currentPhotoPath,imageBytArray))
            pictureOfProperty.add(currentPhotoPath)
            initRecyclerView()
            val numberOfImage =pictureInters.size

            Toast.makeText(this.applicationContext,"$numberOfImage image add",Toast.LENGTH_SHORT).show()

        }
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            loadImageFromFile()
            Toast.makeText(this.applicationContext,"text",Toast.LENGTH_SHORT).show()

        }
    }
    fun loadImageFromFile() {
       // val view: ImageView = findViewById<View>(R.id.imageViewHeader) as ImageView
       // view.setVisibility(View.VISIBLE)
      //  val targetW: Int = view.getWidth()
      //  val targetH: Int = view.getHeight()

        // Get the dimensions of the bitmap
        val bmOptions = BitmapFactory.Options()
        bmOptions.inJustDecodeBounds = true
        BitmapFactory.decodeFile(currentPhotoPath, bmOptions)
        val photoW = bmOptions.outWidth
        val photoH = bmOptions.outHeight

        // Determine how much to scale down the image
      //// val scaleFactor = Math.min(photoW / targetW, photoH / targetH)

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false
    //    bmOptions.inSampleSize = scaleFactor
        bmOptions.inPurgeable = true
        val bitmap = BitmapFactory.decodeFile(currentPhotoPath, bmOptions)
       // val description = editImgDescription.text.toString()
          val imageBytArray: ByteArray = getBytes(bitmap)
          pictureInters.add(PictureInter(photoDescription,currentPhotoPath,imageBytArray))
        pictureOfProperty.add(currentPhotoPath)
        initRecyclerView()
       // view.setImageBitmap(bitmap)
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
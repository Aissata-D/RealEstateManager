package com.sitadigi.realestatemanager.utils

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import android.provider.MediaStore
import android.text.InputType
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import com.sitadigi.realestatemanager.BuildConfig
import com.sitadigi.realestatemanager.model.PictureInter
import com.sitadigi.realestatemanager.model.Property
import com.sitadigi.realestatemanager.ui.AddImageAdapter
import com.sitadigi.realestatemanager.ui.MainActivity
import com.sitadigi.realestatemanager.ui.PropertyViewModel
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

class AddPropertyUtils(private val propertyViewModel : PropertyViewModel, val activity: Activity?,
                       private val recyclerView: RecyclerView) {
    var bitmap: Bitmap? = null
    private val viewModelJob = SupervisorJob()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private var pictureInters = mutableListOf<PictureInter>()
    private var pictureOfProperty = mutableListOf<String>()

    lateinit var currentPhotoPath: String
    private val REQUEST_EXTERNAL_STORAGE = 1
    private val PERMISSIONS_STORAGE = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    val REQUEST_IMAGE_CAPTURE = 1
    val REQUEST_IMAGE_GALLERY= 2
    var photoDescription = ""
    val OPEN_GALLERY = "OPEN_GALLERY"
    val OPEN_CAMERA = "OPEN_CAMERA"


     fun clickOnAddPropertyBtn(editPropertyType: TextInputEditText, editPropertyPrice:TextInputEditText,
                                     editPropertySurface:TextInputEditText, editNumberOfRooms:TextInputEditText,
                                     editNumberOfBedRooms:TextInputEditText
                                    , editNumberOfBathRooms:TextInputEditText,
                                     editDescription:TextInputEditText, editAddress:String,
                                     //tvNearbyPointOfInterest:MaterialTextView,
                                     propertyNearbyPointOfInterests: List<String>,
                                     tvEmailOfRealEstateAgent: String){

        uiScope.launch {

            val propertyType = editPropertyType.text.toString()
            val propertyPrice = (editPropertyPrice.text.toString()).toDouble()
            val propertySurface = (editPropertySurface.text.toString()).toInt()
            val propertyNumberOfRooms = (editNumberOfRooms.text.toString()).toInt()
            val propertyNumberOfBedRooms = (editNumberOfBedRooms.text.toString()).toInt()
            val propertyNumberOfBathRooms = (editNumberOfBathRooms.text.toString()).toInt()
            val propertyDescription = editDescription.text.toString()
            val propertyAddress = editAddress
            //val propertyNearbyPointOfInterests =  mutableListOf<String>()//tvNearbyPointOfInterest.text.toString()
           // val propertySpinnerEmailOfRealEstateAgent = tvEmailOfRealEstateAgent

            val property = Property(0,propertyType,propertyPrice,propertySurface
                    ,propertyNumberOfRooms,propertyNumberOfBedRooms,propertyNumberOfBathRooms
                    ,propertyDescription, propertyAddress,
                    propertyNearbyPointOfInterests,Date(),null,
                    1, tvEmailOfRealEstateAgent,pictureOfProperty)

            propertyViewModel.insert(property)
            Log.e("TAG", "onCreate: INSERT $property" )

            startActivity()

        }

    initRecyclerView()
}

 fun initRecyclerView(){
    // set up the RecyclerView

    val numberOfColumns = 3
    recyclerView.layoutManager = GridLayoutManager(activity?.applicationContext, numberOfColumns)
    val adapter = AddImageAdapter(pictureInters)
    recyclerView.adapter = adapter

}

fun startActivity(){
    val intent = Intent(activity, MainActivity::class.java)
    activity?.startActivity(intent)
}

    fun getPictureBitmap(image: ByteArray): Bitmap {
        val bitmap = BitmapFactory.decodeByteArray(image,0,image.size)
        return bitmap
    }

    fun getBytes(bitmap: Bitmap?): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.PNG,0,stream)
        return stream.toByteArray()

    }

    private fun importImageFromGallery(){
        val intentImg = Intent(Intent.ACTION_GET_CONTENT)
        intentImg.type = "image/*"
        intentImg.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

        val photoURI = FileProvider.getUriForFile(Objects.requireNonNull(activity?.applicationContext !! ),
                BuildConfig.APPLICATION_ID + ".fileprovider", createImageFile())
        // intentImg.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)

        //  intentImg.putExtra("outputFormat",Bitmap.CompressFormat.PNG.name)
        // intentImg.putExtra("return-data", true);
        try {
            activity.startActivityForResult(intentImg, REQUEST_IMAGE_GALLERY)
        } catch (e: ActivityNotFoundException) {
            // display error state to the user
        }

    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

        val photoURI = FileProvider.getUriForFile(Objects.requireNonNull(activity?.applicationContext
                !! ),
                BuildConfig.APPLICATION_ID + ".fileprovider", createImageFile())


        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
        try {
            activity.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        } catch (e: ActivityNotFoundException) {
            // display error state to the user
        }
    }
    fun verifyStoragePermissions(activity: Activity?) {
        // Check if we have write permission
        val permission = ActivityCompat.checkSelfPermission(activity!!,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
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
        val storageDir: File? = activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
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


    fun showAddImageDialog(openCameraOrGallery:String, context : Context){
        val builder: AlertDialog.Builder = android.app.AlertDialog.Builder(context)
        builder.setTitle("Set photo description")

// Set up the input
        val input = EditText(context)
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
                Toast.makeText(activity?.applicationContext !! ,"Write photo description please", Toast.LENGTH_SHORT).show()

            }

        })
        builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

        builder.show()
    }
    fun checkActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        if(requestCode == REQUEST_IMAGE_GALLERY && resultCode == FragmentActivity.RESULT_OK){
            val uri = data?.data
            // Transform image to bitmap to show as image in imageView
            val inputStream = activity?.contentResolver?.openInputStream(uri!!)
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

            Toast.makeText(activity?.applicationContext,"$numberOfImage image add",Toast.LENGTH_SHORT).show()

        }
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == FragmentActivity.RESULT_OK) {

            loadImageFromFile()
            Toast.makeText(activity?.applicationContext,"text",Toast.LENGTH_SHORT).show()

        }
    }

}
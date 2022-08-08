package com.sitadigi.realestatemanager.ui

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.sitadigi.realestatemanager.R
import com.sitadigi.realestatemanager.dao.PictureDao
import com.sitadigi.realestatemanager.database.UserDatabase
import com.sitadigi.realestatemanager.model.Picture
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream

class AddPropertyActivity2 : AppCompatActivity() {
    lateinit var imgProperty : ImageView
    lateinit var imgFromBdd : ImageView
    lateinit var editDescription: EditText
    lateinit var btnRegister: Button
    lateinit var btnBdd: Button

    var bitmap : Bitmap? = null
    private val viewModelJob = SupervisorJob()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    lateinit var pictureDao: PictureDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_property2)
         pictureDao = UserDatabase.getInstance(this)?.pictureDao!!

        imgProperty = findViewById(R.id.img_property)
        editDescription = findViewById(R.id.img_description)
        btnRegister = findViewById(R.id.btn_enregister)
        imgFromBdd = findViewById(R.id.img_from_bdd)
        btnBdd = findViewById(R.id.btn_bdd)


        /* val galleryLuncher =  registerForActivityresult(ActivityResultContracts.GetContent()){data ->
             val inputStream = contentResolver.openInputStream(data)
             val bitmap = BitmapFactory.decodeStream(inputStream)
             imgProperty.setImageBitmap(bitmap)

         }*/
        imgProperty.setOnClickListener{
            val intentImg = Intent(Intent.ACTION_GET_CONTENT)
            intentImg.type = "image/*"
            startActivityForResult(intentImg,100)

        }
        btnRegister.setOnClickListener{v->
            uiScope.launch {
                val description = editDescription.text.toString()
                val imageBlob: ByteArray = getBytes(bitmap)
                val fkProperty : Int = 2
               // val picture = Picture(0,  description, imageBlob,fkProperty)

               // pictureDao.insert(picture)
            }
        }
        btnBdd.setOnClickListener{
            uiScope.launch {
                val i = pictureDao.getAllPicture().size -1
                val picture1 = pictureDao.getAllPicture().get(i)
//                val picture2 = pictureDao.getAllPicture().get(1)
  //              val picture3 = pictureDao.getAllPicture().get(2)
                imgFromBdd.setImageBitmap(getPictureBitmap(picture1.image))
            }

        }
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 100 && resultCode == RESULT_OK){
            val uri = data?.data
// Transform image to bitmap to show as image in imageView
            val inputStream = contentResolver.openInputStream(uri!!)
             bitmap = BitmapFactory.decodeStream(inputStream)
            imgProperty.setImageBitmap(bitmap)
        }
    }
}
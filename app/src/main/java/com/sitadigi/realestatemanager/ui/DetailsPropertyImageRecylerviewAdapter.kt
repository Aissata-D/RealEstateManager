package com.sitadigi.realestatemanager.ui

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import com.sitadigi.realestatemanager.R
import com.sitadigi.realestatemanager.model.Picture
import com.sitadigi.realestatemanager.model.Property
import com.sitadigi.realestatemanager.utils.PropertyRecyclerViewCustom

class DetailsPropertyImageRecylerviewAdapter(private val listOfPhoto : List<Picture>?,
                                             private val custom: PropertyRecyclerViewCustom,
                                  ) : RecyclerView.Adapter<DetailsPropertyImageRecylerviewAdapter.ViewHolder>() {
    var description = ""
    var numberOfRooms = 0
    var numberOfBathRooms = 0
    var numberOfBedRooms = 0
    var surface = 0

    val DESCRIPTION = "DESCRIPTION"
    val NUMBER_OF_ROOMS = "NUMBER_OF_ROOMS"
    val NUMBER_OF_BATH_ROOMS = "NUMBER_OF_BATH_ROOMS"
    val NUMBER_OF_BED_ROOMS = "NUMBER_OF_BED_ROOMS"
    val SURFACE = "SURFACE"


    val POSITION = "POSITION"
    val CONFIG = "CONFIG"
    val PHONE = "PHONE"
    val TABLET = "TABLET"
    var detailPropertyFragment: DetailsPropertyFragment? = null


    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_property_img_recyclerview, parent, false)
        // detailPropertyFragment=DetailsPropertyFragment()

        return ViewHolder(view)
    }


    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


       // val ItemsViewModel = listOfPhoto[position]

        // sets the image to the imageview from our itemHolder class
        //holder.imageView.setImageBitmap(getPictureBitmap(ItemsViewModel.propertyListOfPictures.get(0)))

            //val bitmap =loadImageFromFile(listOfPhoto?.get(0))
            val bitmap =loadImageFromFile(listOfPhoto?.get(position)?.currentPhotoPath)
            holder.customView.setImageBitmap(bitmap)
        if (listOfPhoto?.get(position)!=null){
            holder.customView.setText(listOfPhoto.get(position).description)
        }else {holder.customView.setText("image")}


    }


    // return the number of the items in the list
    override fun getItemCount(): Int {
        var i = 2
        if(listOfPhoto != null){
            i = listOfPhoto.size
        }
        return i
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val customView : PropertyRecyclerViewCustom = itemView.findViewById(R.id.property_recycler_view_custom)
        // val itemPropertyImageView: ImageView = itemView.findViewById(R.id.property_recycler_view_custom)

        // val textView: TextView = itemView.findViewById(R.id.textView)
    }
    private fun getPictureBitmap(image: ByteArray): Bitmap {
        val bitmap = BitmapFactory.decodeByteArray(image,0,image.size)
        return bitmap
    }

    fun loadImageFromFile(/*view : ImageView,*/ currentPhotoPath : String?): Bitmap? {
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
        // val imageBytArray: ByteArray = getBytes(bitmap)
        // pictureInters.add(PictureInter(photoDescription,currentPhotoPath,imageBytArray))
        //pictureOfProperty.add(currentPhotoPath)
        //initRecyclerView()
        custom.setImageBitmap(bitmap)
        // view.setImageBitmap(bitmap)
        return bitmap
    }

}

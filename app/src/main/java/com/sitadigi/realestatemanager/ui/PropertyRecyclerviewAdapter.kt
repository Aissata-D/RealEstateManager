package com.sitadigi.realestatemanager.ui

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import com.sitadigi.realestatemanager.R
import com.sitadigi.realestatemanager.model.Property
import com.sitadigi.realestatemanager.utils.PropertyRecyclerViewCustom

class PropertyRecyclerviewAdapter (private val mList: List<Property>, private val custom: PropertyRecyclerViewCustom) :
        RecyclerView.Adapter<PropertyRecyclerviewAdapter.ViewHolder>(){

        // create new views
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            // inflates the card_view_design view
            // that is used to hold list item
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_property, parent, false)
                    return ViewHolder(view)
        }

        // binds the list items to a view
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {

            val ItemsViewModel = mList[position]

            // sets the image to the imageview from our itemHolder class
            //holder.imageView.setImageBitmap(getPictureBitmap(ItemsViewModel.propertyListOfPictures.get(0)))
            val bitmap =loadImageFromFile(/*holder.itemPropertyImageView,*/ItemsViewModel.propertyListOfPictures.get(0))
            holder.itemPropertyType.text = ItemsViewModel.propertyType
            holder.itemPropertyLocation.text = ItemsViewModel.propertyAddress
            holder.itemPropertyPrice.text = ItemsViewModel.propertyPrice.toString()
            holder.customView.setImageBitmap(bitmap)
            holder.customView.setText("toto")

            // Clic on item in list ; Open Details of property clicked
            holder.itemView.setOnClickListener(View.OnClickListener { v ->
                Toast.makeText(v.context, "ITEM CLIQUE", Toast.LENGTH_SHORT).show()
                val property = ItemsViewModel
                val intentPropertyDetail = Intent(v.context,DetailsPropertyActivity::class.java)
                v.context.startActivity(intentPropertyDetail)

                intentPropertyDetail.putExtra("propertyType", property.propertyType)
                // intentPropertyDetail.put

                /* mPosition = listViewHolder.getAdapterPosition()
                 val restaurant: GoogleMapApiClass.Result = mRestaurants.get(mPosition)
                 val openDetailActivityUtils = OpenDetailActivityUtils()
                 openDetailActivityUtils.clickOnOpenDetailActivityInLisViewAdapter(
                     restaurant,
                     v.context, mMainViewViewModel
                 )*/
            })
        // sets the text to the textview from our itemHolder class
            // holder.textView.text = ItemsViewModel.text


        }

        // return the number of the items in the list
        override fun getItemCount(): Int {
            return mList.size
        }

        // Holds the views for adding it to image and text
        class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
            val customView : PropertyRecyclerViewCustom = itemView.findViewById(R.id.property_recycler_view_custom)
           // val itemPropertyImageView: ImageView = itemView.findViewById(R.id.property_recycler_view_custom)
            val itemPropertyType : MaterialTextView = itemView.findViewById(R.id.item_property_type)
            val itemPropertyPrice : MaterialTextView = itemView.findViewById(R.id.item_property_price)
            val itemPropertyLocation : MaterialTextView = itemView.findViewById(R.id.item_property_location)
            // val textView: TextView = itemView.findViewById(R.id.textView)
        }
        private fun getPictureBitmap(image: ByteArray): Bitmap {
            val bitmap = BitmapFactory.decodeByteArray(image,0,image.size)
            return bitmap
        }

    fun loadImageFromFile(/*view : ImageView,*/ currentPhotoPath : String): Bitmap? {
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
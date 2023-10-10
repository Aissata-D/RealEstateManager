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
import com.sitadigi.realestatemanager.model.Property
import com.sitadigi.realestatemanager.utils.PropertyRecyclerViewCustom


class PropertyRecyclerviewAdapter (private val mList: List<Property>, private val custom: PropertyRecyclerViewCustom,
                                   val fragmentActivity: FragmentActivity, val mConfig:String?) :
        RecyclerView.Adapter<PropertyRecyclerviewAdapter.ViewHolder>(){

    val POSITION = "POSITION"
    val CONFIG = "CONFIG"
    val PHONE = "PHONE"
    val TABLET= "TABLET"
    var detailPropertyFragment: DetailsPropertyFragment? =null
        // create new views
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            // inflates the card_view_design view
            // that is used to hold list item
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_property, parent, false)
           // detailPropertyFragment=DetailsPropertyFragment()

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
               val fragmentManager: FragmentManager = fragmentActivity.supportFragmentManager
               val transaction = fragmentManager.beginTransaction()

               if (mConfig !== TABLET) {
                  // position = holder.getAdapterPosition()
                   if (detailPropertyFragment == null) {
                       detailPropertyFragment = DetailsPropertyFragment()
                       // Put Meeting position in a detailMeetingFragment
                       val bundle = Bundle()
                       bundle.putInt(POSITION, position)
                       bundle.putString(CONFIG, PHONE)
                       detailPropertyFragment!!.setArguments(bundle)
                      // if(fragmentActivity.findFr(R.id.framLayout_detail_or_add_property)==null){

                       //}
                       transaction.replace(R.id.framLayout_list_property, detailPropertyFragment!!)
                   } //give your fragment container id in first parameter
                   else {
                       transaction.show(detailPropertyFragment!!)
                       /*  val bundle = Bundle()
                         bundle.putInt(POSITION, position)
                         bundle.putString(CONFIG, PHONE)
                         detailPropertyFragment!!.setArguments(bundle)
                         transaction.replace(R.id.framLayout_list_property, detailPropertyFragment!!)
                    */
                   }
               } else if (mConfig === TABLET) {
                  // mPosition = holder.getAdapterPosition()
                   detailPropertyFragment = DetailsPropertyFragment()
                   // Put Meeting position in a detailMeetingFragment
                   val bundle = Bundle()
                   bundle.putInt(POSITION, position)
                   bundle.putString(CONFIG, TABLET)
                   detailPropertyFragment!!.setArguments(bundle)
                   transaction.replace(R.id.framLayout_detail_or_add_property, detailPropertyFragment!!)
               } else {
                   transaction.show(detailPropertyFragment!!)
               }

               transaction.addToBackStack("detailMeetingFragment") //if written, this transaction will be added to backstack

               transaction.commit()

        })
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
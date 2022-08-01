package com.sitadigi.realestatemanager.ui

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.sitadigi.realestatemanager.R
import com.sitadigi.realestatemanager.model.PictureInter

class AddImageAdapter (private val mList: List<PictureInter>) :
        RecyclerView.Adapter<AddImageAdapter.ViewHolder>(){




        // create new views
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            // inflates the card_view_design view
            // that is used to hold list item
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_add_image, parent, false)

            return ViewHolder(view)
        }

        // binds the list items to a view
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {

            val ItemsViewModel = mList[position]

            // sets the image to the imageview from our itemHolder class
            holder.imageView.setImageBitmap(getPictureBitmap(ItemsViewModel.image))

            // sets the text to the textview from our itemHolder class
           // holder.textView.text = ItemsViewModel.text

        }

        // return the number of the items in the list
        override fun getItemCount(): Int {
            return mList.size
        }

        // Holds the views for adding it to image and text
        class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
            val imageView: ImageView = itemView.findViewById(R.id.item_property_img)
           // val textView: TextView = itemView.findViewById(R.id.textView)
        }
    private fun getPictureBitmap(image: ByteArray): Bitmap {
        val bitmap = BitmapFactory.decodeByteArray(image,0,image.size)
        return bitmap
    }
    }
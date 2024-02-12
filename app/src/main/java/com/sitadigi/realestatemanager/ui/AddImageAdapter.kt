package com.sitadigi.realestatemanager.ui

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.sitadigi.realestatemanager.R
import com.sitadigi.realestatemanager.model.Picture
import com.sitadigi.realestatemanager.model.PictureInter
import com.sitadigi.realestatemanager.viewModel.PictureViewModel
import com.sitadigi.realestatemanager.viewModelFactory.PictureViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class AddImageAdapter (private val mList : MutableList<Picture>, var pictureViewModel: PictureViewModel) :
        RecyclerView.Adapter<AddImageAdapter.ViewHolder>(){

    private val viewModelJob = SupervisorJob()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    //val factoryProperty = PictureViewModelFactory(activity as FragmentActivity)
   // pictureViewModel = ViewModelProvider(activity, factoryProperty).get(PictureViewModel::class.java)


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


                val ItemsViewModel = mList.get(position)

                // sets the image to the imageview from our itemHolder class
                holder.imageView.setImageBitmap(getPictureBitmap(ItemsViewModel.image))
                holder.imageViewDelete.setOnClickListener(View.OnClickListener { v ->
                    uiScope.launch {
                    val actualPosition: Int = holder.getAdapterPosition()
                    mList.removeAt(actualPosition)
                        pictureViewModel.deletePictureById(ItemsViewModel.id)
                    notifyItemRemoved(actualPosition)
                    notifyItemRangeChanged(actualPosition, mList.size)


                    }

                })

                // sets the text to the textview from our itemHolder class
                // holder.textView.text = ItemsViewModel.text


        }
   /* private fun removeItem(position: Int) {
        val actualPosition: Int = holder.getAdapterPosition()
        model.remove(actualPosition)
        notifyItemRemoved(actualPosition)
        notifyItemRangeChanged(actualPosition, model.size())
    }*/

        // return the number of the items in the list
        override fun getItemCount(): Int {
            return mList.size
        }

        // Holds the views for adding it to image and text
        class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
            val imageView: ImageView = itemView.findViewById(R.id.item_property_img)
            val imageViewDelete: ImageView = itemView.findViewById(R.id.img_delete_button)
           // val textView: TextView = itemView.findViewById(R.id.textView)
        }
    private fun getPictureBitmap(image: ByteArray): Bitmap {
        val bitmap = BitmapFactory.decodeByteArray(image,0,image.size)
        return bitmap
    }
    }
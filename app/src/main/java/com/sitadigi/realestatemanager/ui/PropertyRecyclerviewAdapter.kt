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
import com.sitadigi.realestatemanager.dao.PictureDao
import com.sitadigi.realestatemanager.database.UserDatabase
import com.sitadigi.realestatemanager.model.Picture
import com.sitadigi.realestatemanager.model.Property
import com.sitadigi.realestatemanager.utils.PropertyRecyclerViewCustom
import com.sitadigi.realestatemanager.viewModel.UpdateViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch


class PropertyRecyclerviewAdapter (private val mList: List<Property>
/*,private val mPictureList: List<Picture>*/,
                                   private val custom: PropertyRecyclerViewCustom,
                                   val fragmentActivity: FragmentActivity, val mConfig:String?) :
        RecyclerView.Adapter<PropertyRecyclerviewAdapter.ViewHolder>(){
    var description =""
    var numberOfRooms = 0
    var numberOfBathRooms = 0
    var numberOfBedRooms = 0
    var surface = 0
    var mPosition :Int = 0

    val DESCRIPTION = "DESCRIPTION"
    val NUMBER_OF_ROOMS = "NUMBER_OF_ROOMS"
    val NUMBER_OF_BATH_ROOMS = "NUMBER_OF_BATH_ROOMS"
    val NUMBER_OF_BED_ROOMS = "NUMBER_OF_BED_ROOMS"
    val SURFACE = "SURFACE"
    lateinit var picture: Picture
    private lateinit var pictureDao: PictureDao
    lateinit var mPictureList : List<Picture>



    val POSITION = "POSITION"
    val CONFIG = "CONFIG"
    val PHONE = "PHONE"
    val TABLET= "TABLET"
    var detailPropertyFragment: DetailsPropertyFragment? =null
   lateinit  var listOfPhoto: ArrayList<String>
     var bitmap: Bitmap? =null

        // create new views
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            // inflates the card_view_design view
            // that is used to hold list item
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_property, parent, false)
           // detailPropertyFragment=DetailsPropertyFragment()
            pictureDao = UserDatabase.getInstance(parent.context)?.pictureDao!!
            listOfPhoto = ArrayList()

           // val scope = MainScope()
            return ViewHolder(view)
        }

        // binds the list items to a view
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {


            val ItemsViewModel = mList.get(position)
            mPosition = holder.getAdapterPosition()

            // sets the image to the imageview from our itemHolder class
            //holder.imageView.setImageBitmap(getPictureBitmap(ItemsViewModel.propertyListOfPictures.get(0)))
            val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())

            val propertyId:Int = ItemsViewModel.id
            val propertyLocality : String = ItemsViewModel.propertyLocality
            scope.launch {

                mPictureList = pictureDao.getListOfPictureByFkId(ItemsViewModel.id)
            if(mPictureList.size > 0){
                bitmap = loadImageFromFile(mPictureList.get(0).currentPhotoPath)
                holder.customView.setText(mPictureList.get(0).description)
                holder.customView.setImageBitmap(bitmap)
            }

            holder.itemPropertyType.text = ItemsViewModel.propertyType
            holder.itemPropertyLocation.text = ItemsViewModel.propertyAddress
            holder.itemPropertyPrice.text = ItemsViewModel.propertyPrice.toString()



            description = ItemsViewModel.propertyDescription
           // description = mList.get(propertyId).propertyDescription
            numberOfRooms = ItemsViewModel.propertyNumberOfRooms
            numberOfBathRooms = ItemsViewModel.propertyNumberOfBathRooms
            numberOfBedRooms = ItemsViewModel.propertyNumberOfBedRooms
            surface = ItemsViewModel.propertySurface
        }
            // Clic on item in list ; Open Details of property clicked
           holder.itemView.setOnClickListener(View.OnClickListener { v ->
                Toast.makeText(v.context, "ITEM CLIQUE", Toast.LENGTH_SHORT).show()
               val fragmentManager: FragmentManager = fragmentActivity.supportFragmentManager
               val transaction = fragmentManager.beginTransaction()

               if (mConfig !== TABLET) {
                    val viewModel= UpdateViewModel()
                       viewModel.setPropertyIdToUpdate(propertyId)

                  // position = holder.getAdapterPosition()
                   if (detailPropertyFragment == null) {
                       detailPropertyFragment = DetailsPropertyFragment()
                       // Put Meeting position in a detailMeetingFragment
                       val bundle = Bundle()
                       bundle.putInt(POSITION, mPosition)
                       bundle.putString(CONFIG, PHONE)
                       bundle.putInt(SURFACE, surface)
                       bundle.putInt(NUMBER_OF_ROOMS, numberOfRooms)
                       bundle.putInt(NUMBER_OF_BATH_ROOMS, numberOfBathRooms)
                       bundle.putInt(NUMBER_OF_BED_ROOMS, numberOfBedRooms)
                       bundle.putString(DESCRIPTION, description)
                       bundle.putStringArrayList("PHOTO", listOfPhoto)
                       bundle.putInt("PROPERTY_ID",propertyId )// A RECUPERER
                       bundle.putString("PROPERTY_LOCALITY",propertyLocality )// A RECUPERER
                       //bundle.putInt(mPosition,position )// A RECUPERER
                       detailPropertyFragment!!.setArguments(bundle)
                      // if(fragmentActivity.findFr(R.id.framLayout_detail_or_add_property)==null){

                       //}
                       transaction.replace(R.id.framLayout_list_property, detailPropertyFragment!!)
                   } //give your fragment container id in first parameter
                   else {
                       val viewModel= UpdateViewModel()
                       viewModel.setPropertyIdToUpdate(propertyId)
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
                   bundle.putInt(SURFACE, surface)
                   bundle.putInt(NUMBER_OF_ROOMS, numberOfRooms)
                   bundle.putInt(NUMBER_OF_BATH_ROOMS, numberOfBathRooms)
                   bundle.putInt(NUMBER_OF_BED_ROOMS, numberOfBedRooms)
                   bundle.putString(DESCRIPTION, description)
                   bundle.putStringArrayList("PHOTO", listOfPhoto)
                   bundle.putInt("PROPERTY_ID",propertyId )

                   detailPropertyFragment!!.setArguments(bundle)
                   transaction.replace(R.id.framLayout_detail_or_add_property, detailPropertyFragment!!)
               } else {
                   transaction.show(detailPropertyFragment!!)
               }

             //  transaction.addToBackStack("detailMeetingFragment") //if written, this transaction will be added to backstack
            //   transaction.addToBackStack(null)
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
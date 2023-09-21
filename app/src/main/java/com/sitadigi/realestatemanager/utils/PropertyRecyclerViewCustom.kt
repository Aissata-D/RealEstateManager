package com.sitadigi.realestatemanager.utils

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Bitmap
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import com.sitadigi.realestatemanager.R

class PropertyRecyclerViewCustom @JvmOverloads constructor(context: Context,
                                                           attrs: AttributeSet? = null,
                                                           defStyle: Int = 0):
        ConstraintLayout(context,attrs,defStyle) {
    lateinit var image_thumb: ImageView
    lateinit var text_title: TextView

    init {
        init(attrs)
    }


    private fun init(attrs: AttributeSet?) {
        View.inflate(context, R.layout.custom_recycler_view, this)

         image_thumb = findViewById(R.id.img_property_recyclerview)
         text_title = findViewById(R.id.img_description)

       /* val ta = context.obtainStyledAttributes(attrs, R.styleable.PropertyRecyclerViewCustom)
        try {
            val text = ta.getString(R.styleable.PropertyRecyclerViewCustom_text)
            val drawableId = ta.getResourceId(R.styleable.PropertyRecyclerViewCustom_image, 0)
            if (drawableId != 0) {
               val drawable = AppCompatResources.getDrawable(context, drawableId)
                image_thumb.setImageDrawable(drawable)
            }
            text_title.text = text
        } finally {
            ta.recycle()
        }*/
    }

    fun setImageBitmap(bm: Bitmap?){
        image_thumb.setImageBitmap(bm)
        image_thumb.scaleType = ImageView.ScaleType.CENTER_CROP

    }
    fun setText(text: String ){
        text_title.text = text
        text_title.visibility = View.VISIBLE
       // text_title.setBackgroundColor(resources.getColor(R.color.black_transparent))
       // text_title.background= @res.color. black_transparent
    }

//ImageWithOverlayView
}
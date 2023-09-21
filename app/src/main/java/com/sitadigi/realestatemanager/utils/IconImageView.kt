package com.sitadigi.realestatemanager.utils

import android.content.Context
import android.graphics.Bitmap
import android.util.AttributeSet

class IconImageView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null,
                                              defStyle: Int = 0):
        androidx.appcompat.widget.AppCompatImageView(context, attrs, defStyle) {

    override fun setImageBitmap(bm: Bitmap?) {
        super.setImageBitmap(bm)
        scaleType = ScaleType.CENTER_CROP
    }
}
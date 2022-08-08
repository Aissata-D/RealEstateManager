package com.sitadigi.realestatemanager.model

import androidx.room.ColumnInfo

data class PictureInter (
    var description: String,
    var currentPhotoPath : String,
    var image:  ByteArray
)
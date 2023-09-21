package com.sitadigi.realestatemanager.repository

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.room.Database
import com.sitadigi.realestatemanager.dao.PictureDao
import com.sitadigi.realestatemanager.dao.StatusDao
import com.sitadigi.realestatemanager.database.UserDatabase
import com.sitadigi.realestatemanager.model.Picture
import com.sitadigi.realestatemanager.model.Status

class PictureRepository(private val dao: PictureDao) {
    suspend fun getAllPicture(): List<Picture> {
        return dao.getAllPicture()
    }

    suspend fun insert(picture: Picture) {
        return dao.insert(picture)
    }

    suspend fun getPictureById(id: Int): Picture? {
        return dao.getPictureById(id)
    }
   /* fun setImage(img: Bitmap) {
        val dao = UserDatabase.getInstance(context).PictureDao()
        val picture = Picture()
        picture.image = getBytesFromImageMethod(image)//TODO
        dao.upsertByReplacement(picture)
        // dao.updsertByReplacement(picture)
    }

        fun getImage(): Bitmap? {
            val dao = UserDatabase.getInstance(context).PictureDao()
            val imageByteArray = dao.getAll()
            return loadImageFromBytes(imageByteArray[0].image)
            //change accordingly
        }
    */
    }
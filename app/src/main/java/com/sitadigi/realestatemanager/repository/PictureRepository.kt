package com.sitadigi.realestatemanager.repository

import com.sitadigi.realestatemanager.dao.PictureDao
import com.sitadigi.realestatemanager.model.Picture

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
    suspend fun getPictureLastId(): Int {
        return dao.getPictureLastId()
    }

    suspend fun getListOfPictureByFkId(fk_id : Int): List<Picture> {
        return dao.getListOfPictureByFkId(fk_id)
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
package com.sitadigi.realestatemanager.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sitadigi.realestatemanager.model.Picture
import com.sitadigi.realestatemanager.model.Status
import com.sitadigi.realestatemanager.model.UserEntity

@Dao
interface PictureDao {
    @Insert
    suspend fun insert(picture: Picture)

    @Query("SELECT * FROM Picture_table")
    suspend fun getAllPicture(): List<Picture>

    @Query("SELECT * FROM Picture_table WHERE picture_id LIKE :id")
    suspend fun getPictureById(id: Int): Picture?

    @Query("SELECT picture_id FROM Picture_table ORDER BY picture_id DESC LIMIT 1")
    suspend fun getPictureLastId(): Int

    @Query("SELECT * FROM Picture_table WHERE fk_property_id LIKE :fk_id")
    suspend fun getListOfPictureByFkId(fk_id : Int): List<Picture>

    @Query("DELETE  FROM Picture_table WHERE picture_id LIKE :id")
    suspend fun deletePictureById(id : Int)






/* @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertByReplacement(image: List<Picture>)

    @Query("SELECT * FROM Picture_table")
    fun getAll(): List<Picture>

    @Query("SELECT * FROM Picture_table WHERE picture_id IN (:arg0)")
    fun findByIds(imageTestIds: List<Int>): List<Picture>

    @Delete
    fun delete(imageTest: Picture)*/

}
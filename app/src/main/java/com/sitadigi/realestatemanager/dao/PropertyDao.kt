package com.sitadigi.realestatemanager.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.sitadigi.realestatemanager.model.Picture
import com.sitadigi.realestatemanager.model.Property

@Dao
interface PropertyDao {

        @Insert
        suspend fun insert(property: Property)


        @Query("SELECT * FROM Property_table")
        suspend fun getAllProperty(): List<Property>

        @Query("SELECT * FROM Property_table WHERE property_id LIKE :id")
        suspend fun getPropertyById(id: Int): Property?

        @Query("SELECT property_id from Property_table order by property_id DESC limit 1")
        suspend fun getLastId(): Int
}
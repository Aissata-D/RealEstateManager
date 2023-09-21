package com.sitadigi.realestatemanager.repository

import androidx.room.Query
import com.sitadigi.realestatemanager.dao.PropertyDao
import com.sitadigi.realestatemanager.model.Property

class PropertyRepository(private val dao: PropertyDao) {

    suspend fun insert(property: Property){
        return dao.insert(property)
    }

    suspend fun getAllProperty(): List<Property>{
        return dao.getAllProperty()
    }

    suspend fun getPropertyById(id: Int): Property?{
        return dao.getPropertyById(id)
    }

    suspend fun getLastId(): Int{
        return dao.getLastId()
    }
}
package com.sitadigi.realestatemanager.repository

import androidx.room.Query
import com.google.android.gms.maps.model.LatLng
import com.sitadigi.realestatemanager.dao.PropertyDao
import com.sitadigi.realestatemanager.model.Property
import java.util.*

class PropertyRepository(private val dao: PropertyDao) {

    suspend fun insert(property: Property){
        return dao.insert(property)
    }

    suspend fun getAllProperty(): List<Property>{
        return dao.getAllProperty()
    }

    suspend fun getPropertyById(id: Int): Property{
        return dao.getPropertyById(id)
    }

    suspend fun getLastId(): Int{
        return dao.getLastId()
    }

    suspend fun countPropertyTable(): Int{
        return dao.countPropertyTable()
    }


    suspend fun updatePropertyTable(id: Int, propertyType: String, propertyPrice: Double, propertySurface: Int,
                                    propertyNumberOfRooms: Int, propertyNumberOfBedrooms: Int, propertyNumberOfBathrooms: Int
                                    , propertyDescription : String, propertyAddress: String?, propertyNearbyPointsOfInterest:MutableList<String>
                                    , propertyDateOfRegister : Date?, propertyDateOfSale: Date?, propertyStatusId: Int
                                    , propertyEmailOfRealEstateAgent: String, propertyAdresseComplet : String?, propertyLatlng: LatLng?
    ){
        return dao.updatePropertyTable(id, propertyType,propertyPrice,propertySurface,
            propertyNumberOfRooms,propertyNumberOfBedrooms , propertyNumberOfBathrooms
            ,propertyDescription ,propertyAddress , propertyNearbyPointsOfInterest
        ,propertyDateOfRegister , propertyDateOfSale, propertyStatusId
        ,propertyEmailOfRealEstateAgent, propertyAdresseComplet , propertyLatlng)
    }


}
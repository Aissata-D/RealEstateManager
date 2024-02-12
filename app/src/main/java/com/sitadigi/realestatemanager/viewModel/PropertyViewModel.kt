package com.sitadigi.realestatemanager.viewModel

import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.sitadigi.realestatemanager.model.Property
import com.sitadigi.realestatemanager.repository.PropertyRepository
import com.sitadigi.realestatemanager.repository.StatusRepository
import java.util.*

class PropertyViewModel(private val propertyRepository: PropertyRepository): ViewModel() {

    suspend fun insert(property: Property){
        return propertyRepository.insert(property)
    }

    suspend fun getAllProperty(): List<Property>{
        return propertyRepository.getAllProperty()
    }

    suspend fun getPropertyById(id: Int): Property{
        return propertyRepository.getPropertyById(id)
    }

    suspend fun getLastId(): Int{
        return propertyRepository.getLastId()
    }

    suspend fun  countPropertyTable(): Int{
        return propertyRepository.countPropertyTable()
    }

    suspend fun  updatePropertyTable(id: Int, propertyType: String, propertyPrice: Double, propertySurface: Int,
                                     propertyNumberOfRooms: Int, propertyNumberOfBedrooms: Int, propertyNumberOfBathrooms: Int
                                     , propertyDescription : String, propertyAddress: String?, propertyNearbyPointsOfInterest:MutableList<String>
                                     , propertyDateOfRegister : Date?, propertyDateOfSale: Date?, propertyStatusId: Int
                                     , propertyEmailOfRealEstateAgent: String, propertyAdresseComplet : String?, propertyLatlng: LatLng?
    ){
        return propertyRepository.updatePropertyTable(id, propertyType,propertyPrice,propertySurface,
            propertyNumberOfRooms,propertyNumberOfBedrooms , propertyNumberOfBathrooms
            ,propertyDescription ,propertyAddress , propertyNearbyPointsOfInterest
            ,propertyDateOfRegister , propertyDateOfSale, propertyStatusId
            ,propertyEmailOfRealEstateAgent, propertyAdresseComplet , propertyLatlng)
    }



}
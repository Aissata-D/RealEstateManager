package com.sitadigi.realestatemanager.viewModel

import androidx.lifecycle.ViewModel
import com.sitadigi.realestatemanager.model.Property
import com.sitadigi.realestatemanager.repository.PropertyRepository
import com.sitadigi.realestatemanager.repository.StatusRepository

class PropertyViewModel(private val propertyRepository: PropertyRepository): ViewModel() {

    suspend fun insert(property: Property){
        return propertyRepository.insert(property)
    }

    suspend fun getAllProperty(): List<Property>{
        return propertyRepository.getAllProperty()
    }

    suspend fun getPropertyById(id: Int): Property?{
        return propertyRepository.getPropertyById(id)
    }

    suspend fun getLastId(): Int{
        return propertyRepository.getLastId()
    }

    suspend fun  countPropertyTable(): Int{
        return propertyRepository. countPropertyTable()
    }


}
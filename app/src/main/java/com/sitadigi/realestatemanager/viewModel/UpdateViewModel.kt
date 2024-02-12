package com.sitadigi.realestatemanager.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UpdateViewModel : ViewModel()  {
    private val propertyId = MutableLiveData<Int>()


    fun setPropertyIdToUpdate( idd: Int) {
        propertyId.value = idd
    }
    fun getPropertyIdToUpdate(): Int? {
        return  propertyId.value
    }

}
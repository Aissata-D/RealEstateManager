package com.sitadigi.realestatemanager.viewModelFactory

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sitadigi.realestatemanager.database.UserDatabase
import com.sitadigi.realestatemanager.repository.PropertyRepository
import com.sitadigi.realestatemanager.viewModel.PropertyViewModel

class PropertyViewModelFactory(activity: Activity) : ViewModelProvider.Factory{

    val dao = UserDatabase.getInstance(activity.applicationContext)?.propertyDao!!
    val repository = PropertyRepository(dao)


    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PropertyViewModel::class.java)) {
            return PropertyViewModel(repository ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
package com.sitadigi.realestatemanager.viewModelFactory

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sitadigi.realestatemanager.database.UserDatabase
import com.sitadigi.realestatemanager.repository.LoginRepository
import com.sitadigi.realestatemanager.repository.StatusRepository
import com.sitadigi.realestatemanager.ui.login.LoginViewModel
import com.sitadigi.realestatemanager.viewModel.StatusViewModel

class StatusViewModelFactory(activity: AppCompatActivity) : ViewModelProvider.Factory{

    val dao = UserDatabase.getInstance(activity.applicationContext)?.statusDao!!
    val repository = StatusRepository(dao)


    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StatusViewModel::class.java)) {
            return StatusViewModel(repository ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
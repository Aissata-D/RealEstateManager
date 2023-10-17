package com.sitadigi.realestatemanager.viewModelFactory

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sitadigi.realestatemanager.database.UserDatabase
import com.sitadigi.realestatemanager.repository.LoginRepository
import com.sitadigi.realestatemanager.repository.PictureRepository
import com.sitadigi.realestatemanager.ui.login.LoginViewModel
import com.sitadigi.realestatemanager.viewModel.PictureViewModel

class PictureViewModelFactory(activity: Activity): ViewModelProvider.Factory {
    val dao = UserDatabase.getInstance(activity.applicationContext)?.pictureDao!!
    val repository = PictureRepository(dao)


    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PictureViewModel::class.java)) {
            return PictureViewModel(repository ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
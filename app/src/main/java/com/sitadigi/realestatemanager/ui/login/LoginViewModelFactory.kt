package com.sitadigi.realestatemanager.ui.login

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sitadigi.realestatemanager.repository.LoginRepository
import com.sitadigi.realestatemanager.database.UserDatabase

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
class LoginViewModelFactory(activity: AppCompatActivity) : ViewModelProvider.Factory {

   val dao = UserDatabase.getInstance(activity.applicationContext)?.userDao!!
   val repository = LoginRepository(dao)


    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(repository ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
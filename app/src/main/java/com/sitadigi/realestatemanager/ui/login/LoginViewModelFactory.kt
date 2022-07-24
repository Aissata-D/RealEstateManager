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
class LoginViewModelFactory(/*private val repository: LoginRepository
                            ,*/private val activity: AppCompatActivity) : ViewModelProvider.Factory {

   // private val repository: LoginRepository = TODO()
   // val application = requireNotNull(this).application

   val dao = UserDatabase.getInstance(activity.applicationContext).userDatabaseDao
   val repository = LoginRepository(dao)


     /*fun LoginViewModelFactory(activity: AppCompatActivity) {
        dao = UserDatabase.getInstance(activity.applicationContext).userDatabaseDao
        repository = LoginRepository(dao)
        //val database: SaveMyTripDatabase = SaveMyTripDatabase.getInstance(context)
       // this.itemDataSource = ItemDataRepository(database.itemDao())
       // this.userDataSource = UserDataRepository(database.userDao())
       // this.executor = Executors.newSingleThreadExecutor()
    }*/


    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(repository ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
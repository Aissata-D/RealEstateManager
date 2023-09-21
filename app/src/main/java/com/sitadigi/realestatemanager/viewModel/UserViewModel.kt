package com.sitadigi.realestatemanager.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.sitadigi.realestatemanager.model.UserEntity
import com.sitadigi.realestatemanager.repository.LoginRepository
import com.sitadigi.realestatemanager.repository.UserRepository

class UserViewModel(private val userRepository: UserRepository): ViewModel() {

    suspend fun insert(userEntity: UserEntity){
        return userRepository.insert(userEntity)
    }

    fun getAllUsers(): LiveData<List<UserEntity>> {
        return userRepository.getAllUsers()
    }

    suspend fun getUserName(userName: String): UserEntity?{
        return userRepository.getUserName(userName)
    }

    suspend fun getUserPassword(userPassword: String): UserEntity?{
        return userRepository.getUserPassword(userPassword)
    }

    suspend fun getUserEmailAddress(userEmailAddress: String): UserEntity?{
        return userRepository.getUserEmailAddress(userEmailAddress)
    }
}
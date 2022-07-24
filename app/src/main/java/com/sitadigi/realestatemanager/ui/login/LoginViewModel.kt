package com.sitadigi.realestatemanager.ui.login


import androidx.lifecycle.ViewModel
import com.sitadigi.realestatemanager.repository.LoginRepository
import com.sitadigi.realestatemanager.model.UserEntity

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    val users = loginRepository.users
    suspend fun createUser(user: UserEntity) {
        return loginRepository.createUser(user)
    }

    suspend fun getUserName(userName: String): UserEntity? {
        return loginRepository.getUserName(userName)
    }

    suspend fun getUserPassword(userPassword: String): UserEntity? {
        return loginRepository.getUserPassword(userPassword)
    }

    suspend fun getUserEmailAddress(userEmailAddress: String): UserEntity? {
        return loginRepository.getUserEmailAddress(userEmailAddress)
    }
}

package com.sitadigi.realestatemanager.repository

import androidx.lifecycle.LiveData
import androidx.room.Query
import com.sitadigi.realestatemanager.dao.UserDao
import com.sitadigi.realestatemanager.model.UserEntity

class UserRepository(private val dao: UserDao) {
    suspend fun insert(userEntity: UserEntity){
        return dao.insert(userEntity)
    }

    fun getAllUsers(): LiveData<List<UserEntity>>{
        return dao.getAllUsers()
    }

    suspend fun getUserName(userName: String): UserEntity?{
        return dao.getUserName(userName)
    }

    suspend fun getUserPassword(userPassword: String): UserEntity?{
        return dao.getUserPassword(userPassword)
    }

    suspend fun getUserEmailAddress(userEmailAddress: String): UserEntity?{
        return dao.getUserEmailAddress(userEmailAddress)
    }
}
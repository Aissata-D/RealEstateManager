package com.sitadigi.realestatemanager.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.sitadigi.realestatemanager.model.UserEntity

@Dao
interface UserDao {
    //Create a new user
    @Insert
    suspend fun insert(userEntity: UserEntity)

    @Query("SELECT * FROM Login_users_table")
    fun getAllUsers(): LiveData<List<UserEntity>>

    @Query("SELECT * FROM Login_users_table WHERE user_name LIKE :userName")
    suspend fun getUserName(userName: String): UserEntity?

    @Query("SELECT * FROM Login_users_table WHERE user_password LIKE :userPassword")
    suspend fun getUserPassword(userPassword: String): UserEntity?

    @Query("SELECT * FROM Login_users_table WHERE user_email_address LIKE :userEmailAddress")
    suspend fun getUserEmailAddress(userEmailAddress: String): UserEntity?
}
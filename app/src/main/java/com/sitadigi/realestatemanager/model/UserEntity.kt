package com.sitadigi.realestatemanager.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
@Entity(tableName = "Login_users_table")
data class UserEntity(

        //@ColumnInfo(name = "user_id")
       // val id: Int ,
        @PrimaryKey(autoGenerate = false)
        @ColumnInfo(name = "user_email_address")
        val userEmailAddress: String,
        @ColumnInfo(name = "user_password")
        val userPassword: String,
        @ColumnInfo(name = "user_name")
        val userName: String,
        @ColumnInfo(name = "user_phone_number")
        val userPhoneNumber: String
)
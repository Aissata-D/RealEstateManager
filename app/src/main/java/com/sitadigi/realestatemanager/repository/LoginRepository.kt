package com.sitadigi.realestatemanager.repository

import com.sitadigi.realestatemanager.dao.UserDao
import com.sitadigi.realestatemanager.model.UserEntity

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class LoginRepository(private val dao: UserDao) {


    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        //user = null
    }

    val users = dao.getAllUsers()


    suspend fun createUser(user: UserEntity) {
        return dao.insert(user)
    }

    suspend fun getUserName(userName: String): UserEntity? {
        return dao.getUserName(userName)
    }

    suspend fun getUserPassword(userPassword: String): UserEntity? {
        return dao.getUserPassword(userPassword)
    }

    suspend fun getUserEmailAddress(userEmailAddress: String): UserEntity? {
        return dao.getUserEmailAddress(userEmailAddress)
    }


    fun logout() {
        /*  user = null
          dataSource.logout()*/
    }

    /*fun login(username: String, password: String): Result<UserEntity> {
        // handle login
          val result = dataSource.login(username, password)

          if (result is Result.Success) {
              setLoggedInUser(result.data)
          }

          return result

    }*/

    private fun setLoggedInUser(userEntity: UserEntity) {
        // this.user = userEntity
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }
}
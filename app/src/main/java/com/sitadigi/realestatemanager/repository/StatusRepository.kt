package com.sitadigi.realestatemanager.repository

import com.sitadigi.realestatemanager.dao.StatusDao
import com.sitadigi.realestatemanager.dao.UserDao
import com.sitadigi.realestatemanager.model.Status
import com.sitadigi.realestatemanager.model.UserEntity

class StatusRepository(private val dao: StatusDao) {

    suspend fun insert(status: Status) {
        return dao.insert(status)
    }

    suspend fun getStatusById(id: Int): Status? {
        return dao.getStatusById(id)
    }

}
package com.sitadigi.realestatemanager.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.sitadigi.realestatemanager.model.Status

@Dao
interface StatusDao {
// create a new status
    @Insert
    suspend fun insert(status: Status)

    @Query("SELECT * FROM Status_table WHERE status_id LIKE :id")
    suspend fun getStatusById(id: Int): Status?
}
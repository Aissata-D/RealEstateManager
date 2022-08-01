package com.sitadigi.realestatemanager.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(tableName = "Status_table")
data class Status (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "status_id")
    val id: Int,
    @ColumnInfo(name = "status_available_or_sold")
    val statusAvailableOrSale: String
  //  ,@ColumnInfo(name = "status_sale")
    //val statusSale: String
    )
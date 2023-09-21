package com.sitadigi.realestatemanager.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

/**
 * Data class that captures property information
 */

@Entity(tableName = "Property_table",
        foreignKeys = [ForeignKey(entity = Status::class,
                parentColumns = arrayOf("status_id"),
                childColumns = arrayOf("property_status_id"))])
data class Property(

        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "property_id")
        val id: Int,
        //@ColumnInfo(name = "property_name")
        //val propertyName: String,
        @ColumnInfo(name = "property_type")
        val propertyType: String,
        @ColumnInfo(name = "property_price")
        val propertyPrice: Double,
        @ColumnInfo(name = "property_surface")
        val propertySurface: Int,
        @ColumnInfo(name = "property_number_of_rooms")
        val propertyNumberOfRooms: Int,
        @ColumnInfo(name = "property_description")
        val propertyDescription: String,
        @ColumnInfo(name = "property_address")
        val propertyAddress: String,
        @ColumnInfo(name = "property_nearby_points_of_interest")
        val propertyNearbyPointsOfInterest: List<String>,
        @ColumnInfo(name = "property_date_of_register")
        val propertyDateOfRegister: Date?,
        @ColumnInfo(name = "property_date_of_sale")
        val propertyDateOfSale: Date?,
        @ColumnInfo(name = "property_status_id")
        val propertyStatusId: Int,
        @ColumnInfo(name = "property_email_of_real_estate_agent")
        val propertyEmailOfRealEstateAgent: String,
        @ColumnInfo(name = "property_list_of_pictures")
        val propertyListOfPictures: List<String>

)
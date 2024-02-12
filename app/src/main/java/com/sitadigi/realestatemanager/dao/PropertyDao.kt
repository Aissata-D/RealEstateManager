package com.sitadigi.realestatemanager.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.google.android.gms.maps.model.LatLng
import com.sitadigi.realestatemanager.model.Picture
import com.sitadigi.realestatemanager.model.Property
import java.util.*

@Dao
interface PropertyDao {

        @Insert
        suspend fun insert(property: Property)


        @Query("SELECT * FROM Property_table")
        suspend fun getAllProperty(): List<Property>

        @Query("SELECT * FROM Property_table WHERE property_id LIKE :id")
        suspend fun getPropertyById(id: Int): Property

        @Query("SELECT property_id from Property_table order by property_id DESC limit 1")
        suspend fun getLastId(): Int

        @Query("SELECT COUNT (*) from Property_table")
        suspend fun countPropertyTable(): Int



        @Query("UPDATE Property_table" +
                " SET property_type = :propertyType," +
                " property_price = :propertyPrice," +
                "property_surface = :propertySurface," +
                "property_number_of_rooms = :propertyNumberOfRooms," +
                "property_number_of_bedrooms = :propertyNumberOfBedrooms," +
                " property_number_of_bathrooms = :propertyNumberOfBathrooms," +
                "property_description = :propertyDescription," +
                "property_address = :propertyAddress, " +
                "property_nearby_points_of_interest = :propertyNearbyPointsOfInterest, " +
                "property_date_of_register = :propertyDateOfRegister," +
                "property_date_of_sale = :propertyDateOfSale," +
                "property_status_id = :propertyStatusId," +
                "property_email_of_real_estate_agent = :propertyEmailOfRealEstateAgent," +
                "property_adresse_complete = :propertyAdresseComplete," +
                "property_latlng = :propertyLatlng WHERE property_id = :id" )

        suspend fun updatePropertyTable( id: Int, propertyType: String,propertyPrice: Double,propertySurface: Int,
                                         propertyNumberOfRooms: Int,propertyNumberOfBedrooms: Int , propertyNumberOfBathrooms: Int
                                         ,propertyDescription : String,propertyAddress: String? , propertyNearbyPointsOfInterest:MutableList<String>
                                         ,propertyDateOfRegister : Date?, propertyDateOfSale: Date?, propertyStatusId: Int
                                         ,propertyEmailOfRealEstateAgent: String, propertyAdresseComplete : String?, propertyLatlng: LatLng?)

}





package com.sitadigi.realestatemanager.model

import androidx.room.ColumnInfo
import androidx.room.Entity

import androidx.room.PrimaryKey
import java.sql.Blob


@Entity(tableName = "Picture_table" ,foreignKeys = [androidx.room.ForeignKey(
        entity = com.sitadigi.realestatemanager.model.Property::class,
        parentColumns = kotlin.arrayOf("property_id"),
        childColumns = kotlin.arrayOf("fk_property_id"))])
data class Picture (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "picture_id")
    var id: Int ,

    @ColumnInfo(name = "picture_description")
    var description: String,

    //@ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    @ColumnInfo(name = "picture_image")
     var image:  ByteArray,

    @ColumnInfo(name = "fk_property_id")
    var fkPropertyId :Int



    ) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Picture

        if (id != other.id) return false
        if (description != other.description) return false
        if (!image.contentEquals(other.image)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + description.hashCode()
        result = 31 * result + image.contentHashCode()
        return result
    }
}
package com.sitadigi.realestatemanager.utils

import androidx.room.TypeConverter
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.sitadigi.realestatemanager.model.Picture
import com.sitadigi.realestatemanager.model.PictureInter
import org.w3c.dom.Comment
import java.util.*

class Converters {


        @TypeConverter
        fun fromGroupTaskMemberList(value: List<Picture>): String {
            val gson = Gson()
            val type = object : TypeToken<List<Picture>>() {}.type
            return gson.toJson(value, type)
        }

        @TypeConverter
        fun toGroupTaskMemberList(value: String): List<Picture> {
            val gson = Gson()
            val type = object : TypeToken<List<Picture>>() {}.type
            return gson.fromJson(value, type)
        }

    @TypeConverter
    fun fromGroupTaskMemberList1(value: List<PictureInter>): String {
        val gson = Gson()
        val type = object : TypeToken<List<PictureInter>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toGroupTaskMemberList1(value: String): List<PictureInter> {
        val gson = Gson()
        val type = object : TypeToken<List<PictureInter>>() {}.type
        return gson.fromJson(value, type)
    }
    @TypeConverter
    fun fromGroupTaskMemberList2(value: List<String>): String {
        val gson = Gson()
        val type = object : TypeToken<List<String>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toGroupTaskMemberList2(value: String): List<String> {
        val gson = Gson()
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, type)
    }



    @TypeConverter
    fun toDate(timestamp: Long?): Date? {
        return timestamp?.let { Date(it) }
    }

    @TypeConverter
    fun toTimestamp(date: Date?): Long? {
        return date?.getTime()
    }
}
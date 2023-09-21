package com.sitadigi.realestatemanager.database

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.sitadigi.realestatemanager.dao.PictureDao
import com.sitadigi.realestatemanager.dao.PropertyDao
import com.sitadigi.realestatemanager.dao.StatusDao
import com.sitadigi.realestatemanager.dao.UserDao
import com.sitadigi.realestatemanager.database.UserDatabase.Companion.uiScope
import com.sitadigi.realestatemanager.model.Picture
import com.sitadigi.realestatemanager.model.Property

import com.sitadigi.realestatemanager.model.Status
import com.sitadigi.realestatemanager.model.UserEntity
import com.sitadigi.realestatemanager.utils.Converters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

@Database(entities = [UserEntity::class, Status::class, Picture::class, Property::class],
        version = 7, exportSchema = false)
@TypeConverters(Converters::class)
abstract class UserDatabase : RoomDatabase() {


        abstract val userDao: UserDao
        abstract val pictureDao: PictureDao
    abstract val propertyDao: PropertyDao

    //abstract val statusDao: StatusDao
         abstract val statusDao: StatusDao
        //abstract fun pictureDao(): PictureDao

   // @TypeConverters(DataConverters::class)



    companion object {
            private val viewModelJob = SupervisorJob()
            private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)


            @Volatile
            private var INSTANCE: UserDatabase? = null

            fun getInstance(context: Context?): UserDatabase? {
                synchronized(this) {

                    var instance = INSTANCE

                    if (instance == null) {
                        if (context != null) {
                            instance = Room.databaseBuilder(
                                    context.applicationContext,
                                    UserDatabase::class.java,
                                    "database12"
                            ).addCallback(prepopulateDatabase())
                                    .build()
                        }

                        INSTANCE = instance
                    }
                    return instance
                }
            }
       /* fun setImage(img: Bitmap){
            val dao = getInstance(context).getImageTestDao()
            val imageTest = ImageTest()
            imageTest.data = getBytesFromImageMethod(image)//TODO
            dao.updsertByReplacement(imageTest)

            fun getImage():Bitmap?{
                val dao = DatabaseHelper.instance.getImageTestDao()
                val imageByteArray = dao.getAll()
                return loadImageFromBytes(imageByteArray[0].data)
                //change accordingly
            }*/

    private fun prepopulateDatabase(): RoomDatabase.Callback {
        return object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
             super.onCreate(db)
               // getInstance(context).dataDao().insertData(PREPOPULATE_DATA)

                uiScope.launch {

                    INSTANCE?.statusDao?.insert(Status(1, "AVAILABLE"))
                    INSTANCE?.statusDao?.insert(Status(2, "SOLD"))

                    INSTANCE?.userDao?.insert(UserEntity("d","d",
                            "Aissata","0606060606"))

                }
      /*       val contentValuesStatusAvailable = ContentValues()
            contentValuesStatusAvailable.put("status_id", 1)
            contentValuesStatusAvailable.put("status_available_or_sold", "Available")
                val contentValuesStatusSold = ContentValues()
                contentValuesStatusAvailable.put("status_id", 2)
                contentValuesStatusAvailable.put("status_available_or_sale", "Sold")
*/
          //  db.insert("status_table",OnConflictStrategy.IGNORE,contentValuesStatusAvailable)
            //    db.insert("status_table",OnConflictStrategy.IGNORE,contentValuesStatusSold)
              //  var statusDao : StatusDao
                //statusDao.insert(Status(1))
                //db.insert("User", OnConflictStrategy.IGNORE, contentValues)
            }
           }
       }
}
}
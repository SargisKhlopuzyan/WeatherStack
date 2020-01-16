package app.sargis.khlopuzyan.weatherstack.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import app.sargis.khlopuzyan.weatherstack.database.dao.CurrentDAO
import app.sargis.khlopuzyan.weatherstack.model.Current

/**
 * Created by Sargis Khlopuzyan, on 1/15/2020.
 *
 * @author Sargis Khlopuzyan (sargis.khlopuzyan@fcc.am)
 */
//@TypeConverters(
//    ImageListConverter::class,
//    TrackListConverter::class,
//    ArtistConverter::class,
//    ItemDatabaseStateConverter::class
//)
@Database(entities = [Current::class], version = 1, exportSchema = false)
abstract class CurrentDatabase : RoomDatabase() {

    abstract fun getCurrentDAO(): CurrentDAO

    companion object {

        @Volatile
        private var INSTANCE: CurrentDatabase? = null

        fun getInstance(context: Context): CurrentDatabase {

            if (INSTANCE == null) {

                synchronized(CurrentDatabase::class) {

                    if (INSTANCE == null) {

                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            CurrentDatabase::class.java, "current.db"
                        )
                            .allowMainThreadQueries()
                            .build()
                    }
                }
            }
            return INSTANCE!!
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}
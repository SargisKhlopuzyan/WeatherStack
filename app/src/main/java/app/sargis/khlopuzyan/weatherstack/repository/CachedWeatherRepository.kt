package app.sargis.khlopuzyan.weatherstack.repository

import androidx.lifecycle.LiveData
import app.sargis.khlopuzyan.weatherstack.database.DatabaseManager
import app.sargis.khlopuzyan.weatherstack.model.Current

/**
 * Created by Sargis Khlopuzyan, on 1/15/2020.
 *
 * @author Sargis Khlopuzyan (sargis.khlopuzyan@fcc.am)
 */
interface CachedWeatherRepository {
    fun saveWeatherInCache(current: Current): Long
    fun deleteWeatherFromCache(current: Current): Int
    fun getAllCachedWeathersLiveData(): LiveData<List<Current>?>
}

/**
 * Repository implementation for doing caching queries.
 */
class CachedWeatherRepositoryImpl(
    private val databaseManager: DatabaseManager
) : CachedWeatherRepository {

    override fun saveWeatherInCache(current: Current): Long {
        return databaseManager.saveWeatherInDatabase(current)
    }

    override fun deleteWeatherFromCache(current: Current): Int {
        return databaseManager.deleteWeatherFromDatabase(current)
    }

    override fun getAllCachedWeathersLiveData(): LiveData<List<Current>?> {
        return databaseManager.getAllCachedWeathersLiveDataFromDatabase()
    }

}
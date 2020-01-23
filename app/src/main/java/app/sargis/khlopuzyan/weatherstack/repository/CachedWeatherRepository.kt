package app.sargis.khlopuzyan.weatherstack.repository

import androidx.lifecycle.LiveData
import app.sargis.khlopuzyan.weatherstack.database.DatabaseManager
import app.sargis.khlopuzyan.weatherstack.model.Current
import app.sargis.khlopuzyan.weatherstack.model.ResultWeather
import app.sargis.khlopuzyan.weatherstack.networking.api.ApiService
import app.sargis.khlopuzyan.weatherstack.networking.callback.Result
import app.sargis.khlopuzyan.weatherstack.networking.helper.getResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext

/**
 * Created by Sargis Khlopuzyan, on 1/15/2020.
 *
 * @author Sargis Khlopuzyan (sargis.khlopuzyan@fcc.am)
 */
interface CachedWeatherRepository {
    fun saveWeatherInCache(current: Current): Long
    fun updateWeathersInDatabase(currents: List<Current>): Int
    fun updateWeatherInDatabase(current: Current): Int
    fun deleteWeatherFromCache(current: Current): Int
    fun getAllCachedWeathers(): List<Current>
    fun getAllCachedWeathersLiveData(): LiveData<List<Current>?>

    suspend fun searchCurrentWeather(location: String): Result<ResultWeather>
}

/**
 * Repository implementation for doing caching queries.
 */
class CachedWeatherRepositoryImpl(
    private val databaseManager: DatabaseManager,
    private val apiService: ApiService,
    private val coroutineScope: CoroutineScope
) : CachedWeatherRepository {

    override fun saveWeatherInCache(current: Current): Long {
        return databaseManager.saveWeatherInDatabase(current)
    }

    override fun updateWeathersInDatabase(currents: List<Current>): Int {
        return databaseManager.updateWeathersInDatabase(currents)
    }

    override fun updateWeatherInDatabase(current: Current): Int {
        return databaseManager.updateWeatherInDatabase(current)
    }

    override fun deleteWeatherFromCache(current: Current): Int {
        return databaseManager.deleteWeatherFromDatabase(current)
    }

    override fun getAllCachedWeathers(): List<Current> {
        return databaseManager.getAllCachedWeathersFromDatabase()
    }

    override fun getAllCachedWeathersLiveData(): LiveData<List<Current>?> {
        return databaseManager.getAllCachedWeathersLiveDataFromDatabase()
    }

    override suspend fun searchCurrentWeather(location: String): Result<ResultWeather> =
        withContext(coroutineScope.coroutineContext) {
            try {
                return@withContext apiService.getCurrentWeatherData(query = location).getResult()
            } catch (ex: Exception) {
                return@withContext Result.Failure(ex)
            }
        }

}
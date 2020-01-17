package app.sargis.khlopuzyan.weatherstack.repository

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
interface WeatherSearchRepository {
    suspend fun searchCurrentWeather(location: String): Result<ResultWeather>
    fun saveWeatherInCache(current: Current): Long
}

/**
 * Repository implementation for doing caching queries.
 */
class WeatherSearchRepositoryImpl(
    private val databaseManager: DatabaseManager,
    private val apiService: ApiService,
    private val coroutineScope: CoroutineScope
) : WeatherSearchRepository {

    override suspend fun searchCurrentWeather(location: String): Result<ResultWeather> =
        withContext(coroutineScope.coroutineContext) {
            try {
                return@withContext apiService.getCurrentWeatherData(query = location).getResult()
            } catch (ex: Exception) {
                return@withContext Result.Failure(ex)
            }
        }

    override fun saveWeatherInCache(current: Current): Long {
        return databaseManager.saveWeatherInDatabase(current)
    }
}
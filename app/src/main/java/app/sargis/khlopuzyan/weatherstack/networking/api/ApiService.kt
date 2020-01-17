package app.sargis.khlopuzyan.weatherstack.networking.api

import app.sargis.khlopuzyan.weatherstack.model.ResultWeather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

/**
 * Created by Sargis Khlopuzyan, on 1/15/2020.
 *
 * @author Sargis Khlopuzyan (sargis.khlopuzyan@fcc.am)
 */
interface ApiService {

    @GET("current?")
    suspend fun getCurrentWeatherData(
        @Query(value = "query") query: String
    ): Response<ResultWeather>

}
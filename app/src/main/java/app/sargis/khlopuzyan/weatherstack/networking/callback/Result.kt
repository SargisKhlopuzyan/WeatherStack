package app.sargis.khlopuzyan.weatherstack.networking.callback

import okhttp3.ResponseBody

/**
 * Created by Sargis Khlopuzyan, on 1/15/2020.
 *
 * @author Sargis Khlopuzyan (sargis.khlopuzyan@fcc.am)
 */
sealed class Result<out T> {
    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Error(val errorCode: Int, val responseBody: ResponseBody?) : Result<Nothing>()
    data class Failure(val throwable: Throwable?) : Result<Nothing>()
}
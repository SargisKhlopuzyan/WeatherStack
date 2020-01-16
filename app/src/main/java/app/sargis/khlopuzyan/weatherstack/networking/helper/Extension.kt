package app.sargis.khlopuzyan.weatherstack.networking.helper

import retrofit2.Response
import app.sargis.khlopuzyan.weatherstack.networking.callback.Result

/**
 * Created by Sargis Khlopuzyan, on 1/15/2020.
 *
 * @author Sargis Khlopuzyan (sargis.khlopuzyan@fcc.am)
 */
fun <T : Any> Response<T>.getResult(): Result<T> {
    return if (this.isSuccessful) {
        val body = this.body()
        if (body != null) {
            Result.Success(body)
        } else {
            Result.Error(this.code(), this.errorBody())
        }
    } else {
        Result.Error(this.code(), this.errorBody())
    }
}
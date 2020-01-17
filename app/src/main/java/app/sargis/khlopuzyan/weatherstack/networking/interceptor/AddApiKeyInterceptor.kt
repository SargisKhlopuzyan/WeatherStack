package app.sargis.khlopuzyan.weatherstack.networking.interceptor

import app.sargis.khlopuzyan.weatherstack.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

/**
 * Created by Sargis Khlopuzyan, on 1/15/2020.
 *
 * @author Sargis Khlopuzyan (sargis.khlopuzyan@fcc.am)
 */
class AddApiKeyInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val httpUrl = original.url
        val newUrl = httpUrl.newBuilder()
            .addQueryParameter("access_key", accessKey)
            .build()
        val newRequest = original.newBuilder()
            .url(newUrl)
            .build()
        return chain.proceed(newRequest)
    }

    companion object {
        private const val accessKey = BuildConfig.access_key
    }

}
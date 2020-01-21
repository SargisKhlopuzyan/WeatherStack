package app.sargis.khlopuzyan.weatherstack.networking.retrofit

import app.sargis.khlopuzyan.weatherstack.BuildConfig
import app.sargis.khlopuzyan.weatherstack.database.converter.ListConverter
import app.sargis.khlopuzyan.weatherstack.networking.interceptor.AddApiKeyInterceptor
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by Sargis Khlopuzyan, on 1/15/2020.
 *
 * @author Sargis Khlopuzyan (sargis.khlopuzyan@fcc.am)
 */
class NetworkService {

    companion object {

        private const val BASE_URL = "http://api.weatherstack.com/"

        fun initOkHttpClient(addApiKeyInterceptor: AddApiKeyInterceptor): OkHttpClient {
            return OkHttpClient.Builder().apply {
                readTimeout(60, TimeUnit.SECONDS)
                connectTimeout(60, TimeUnit.SECONDS)
                addInterceptor(addApiKeyInterceptor)

                // interceptor for logging
                if (BuildConfig.DEBUG) {
                    val logging = HttpLoggingInterceptor()
                    logging.level = HttpLoggingInterceptor.Level.BASIC
                    addInterceptor(logging)
                }
            }.build()
        }

        fun initRetrofit(okHttpClient: OkHttpClient): Retrofit =
            Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(
                    GsonConverterFactory.create()
//                    MoshiConverterFactory.create(
//                        Moshi.Builder()
//                            .build()
//                    )
                )
                .build()
    }
}
package app.sargis.khlopuzyan.weatherstack.di.module

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.sargis.khlopuzyan.weatherstack.database.DatabaseManager
import app.sargis.khlopuzyan.weatherstack.di.factory.AppViewModelFactory
import app.sargis.khlopuzyan.weatherstack.networking.api.ApiService
import app.sargis.khlopuzyan.weatherstack.networking.interceptor.AddApiKeyInterceptor
import app.sargis.khlopuzyan.weatherstack.networking.retrofit.NetworkService
import app.sargis.khlopuzyan.weatherstack.repository.CachedWeatherRepository
import app.sargis.khlopuzyan.weatherstack.repository.CachedWeatherRepositoryImpl
import app.sargis.khlopuzyan.weatherstack.repository.WeatherSearchRepository
import app.sargis.khlopuzyan.weatherstack.repository.WeatherSearchRepositoryImpl
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.create
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Provider
import javax.inject.Singleton

/**
 * Created by Sargis Khlopuzyan, on 1/15/2020.
 *
 * @author Sargis Khlopuzyan (sargis.khlopuzyan@fcc.am)
 */
@Module(includes = [AppModule.ProvideViewModel::class])
abstract class AppModule {

    @Module
    class ProvideViewModel {

        @Provides
        @Singleton
        fun provideExecutor(): Executor = Executors.newFixedThreadPool(2)

        @Provides
        @Singleton
        fun provideOkHttpClient(addApiKeyInterceptor: AddApiKeyInterceptor): OkHttpClient =
            NetworkService.initOkHttpClient(addApiKeyInterceptor)

        @Provides
        @Singleton
        fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
            NetworkService.initRetrofit(okHttpClient)

        @Provides
        @Singleton
        fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create()

        @Provides
        @Singleton
        fun provideDatabaseManager(
            context: Context
        ): DatabaseManager = DatabaseManager(context)

        @Provides
        fun provideViewModelFactory(
            providers: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
        ): ViewModelProvider.Factory = AppViewModelFactory(providers)

        @Provides
        @Singleton
        fun provideCachedWeatherRepository(
            databaseManager: DatabaseManager,
            apiService: ApiService
        ): CachedWeatherRepository =
            CachedWeatherRepositoryImpl(
                databaseManager,
                apiService,
                CoroutineScope(Job() + Dispatchers.IO)
            )

        @Provides
        @Singleton
        fun provideWeatherSearchRepository(
            databaseManager: DatabaseManager,
            apiService: ApiService
        ): WeatherSearchRepository =
            WeatherSearchRepositoryImpl(
                databaseManager,
                apiService,
                CoroutineScope(Job() + Dispatchers.IO)
            )
    }
}
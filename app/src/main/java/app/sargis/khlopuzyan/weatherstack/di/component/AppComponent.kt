package app.sargis.khlopuzyan.weatherstack.di.component

import android.content.Context
import app.sargis.khlopuzyan.weatherstack.App
import app.sargis.khlopuzyan.weatherstack.di.module.AppModule
import app.sargis.khlopuzyan.weatherstack.di.module.CachedWeatherModule
import app.sargis.khlopuzyan.weatherstack.di.module.WeatherSearchModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Created by Sargis Khlopuzyan, on 1/15/2020.
 *
 * @author Sargis Khlopuzyan (sargis.khlopuzyan@fcc.am)
 */
@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        CachedWeatherModule::class,
        WeatherSearchModule::class,
        AppModule::class
    ]
)
interface AppComponent : AndroidInjector<App> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Context): AppComponent
    }

}
package app.sargis.khlopuzyan.weatherstack.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.sargis.khlopuzyan.weatherstack.di.annotation.ViewModelKey
import app.sargis.khlopuzyan.weatherstack.repository.CachedWeatherRepository
import app.sargis.khlopuzyan.weatherstack.ui.cachedweather.CachedWeatherFragment
import app.sargis.khlopuzyan.weatherstack.ui.cachedweather.CachedWeatherViewModel
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

/**
 * Created by Sargis Khlopuzyan, on 1/16/2020.
 *
 * @author Sargis Khlopuzyan (sargis.khlopuzyan@fcc.am)
 */
@Module(includes = [CachedWeatherModule.ProvideViewModel::class])
interface CachedWeatherModule {

    @ContributesAndroidInjector(modules = [InjectViewModel::class])
    fun bind(): CachedWeatherFragment

    @Module
    class ProvideViewModel {
        @Provides
        @IntoMap
        @ViewModelKey(CachedWeatherViewModel::class)
        fun provideCachedWeatherViewModel(
            cachedWeatherRepository: CachedWeatherRepository
        ): ViewModel = CachedWeatherViewModel(cachedWeatherRepository)
    }

    @Module
    class InjectViewModel {
        @Provides
        fun provideCachedWeatherViewModel(
            factory: ViewModelProvider.Factory,
            target: CachedWeatherFragment
        ) = ViewModelProvider(target, factory)[CachedWeatherViewModel::class.java]
    }

}
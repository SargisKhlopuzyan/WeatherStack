package app.sargis.khlopuzyan.weatherstack.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.sargis.khlopuzyan.weatherstack.di.annotation.ViewModelKey
import app.sargis.khlopuzyan.weatherstack.repository.WeatherSearchRepository
import app.sargis.khlopuzyan.weatherstack.ui.search.WeatherSearchFragment
import app.sargis.khlopuzyan.weatherstack.ui.search.WeatherSearchViewModel
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

/**
 * Created by Sargis Khlopuzyan, on 1/16/2020.
 *
 * @author Sargis Khlopuzyan (sargis.khlopuzyan@fcc.am)
 */
@Module(includes = [WeatherSearchModule.ProvideViewModel::class])
interface WeatherSearchModule {

    @ContributesAndroidInjector(modules = [InjectViewModel::class])
    fun bind(): WeatherSearchFragment

    @Module
    class ProvideViewModel {
        @Provides
        @IntoMap
        @ViewModelKey(WeatherSearchViewModel::class)
        fun provideSearchViewModel(
            weatherSearchRepository: WeatherSearchRepository
        ): ViewModel = WeatherSearchViewModel(weatherSearchRepository)
    }

    @Module
    class InjectViewModel {
        @Provides
        fun provideSearchViewModel(
            factory: ViewModelProvider.Factory,
            target: WeatherSearchFragment
        ) = ViewModelProvider(target, factory)[WeatherSearchViewModel::class.java]
    }

}
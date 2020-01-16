package app.sargis.khlopuzyan.weatherstack

import android.content.Context
import app.sargis.khlopuzyan.weatherstack.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber

/**
 * Created by Sargis Khlopuzyan, on 1/16/2020.
 *
 * @author Sargis Khlopuzyan (sargis.khlopuzyan@fcc.am)
 */
class App : DaggerApplication() {

    init {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        context = applicationContext
//        Lingver.init(this, Locale.ENGLISH)
        return DaggerAppComponent.factory()
            .create(this)
    }

    companion object {

        private lateinit var context: Context
        fun getContext(): Context {
            return context
        }
    }
}
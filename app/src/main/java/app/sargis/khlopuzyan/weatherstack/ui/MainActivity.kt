package app.sargis.khlopuzyan.weatherstack.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import app.sargis.khlopuzyan.weatherstack.R
import app.sargis.khlopuzyan.weatherstack.ui.cachedweather.CachedWeatherFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                replace(
//                    android.R.id.content,
                    R.id.content,
                    CachedWeatherFragment.newInstance(),
                    "fragment_cached_albums"
                )
            }
        }
    }

}
package app.sargis.khlopuzyan.weatherstack.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import app.sargis.khlopuzyan.weatherstack.R
import app.sargis.khlopuzyan.weatherstack.databinding.FragmentWeatherSearchBinding
import app.sargis.khlopuzyan.weatherstack.ui.common.DaggerFragmentX
import javax.inject.Inject

class WeatherSearchFragment : DaggerFragmentX() {

    @Inject
    lateinit var viewModelWeather: WeatherSearchViewModel

    private lateinit var binding: FragmentWeatherSearchBinding

    companion object {
        fun newInstance() = WeatherSearchFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_weather_search, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModelWeather = ViewModelProviders.of(this).get(WeatherSearchViewModel::class.java)
        // TODO: Use the ViewModel
    }

}

package app.sargis.khlopuzyan.weatherstack.ui.weathersearch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import app.sargis.khlopuzyan.weatherstack.R
import app.sargis.khlopuzyan.weatherstack.databinding.FragmentWeatherSearchBinding
import app.sargis.khlopuzyan.weatherstack.ui.common.DaggerFragmentX
import javax.inject.Inject

class WeatherSearchFragment : DaggerFragmentX() {

    companion object {
        private const val ARG_REQUEST_FOCUS = "arg_request_focus"
        fun newInstance(requestFocus: Boolean = false) = WeatherSearchFragment().apply {
            arguments = Bundle().apply {
                putBoolean(ARG_REQUEST_FOCUS, requestFocus)
            }
        }
    }

    @Inject
    lateinit var viewModel: WeatherSearchViewModel

    private lateinit var binding: FragmentWeatherSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_weather_search, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.viewModel = viewModel
    }

}

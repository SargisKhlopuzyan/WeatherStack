package app.sargis.khlopuzyan.weatherstack.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.sargis.khlopuzyan.weatherstack.R


class CachedWeatherFragment : Fragment() {

    companion object {
        fun newInstance() = CachedWeatherFragment()
    }

    private lateinit var viewModel: CachedWeatherModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cached_weather, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CachedWeatherModel::class.java)
        // TODO: Use the ViewModel
    }

}

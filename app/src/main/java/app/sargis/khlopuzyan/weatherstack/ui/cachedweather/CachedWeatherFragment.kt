package app.sargis.khlopuzyan.weatherstack.ui.cachedweather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import app.sargis.khlopuzyan.weatherstack.R
import app.sargis.khlopuzyan.weatherstack.databinding.FragmentCachedWeatherBinding
import app.sargis.khlopuzyan.weatherstack.ui.common.DaggerFragmentX
import app.sargis.khlopuzyan.weatherstack.ui.weathersearch.WeatherSearchFragment
import javax.inject.Inject


class CachedWeatherFragment : DaggerFragmentX() {

    companion object {
        fun newInstance() = CachedWeatherFragment()
    }

    @Inject
    lateinit var viewModel: CachedWeatherViewModel

    private lateinit var binding: FragmentCachedWeatherBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_cached_weather, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.viewModel = viewModel

        setupToolbar()
        setupRecyclerView()
        setupObservers()
    }

    private fun setupToolbar() {
        binding.toolbar.title = ""
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(context)

        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.hasFixedSize()
        val adapter = CachedWeatherAdapter(
            viewModel
        )
        binding.recyclerView.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.openWeatherSearchLiveData.observe(this) {
            openWeatherSearchScreen()
        }

        viewModel.openEditScreenLiveData.observe(this) {
            openEditScreen()
        }
    }

    private fun openWeatherSearchScreen() {
        activity?.supportFragmentManager?.commit {
            replace(
                R.id.content,
                WeatherSearchFragment.newInstance(requestFocus = true),
                "weather_search_fragment"
            ).addToBackStack("weather_search")
        }
    }

    private fun openEditScreen(
    ) {
        /**
        activity?.supportFragmentManager?.commit {
            replace(
                android.R.id.content,
                AlbumDetailsFragment.newInstance(),
                "fragment_album_details"
            )
            addToBackStack("album_details")
        }
         */
    }
}
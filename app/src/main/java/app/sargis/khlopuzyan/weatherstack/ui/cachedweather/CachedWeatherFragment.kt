package app.sargis.khlopuzyan.weatherstack.ui.cachedweather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import androidx.lifecycle.observe
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import app.sargis.khlopuzyan.weatherstack.R
import app.sargis.khlopuzyan.weatherstack.databinding.FragmentCachedWeatherBinding
import app.sargis.khlopuzyan.weatherstack.model.Current
import app.sargis.khlopuzyan.weatherstack.ui.common.DaggerFragmentX
import app.sargis.khlopuzyan.weatherstack.ui.weathersearch.WeatherSearchFragment
import app.sargis.khlopuzyan.weatherstack.util.StateMode
import javax.inject.Inject


class CachedWeatherFragment : DaggerFragmentX() {

    companion object {
        fun newInstance() = CachedWeatherFragment()
        const val TARGET_FRAGMENT_REQUEST_CODE = 0
    }

    @Inject
    lateinit var viewModel: CachedWeatherViewModel

    private lateinit var binding: FragmentCachedWeatherBinding

    private var touchHelper: ItemTouchHelper? = null

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
        setRecyclerViewAnimationState()
        setupObservers()
    }

    private fun setupToolbar() {
        binding.toolbar.title = ""
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)

        binding.toolbar.setNavigationOnClickListener {
            viewModel.onNavigationClick(it)
        }
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(context)

        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.hasFixedSize()
        val adapter = CachedWeatherAdapter(
            viewModel
        )
        binding.recyclerView.adapter = adapter

        val callback: ItemTouchHelper.Callback =
            SimpleItemTouchHelperCallback(
                adapter
            )
        touchHelper = ItemTouchHelper(callback)
    }

    private fun setupObservers() {
        viewModel.openWeatherSearchLiveData.observe(this) {
            openWeatherSearchScreen()
        }

        viewModel.updateEditModeLiveData.observe(this) {
            setRecyclerViewAnimationState()
            updateCachedWeathersList()
        }

        viewModel.updateListElementLiveData.observe(this) {
            binding.recyclerView.adapter?.notifyItemChanged(it)
        }
    }

    private fun setRecyclerViewAnimationState() {
        if (viewModel.stateModeLiveData.value == StateMode.Normal) {
            touchHelper?.attachToRecyclerView(null)
        } else {
            touchHelper?.attachToRecyclerView(binding.recyclerView)
        }
    }

    private fun openWeatherSearchScreen() {

        val weatherSearchFragment = WeatherSearchFragment.newInstance(
            requestFocus = true,
            cachedSize = viewModel.getCachedWeathersSize()
        )

        weatherSearchFragment.setTargetFragment(this, TARGET_FRAGMENT_REQUEST_CODE)

        activity?.supportFragmentManager?.commit {
            replace(
                R.id.content,
                weatherSearchFragment,
                "weather_search_fragment"
            ).addToBackStack("weather_search")
        }
    }

    private fun updateCachedWeathersList() {
        binding.recyclerView.adapter?.notifyDataSetChanged()
    }

    fun saveWeatherInCachedList(current: Current) {
        viewModel.saveWeatherInCachedList(current)
    }
}
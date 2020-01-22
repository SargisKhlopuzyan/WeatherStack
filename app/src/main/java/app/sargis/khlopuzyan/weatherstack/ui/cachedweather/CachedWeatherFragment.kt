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
import app.sargis.khlopuzyan.weatherstack.R
import app.sargis.khlopuzyan.weatherstack.databinding.FragmentCachedWeatherBinding
import app.sargis.khlopuzyan.weatherstack.model.Current
import app.sargis.khlopuzyan.weatherstack.ui.common.DaggerFragmentX
import app.sargis.khlopuzyan.weatherstack.ui.weathersearch.ItemInteractionInterface
import app.sargis.khlopuzyan.weatherstack.ui.weathersearch.SimpleItemTouchHelperCallback
import app.sargis.khlopuzyan.weatherstack.ui.weathersearch.WeatherSearchFragment
import app.sargis.khlopuzyan.weatherstack.util.StateMode
import javax.inject.Inject


class CachedWeatherFragment : DaggerFragmentX(), ItemInteractionInterface {

    companion object {
        fun newInstance() = CachedWeatherFragment()
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
            viewModel, this
        )
        binding.recyclerView.adapter = adapter


        val callback: ItemTouchHelper.Callback = SimpleItemTouchHelperCallback(adapter)
        touchHelper = ItemTouchHelper(callback)
    }

    private fun setupObservers() {
        viewModel.openWeatherSearchLiveData.observe(this) {
            openWeatherSearchScreen()
        }

        viewModel.enableEditModeLiveData.observe(this) {
            enableEditMode()
            setRecyclerViewAnimationState()
        }
    }

    private fun setRecyclerViewAnimationState() {
        if(viewModel.stateModeLiveData.value == StateMode.Normal) {
            touchHelper?.attachToRecyclerView(null)
        } else {
            touchHelper?.attachToRecyclerView(binding.recyclerView)
        }
    }

    private fun openWeatherSearchScreen() {
        activity?.supportFragmentManager?.commit {
            replace(
                R.id.content,
                WeatherSearchFragment.newInstance(requestFocus = true, cachedSize = viewModel.getCachedWeathersSize()),
                "weather_search_fragment"
            ).addToBackStack("weather_search")
        }
    }

    private fun enableEditMode(
    ) {
        binding.recyclerView.adapter?.notifyDataSetChanged()
    }



    override fun onCachedWeatherItemDismiss(
        currents: List<Current?>?,
        deletedCurrent: Current?,
        position: Int
    ) {

    }

    override fun onCachedWeatherItemMoved(fromPosition: Int, toPosition: Int) {
    }

    override fun onCachedWeatherItemSelectedStateChanged(
        current: Current?,
        itemsSize: Int,
        position: Int,
        isSelected: Boolean?
    ) {
    }
}
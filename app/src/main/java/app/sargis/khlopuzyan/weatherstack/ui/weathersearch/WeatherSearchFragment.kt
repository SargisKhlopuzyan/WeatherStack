package app.sargis.khlopuzyan.weatherstack.ui.weathersearch

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import app.sargis.khlopuzyan.weatherstack.R
import app.sargis.khlopuzyan.weatherstack.databinding.FragmentWeatherSearchBinding
import app.sargis.khlopuzyan.weatherstack.model.Current
import app.sargis.khlopuzyan.weatherstack.ui.common.DaggerFragmentX
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class WeatherSearchFragment : DaggerFragmentX() {

    companion object {
        private const val ARG_REQUEST_FOCUS = "arg_request_focus"
        private const val ARG_CACHED_SIZE = "arg_cached_size"
        fun newInstance(requestFocus: Boolean = false, cachedSize: Int) = WeatherSearchFragment().apply {
            arguments = Bundle().apply {
                putBoolean(ARG_REQUEST_FOCUS, requestFocus)
                putInt(ARG_CACHED_SIZE, cachedSize)
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

        val orderId: Int = arguments?.getInt(ARG_CACHED_SIZE, 0) ?: 0

        viewModel.orderId = orderId + 1
        binding.viewModel = viewModel

        setupToolbar()
        setupRecyclerView()
        setupSearchView()
        setupObservers()
    }

    private fun setupToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(context)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.hasFixedSize()

        val adapter = WeatherSearchAdapter(
            viewModel
        )
        adapter.setHasStableIds(true)
        binding.recyclerView.adapter = adapter
    }

    private fun setupSearchView() {
        val requestFocus: Boolean = arguments?.getBoolean(ARG_REQUEST_FOCUS, false) ?: false
        if (requestFocus) {
            binding.searchView.isIconified = false
            arguments?.putBoolean(ARG_REQUEST_FOCUS, false)
        }
    }

    private fun setupObservers() {
        viewModel.openCachedWeatherLiveData.observe(this) {
            openCachedWeatherFragment(it)
        }

        viewModel.showToastLiveData.observe(this) {
            Snackbar.make(binding.toolbar, "$it", Snackbar.LENGTH_SHORT)
                .show()
        }

        viewModel.hideKeyboardLiveData.observe(this) {
            hideKeyboard(it)
        }
    }

    private fun hideKeyboard(view: View) {
        val inputMethodManager =
            activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun openCachedWeatherFragment(
        current: Current
    ) {
        activity?.onBackPressed()
    }

}

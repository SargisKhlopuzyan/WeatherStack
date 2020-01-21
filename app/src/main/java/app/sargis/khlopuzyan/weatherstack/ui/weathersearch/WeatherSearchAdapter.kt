package app.sargis.khlopuzyan.weatherstack.ui.weathersearch

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import app.sargis.khlopuzyan.weatherstack.R
import app.sargis.khlopuzyan.weatherstack.databinding.LayoutRecyclerViewItemLoadingBinding
import app.sargis.khlopuzyan.weatherstack.databinding.LayoutRecyclerViewItemNetworkErrorBinding
import app.sargis.khlopuzyan.weatherstack.databinding.LayoutRecyclerViewItemWeatherSearchBinding
import app.sargis.khlopuzyan.weatherstack.model.Current
import app.sargis.khlopuzyan.weatherstack.ui.common.BindableAdapter
import app.sargis.khlopuzyan.weatherstack.util.DataLoadingState

/**
 * Created by Sargis Khlopuzyan, on 12/18/2019.
 *
 * @author Sargis Khlopuzyan (sargis.khlopuzyan@gmail.com)
 */
class WeatherSearchAdapter(
    val viewModel: WeatherSearchViewModel
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), BindableAdapter<List<Current>> {

    private var dataLoadingState: DataLoadingState? = null
    private var currentsList = mutableListOf<Current>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {

            R.layout.layout_recycler_view_item_weather_search -> {

                val binding: LayoutRecyclerViewItemWeatherSearchBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.layout_recycler_view_item_weather_search,
                    parent, false
                )
                ArtistViewHolder(binding)
            }

            R.layout.layout_recycler_view_item_loading -> {
                val binding: LayoutRecyclerViewItemLoadingBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.layout_recycler_view_item_loading,
                    parent, false
                )
                LoadingViewHolder(binding)
            }

            R.layout.layout_recycler_view_item_network_error -> {

                val binding: LayoutRecyclerViewItemNetworkErrorBinding =
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.layout_recycler_view_item_network_error,
                        parent, false
                    )
                NetworkErrorViewHolder(binding)
            }

            else -> throw IllegalArgumentException("Unknown view type $viewType")
        }
    }

    override fun getItemCount(): Int {

        if (dataLoadingState == null) {
            return 0
        }

        return if (viewModel.hasExtraRow()) {
            currentsList.size + 1
        } else {
            currentsList.size
        }
    }

    override fun getItemId(position: Int) = position.toLong()


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (getItemViewType(position)) {

            R.layout.layout_recycler_view_item_weather_search -> {
                if (dataLoadingState is DataLoadingState.Failure && (itemCount > 1) && (position == itemCount - 2)) {
                    viewModel.dataLoadingStateLiveData.value = DataLoadingState.Loaded
                }
                (holder as ArtistViewHolder).bindItem(currentsList[position], viewModel)
            }

            R.layout.layout_recycler_view_item_loading -> {
                if (dataLoadingState == DataLoadingState.Loaded && viewModel.hasExtraRow()) {
                    viewModel.searchWeather()
                }
            }

            R.layout.layout_recycler_view_item_network_error -> {
                (holder as NetworkErrorViewHolder).bind(viewModel)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {

        return when (dataLoadingState) {

            is DataLoadingState.Loaded, is DataLoadingState.Loading -> {
                if (viewModel.hasExtraRow() && position == itemCount - 1) {
                    R.layout.layout_recycler_view_item_loading
                } else {
                    R.layout.layout_recycler_view_item_weather_search
                }
            }

            is DataLoadingState.Failure -> {
                if (viewModel.hasExtraRow() && position == itemCount - 1) {
                    R.layout.layout_recycler_view_item_network_error
                } else {
                    R.layout.layout_recycler_view_item_weather_search
                }
            }

            else -> throw IllegalArgumentException("Unknown view type")
        }
    }

    override fun setItems(items: List<Current>?) {
        currentsList.clear()
        items?.let {
            currentsList.addAll(items)
        }
        notifyDataSetChanged()
    }

    override fun setDataLoadingState(newDataLoadingState: DataLoadingState?) {
        dataLoadingState = newDataLoadingState
        val pos = if (itemCount > 0) itemCount - 1 else 0
        notifyItemChanged(pos)
    }

    // ViewHolder

    class ArtistViewHolder(private val binding: LayoutRecyclerViewItemWeatherSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItem(current: Current?, viewModel: WeatherSearchViewModel) {
            binding.viewModel = viewModel
            binding.current = current
        }
    }

    class LoadingViewHolder(binding: LayoutRecyclerViewItemLoadingBinding) :
        RecyclerView.ViewHolder(binding.root)

    class NetworkErrorViewHolder(binding: LayoutRecyclerViewItemNetworkErrorBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val binding: LayoutRecyclerViewItemNetworkErrorBinding = binding

        fun bind(viewModel: ViewModel) {
            binding.viewModel = viewModel as WeatherSearchViewModel
        }
    }

}
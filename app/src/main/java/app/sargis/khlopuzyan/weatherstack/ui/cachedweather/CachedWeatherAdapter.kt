package app.sargis.khlopuzyan.weatherstack.ui.cachedweather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import app.sargis.khlopuzyan.weatherstack.R
import app.sargis.khlopuzyan.weatherstack.databinding.LayoutRecyclerViewItemCachedWeathersBinding
import app.sargis.khlopuzyan.weatherstack.model.Current
import app.sargis.khlopuzyan.weatherstack.ui.common.BindableAdapter
import app.sargis.khlopuzyan.weatherstack.util.DataLoadingState

/**
 * Created by Sargis Khlopuzyan, on 12/18/2019.
 *
 * @author Sargis Khlopuzyan (sargis.khlopuzyan@gmail.com)
 */
class CachedWeatherAdapter(
    val viewModel: CachedWeatherViewModel
) : RecyclerView.Adapter<CachedWeatherAdapter.ViewHolder>(), BindableAdapter<List<Current>> {

    private var storedWeathers = mutableListOf<Current>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: LayoutRecyclerViewItemCachedWeathersBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.layout_recycler_view_item_weather_search,
            parent, false
        )
        return ViewHolder(
            binding
        )
    }

    override fun getItemCount(): Int {
        return storedWeathers.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(storedWeathers[position], viewModel)
    }

    override fun setItems(items: List<Current>?) {
        storedWeathers.clear()
        items?.let {
            storedWeathers.addAll(items)
        }
        notifyDataSetChanged()
    }

    override fun setDataLoadingState(dataLoadingState: DataLoadingState?) {

    }

    class ViewHolder(binding: LayoutRecyclerViewItemCachedWeathersBinding) :
        RecyclerView.ViewHolder(binding.root) {

        var binding: LayoutRecyclerViewItemCachedWeathersBinding = binding

        fun bindData(current: Current, viewModel: CachedWeatherViewModel) {
            binding.viewModel = viewModel
            binding.current = current
        }

    }

}
package app.sargis.khlopuzyan.weatherstack.ui.cachedweather

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import app.sargis.khlopuzyan.weatherstack.R
import app.sargis.khlopuzyan.weatherstack.databinding.LayoutRecyclerViewItemCachedWeathersBinding
import app.sargis.khlopuzyan.weatherstack.model.Current
import app.sargis.khlopuzyan.weatherstack.ui.common.BindableAdapter
import app.sargis.khlopuzyan.weatherstack.util.DataLoadingState
import app.sargis.khlopuzyan.weatherstack.util.StateMode
import java.util.*


/**
 * Created by Sargis Khlopuzyan, on 12/18/2019.
 *
 * @author Sargis Khlopuzyan (sargis.khlopuzyan@gmail.com)
 */
class CachedWeatherAdapter(
    val viewModel: CachedWeatherViewModel
) : ListAdapter<Current?, RecyclerView.ViewHolder>(DiffCallback()), BindableAdapter<List<Current>>,
    ItemTouchHelperAdapter {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CachedWeatherViewHolder {
        val binding: LayoutRecyclerViewItemCachedWeathersBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.layout_recycler_view_item_cached_weathers,
            parent, false
        )
        return CachedWeatherViewHolder(
            binding
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CachedWeatherViewHolder).bindItem(getItem(position), viewModel, position)
    }

    override fun setItems(items: List<Current>?) {
        if (items.isNullOrEmpty()) {
            submitList(listOf())
        } else {
            submitList(items)
        }
    }

    override fun setDataLoadingState(dataLoadingState: DataLoadingState?) {
        val pos = if (itemCount > 0) itemCount - 1 else 0
        notifyItemChanged(pos)
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {

        Log.e("LOG_TAG", "onItemMove => fromPosition: $fromPosition, toPosition: $toPosition")

        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(viewModel.cachedWeathersLiveData.value, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(viewModel.cachedWeathersLiveData.value, i, i - 1)
            }
        }
        swapItemsOrderIndexes(fromPosition, toPosition)
        viewModel.onItemMove(fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
        return true
    }

    override fun onItemDismiss(position: Int) {
        Log.e("LOG_TAG", "onItemDismiss => position: $position")
    }

    private fun swapItemsOrderIndexes(fromPosition: Int, toPosition: Int) {
        viewModel.cachedWeathersLiveData.value?.let {
            val tempFromPosition: Int = it[fromPosition].orderIndex
            it[fromPosition].orderIndex = it[toPosition].orderIndex
            it[toPosition].orderIndex = tempFromPosition
        }
    }

    // ViewHolder

    class CachedWeatherViewHolder(binding: LayoutRecyclerViewItemCachedWeathersBinding) :
        RecyclerView.ViewHolder(binding.root) {

        var binding: LayoutRecyclerViewItemCachedWeathersBinding = binding

        fun bindItem(current: Current?, viewModel: CachedWeatherViewModel, position: Int) {
            binding.viewModel = viewModel
            binding.current = current

            current?.let {

                if (viewModel.stateModeLiveData.value != StateMode.Normal) {
                    binding.checkBox.isChecked = current.isSelected
                }

                binding.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
                    current.isSelected = isChecked
                    if (isChecked) {
                        viewModel.addCurrentInSelected(current)
                    } else {
                        viewModel.removeCurrentFromSelected(current)
                    }
                }
            }
        }
    }

    //Callback

    class DiffCallback : DiffUtil.ItemCallback<Current?>() {

        override fun areItemsTheSame(oldItem: Current, newItem: Current): Boolean {
            return oldItem.query == newItem.query
        }

        override fun areContentsTheSame(oldItem: Current, newItem: Current): Boolean {
            return oldItem == newItem
        }
    }
}
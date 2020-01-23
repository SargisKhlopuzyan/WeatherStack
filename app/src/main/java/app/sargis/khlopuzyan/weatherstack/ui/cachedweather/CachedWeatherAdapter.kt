package app.sargis.khlopuzyan.weatherstack.ui.cachedweather

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
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
) : RecyclerView.Adapter<CachedWeatherAdapter.ViewHolder>(), BindableAdapter<List<Current>>,
    ItemTouchHelperAdapter {

    private var storedWeathers = mutableListOf<Current>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: LayoutRecyclerViewItemCachedWeathersBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.layout_recycler_view_item_cached_weathers,
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
        holder.bindData(storedWeathers[position], viewModel, position)
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

        fun bindData(current: Current, viewModel: CachedWeatherViewModel, position: Int) {
            binding.viewModel = viewModel
            binding.current = current

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

    //TODO Update in database
    private fun swapItemsOrderIndexes(fromPosition: Int, toPosition: Int) {
        viewModel.cachedWeathersLiveData.value?.let {
            val tempFromPosition: Int = it[fromPosition].orderIndex
            it[fromPosition].orderIndex = it[toPosition].orderIndex
            it[toPosition].orderIndex = tempFromPosition
        }
    }

}
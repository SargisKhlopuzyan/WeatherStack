package app.sargis.khlopuzyan.weatherstack.ui.cachedweather

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import app.sargis.khlopuzyan.weatherstack.helper.SingleLiveEvent
import app.sargis.khlopuzyan.weatherstack.model.Current
import app.sargis.khlopuzyan.weatherstack.networking.callback.Result
import app.sargis.khlopuzyan.weatherstack.repository.CachedWeatherRepository
import app.sargis.khlopuzyan.weatherstack.util.StateMode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CachedWeatherViewModel constructor(var cachedWeatherRepository: CachedWeatherRepository) :
    ViewModel() {

    val openWeatherSearchLiveData: SingleLiveEvent<View> = SingleLiveEvent()
    val updateEditModeLiveData: SingleLiveEvent<View> = SingleLiveEvent()
    val updateListElementLiveData: SingleLiveEvent<Int> = SingleLiveEvent()

    var cachedWeathersLiveData =
        MutableLiveData<List<Current>>(cachedWeatherRepository.getAllCachedWeathers())

    var stateModeLiveData = MutableLiveData(StateMode.Normal)
    var isDeleteActiveLiveData = MutableLiveData(true)
    var isAllSelectedLiveData = MutableLiveData(false)

    var isRefreshingLiveData = MutableLiveData(false)

    var selectedCurrent = mutableListOf<Current>()

    /**
     * Handles album list item click
     * */
    fun onEditClick(v: View) {
        stateModeLiveData.value = StateMode.Edit
        updateEditModeLiveData.value = v
        Log.e("LOG_TAG", "stateModeLiveData.value: ${stateModeLiveData.value}")
    }

    /**
     * Handles album list item click
     * */
    fun onDeleteClick(v: View) {
    }

    /**
     * Handles album list item click
     * */
    fun onSelectAllClick(v: View) {
    }

    /**
     * Handles navigation item click
     * */
    fun onNavigationClick(v: View) {
        if (stateModeLiveData.value == StateMode.Normal) {
            Log.e("LOG_TAG", "onSearchClick")
            doSearch(v)
        } else {
            Log.e("LOG_TAG", "cancelEditModeClick")
            cancelEditMode(v)
        }
    }

    /**
     * Handles search icon click
     * */
    private fun doSearch(v: View) {
        openWeatherSearchLiveData.value = v
    }

    private fun cancelEditMode(v: View) {

        for (current in selectedCurrent) {
            current.isSelected = false
        }

        selectedCurrent.clear()
        cachedWeathersLiveData.value = cachedWeathersLiveData.value
        stateModeLiveData.value = StateMode.Normal
        updateEditModeLiveData.value = v

    }

    fun getCachedWeathersSize() = cachedWeathersLiveData.value?.size ?: 0

    fun addCurrentInSelected(current: Current) {
        selectedCurrent.add(current)
    }

    fun removeCurrentFromSelected(current: Current) {
        selectedCurrent.remove(current)
    }

    fun onItemMove(fromPosition: Int, toPosition: Int) {
        cachedWeathersLiveData.value?.let {
            var items = listOf(
                cachedWeathersLiveData.value!![fromPosition],
                cachedWeathersLiveData.value!![toPosition]
            )
            cachedWeatherRepository.updateWeathersInDatabase(items)
        }
    }

    fun saveWeatherInCachedList(current: Current) {
        val currents = mutableListOf(current)
        cachedWeathersLiveData.value?.let {
            currents.addAll(it)
        }
        cachedWeathersLiveData.value = currents
    }

    val refreshListener = SwipeRefreshLayout.OnRefreshListener {
        Log.e("LOG_TAG", "SwipeRefreshLayout.OnRefreshListener")
        isRefreshingLiveData.value = true
        updateAllCachedWeathers()
    }

    private fun updateAllCachedWeathers() {
        cachedWeathersLiveData.value?.let {
            viewModelScope.launch(Dispatchers.Main) {
                for ((index, cachedWeather) in it.withIndex()) {

                    when (val resultCurrentWeather =
                        cachedWeatherRepository.searchCurrentWeather(cachedWeather.query)) {

                        is Result.Success -> {

                            resultCurrentWeather.data.current.id = cachedWeather.id
                            resultCurrentWeather.data.current.query = cachedWeather.query
                            resultCurrentWeather.data.current.orderIndex = cachedWeather.orderIndex

                            val cacheResult =
                                cachedWeatherRepository.updateWeatherInDatabase(resultCurrentWeather.data.current)

                            if (cacheResult == 1) {
                                updateListElementLiveData.value = index
                            }
                            Log.e(
                                "LOG_TAG",
                                "updateAllCachedWeathers => Success => cacheResult: $cacheResult"
                            )
                        }

                        is Result.Error -> {
                            Log.e("LOG_TAG", "updateAllCachedWeathers")
                        }

                        is Result.Failure -> {
                            Log.e("LOG_TAG", "updateAllCachedWeathers => Failure")
                        }
                    }
                }

                Log.e("LOG_TAG", "updateAllCachedWeathers => FINISH UPDATING")
                isRefreshingLiveData.value = false
                //TODO
            }
        }
    }

}


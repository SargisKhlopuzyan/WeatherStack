package app.sargis.khlopuzyan.weatherstack.ui.cachedweather

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.sargis.khlopuzyan.weatherstack.helper.SingleLiveEvent
import app.sargis.khlopuzyan.weatherstack.model.Current
import app.sargis.khlopuzyan.weatherstack.repository.CachedWeatherRepository
import app.sargis.khlopuzyan.weatherstack.util.StateMode

class CachedWeatherViewModel constructor(var cachedWeatherRepository: CachedWeatherRepository) :
    ViewModel() {

    val openWeatherSearchLiveData: SingleLiveEvent<View> = SingleLiveEvent()
    val updateEditModeLiveData: SingleLiveEvent<View> = SingleLiveEvent()

    var cachedWeathersLiveData =
        MutableLiveData<List<Current>>(cachedWeatherRepository.getAllCachedWeathers())

    var stateModeLiveData = MutableLiveData(StateMode.Normal)

    var selectedCurrent = mutableListOf<Current>()
    /**
     * Handles search icon click
     * */
    fun onSearchClick(v: View) {
        openWeatherSearchLiveData.value = v
    }

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
     * Handles navigation back item click
     * */
    fun onNavigationClick(v: View) {
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
}


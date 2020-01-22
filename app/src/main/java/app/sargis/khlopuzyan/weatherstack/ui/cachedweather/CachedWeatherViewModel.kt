package app.sargis.khlopuzyan.weatherstack.ui.cachedweather

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.sargis.khlopuzyan.weatherstack.helper.SingleLiveEvent
import app.sargis.khlopuzyan.weatherstack.model.Current
import app.sargis.khlopuzyan.weatherstack.repository.CachedWeatherRepository
import app.sargis.khlopuzyan.weatherstack.util.StateMode

class CachedWeatherViewModel constructor(cachedWeatherRepository: CachedWeatherRepository) :
    ViewModel() {

    val openWeatherSearchLiveData: SingleLiveEvent<View> = SingleLiveEvent()
    val enableEditModeLiveData: SingleLiveEvent<View> = SingleLiveEvent()

    var cachedWeathersLiveData = cachedWeatherRepository.getAllCachedWeathersLiveData()
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
        enableEditModeLiveData.value = v
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
        stateModeLiveData.value = StateMode.Normal
        enableEditModeLiveData.value = v
    }

    fun getCachedWeathersSize() = cachedWeathersLiveData.value?.size ?: 0

    fun addCurrentInSelected(current: Current) {
        selectedCurrent.add(current)
    }

    fun removeCurrentFromSelected(current: Current) {
        selectedCurrent.remove(current)
    }
}


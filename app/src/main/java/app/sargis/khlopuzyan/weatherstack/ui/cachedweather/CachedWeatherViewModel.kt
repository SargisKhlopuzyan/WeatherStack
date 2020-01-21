package app.sargis.khlopuzyan.weatherstack.ui.cachedweather

import android.util.Log
import android.view.View
import android.widget.CompoundButton
import androidx.lifecycle.ViewModel
import app.sargis.khlopuzyan.weatherstack.helper.SingleLiveEvent
import app.sargis.khlopuzyan.weatherstack.model.Current
import app.sargis.khlopuzyan.weatherstack.repository.CachedWeatherRepository


class CachedWeatherViewModel constructor(cachedWeatherRepository: CachedWeatherRepository) : ViewModel() {

    val openWeatherSearchLiveData: SingleLiveEvent<View> = SingleLiveEvent()
    val openEditScreenLiveData: SingleLiveEvent<Current> = SingleLiveEvent()

    var cachedAlbumsLiveData = cachedWeatherRepository.getAllCachedWeathersLiveData()

    /**
     * Handles search icon click
     * */
    fun onSearchClick(v: View) {
        openWeatherSearchLiveData.value = v
    }

    /**
     * Handles album list item click
     * */
    fun onEditClick(current: Current) {
        openEditScreenLiveData.value = current
    }

    /**
     * Handles album list item click
     * */
    fun onCurrentClick(current: Current) {
        openEditScreenLiveData.value = current
    }


    fun onCheckedChange(button: CompoundButton?, check: Boolean) {
        Log.e("LOG_TAG", "onCheckedChange: $check")
    }
}

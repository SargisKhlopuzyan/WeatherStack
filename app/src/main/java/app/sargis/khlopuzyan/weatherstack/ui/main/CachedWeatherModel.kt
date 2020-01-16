package app.sargis.khlopuzyan.weatherstack.ui.main

import android.view.View
import androidx.lifecycle.ViewModel
import app.sargis.khlopuzyan.weatherstack.helper.SingleLiveEvent
import app.sargis.khlopuzyan.weatherstack.model.Current
import app.sargis.khlopuzyan.weatherstack.repository.CachedWeatherRepository

class CachedWeatherModel constructor(cachedWeatherRepository: CachedWeatherRepository) : ViewModel() {

    val openSearchLiveData: SingleLiveEvent<View> = SingleLiveEvent()
    val openAlbumDetailLiveData: SingleLiveEvent<Current> = SingleLiveEvent()

    var cachedAlbumsLiveData = cachedWeatherRepository.getAllCachedWeathersLiveData()

    /**
     * Handles search icon click
     * */
    fun onSearchClick(v: View) {
        openSearchLiveData.value = v
    }

    /**
     * Handles album list item click
     * */
    fun onAlbumClick(current: Current) {
        openAlbumDetailLiveData.value = current
    }

}

package app.sargis.khlopuzyan.weatherstack.ui.weathersearch

import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.sargis.khlopuzyan.weatherstack.helper.SingleLiveEvent
import app.sargis.khlopuzyan.weatherstack.model.Current
import app.sargis.khlopuzyan.weatherstack.model.ResultWeather
import app.sargis.khlopuzyan.weatherstack.networking.callback.Result
import app.sargis.khlopuzyan.weatherstack.repository.WeatherSearchRepository
import app.sargis.khlopuzyan.weatherstack.util.DataLoadingState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeatherSearchViewModel constructor(private var weatherSearchRepository: WeatherSearchRepository) :
    ViewModel() {

    private var location: String = ""
    private var searchQuery: String = ""

    val openCachedWeatherLiveData: SingleLiveEvent<Current> = SingleLiveEvent()
    val hideKeyboardLiveData: SingleLiveEvent<View> = SingleLiveEvent()
    val showToastLiveData: SingleLiveEvent<String> = SingleLiveEvent()

    val dataLoadingStateLiveData = MutableLiveData<DataLoadingState>()

    val weatherLiveData = MutableLiveData<MutableList<Current>>(mutableListOf())
    val errorMessageLiveData = MutableLiveData<String>()

    /**
     * Handles search icon click
     * */
    fun onSearchClick(v: View) {
        hideKeyboardLiveData.value = v
        location = searchQuery
        searchWeather(searchQuery)
    }

    /**
     * Handles retry icon click
     * */
    fun retryClick(v: View) {
        hideKeyboardLiveData.value = v
        searchWeather()
    }

    /**
     * Handles weather list item click
     * */
    fun onCurrentClick(current: Current) {
        openCachedWeatherLiveData.value = current
    }

    val onQueryTextListener = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String): Boolean {
            searchWeather(query)
            return false
        }

        override fun onQueryTextChange(newText: String): Boolean {
            searchQuery = newText
            return false
        }
    }

    /**
     * Performs weather information search for the set location
     * */
    fun searchWeather(location: String? = searchQuery) {

        weatherLiveData.value = mutableListOf()

        if (location == null || location.isBlank()) {
            return
        }

        viewModelScope.launch(Dispatchers.Main) {

            dataLoadingStateLiveData.value = DataLoadingState.Loading

            val resultCurrentWeather = weatherSearchRepository.searchCurrentWeather(
                location = location
            )

            when (resultCurrentWeather) {

                is Result.Success -> {
                    dataLoadingStateLiveData.value = DataLoadingState.Loaded
                    handleSearchResult(resultCurrentWeather.data)
                }

                is Result.Error -> {
                    errorMessageLiveData.value =
                        "Something went wrong.\nError code: ${resultCurrentWeather.errorCode}"
                    dataLoadingStateLiveData.value =
                        DataLoadingState.Failure(Exception("${resultCurrentWeather.errorCode}"))
                }

                is Result.Failure -> {
                    errorMessageLiveData.value =
                        "Something went wrong.\nCheck your internet connection"
                    dataLoadingStateLiveData.value =
                        DataLoadingState.Failure(resultCurrentWeather.throwable)
                }
            }
        }
    }

    /**
     * Handles search result
     * */
    private fun handleSearchResult(resultWeather: ResultWeather?) {

        var currents: MutableList<Current>? =
            if (resultWeather?.current == null) {
                mutableListOf()
            } else {
                resultWeather.current.queryId = resultWeather.request.query
                mutableListOf(resultWeather.current)
            }

        weatherLiveData.value = currents
    }

    /**
     * Checks weather extra row is available
     * */
    fun hasExtraRow(): Boolean {
        return (dataLoadingStateLiveData.value != null && dataLoadingStateLiveData.value != DataLoadingState.Loaded)
    }

}

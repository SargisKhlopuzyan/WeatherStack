package app.sargis.khlopuzyan.weatherstack.util

/**
 * Created by Sargis Khlopuzyan, on 1/15/2020.
 *
 * @author Sargis Khlopuzyan (sargis.khlopuzyan@fcc.am)
 */
sealed class DataLoadingState {
    object Loading : DataLoadingState()
    object Loaded : DataLoadingState()
    class Failure(val throwable: Throwable?) : DataLoadingState()
}
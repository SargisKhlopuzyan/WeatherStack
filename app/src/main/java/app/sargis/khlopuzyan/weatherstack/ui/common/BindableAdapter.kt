package app.sargis.khlopuzyan.weatherstack.ui.common

import app.sargis.khlopuzyan.weatherstack.util.DataLoadingState

/**
 * Created by Sargis Khlopuzyan, on 1/15/2020.
 *
 * @author Sargis Khlopuzyan (sargis.khlopuzyan@fcc.am)
 */
interface BindableAdapter<T> {
    fun setItems(items: T?)
    fun setDataLoadingState(dataLoadingState: DataLoadingState?)
}
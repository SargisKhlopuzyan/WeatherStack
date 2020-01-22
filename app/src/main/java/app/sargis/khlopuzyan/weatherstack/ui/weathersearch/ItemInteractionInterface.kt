package app.sargis.khlopuzyan.weatherstack.ui.weathersearch

import app.sargis.khlopuzyan.weatherstack.model.Current

/**
 * Created by Sargis Khlopuzyan, on 1/22/2020.
 *
 * @author Sargis Khlopuzyan (sargis.khlopuzyan@fcc.am)
 */
interface ItemInteractionInterface {

    fun onCachedWeatherItemDismiss(
        currents: List<Current?>?,
        deletedCurrent: Current?,
        position: Int
    )

    fun onCachedWeatherItemMoved(fromPosition: Int, toPosition: Int)

    fun onCachedWeatherItemSelectedStateChanged(
        current: Current?,
        itemsSize: Int,
        position: Int,
        isSelected: Boolean?
    )
}
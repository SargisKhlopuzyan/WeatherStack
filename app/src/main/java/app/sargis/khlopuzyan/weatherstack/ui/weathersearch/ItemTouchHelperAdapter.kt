package app.sargis.khlopuzyan.weatherstack.ui.weathersearch

/**
 * Created by Sargis Khlopuzyan, on 1/22/2020.
 *
 * @author Sargis Khlopuzyan (sargis.khlopuzyan@fcc.am)
 */
interface ItemTouchHelperAdapter {

    fun onItemMove(fromPosition: Int, toPosition: Int): Boolean
    fun onItemDismiss(position: Int)

}
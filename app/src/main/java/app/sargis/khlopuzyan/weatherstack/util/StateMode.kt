package app.sargis.khlopuzyan.weatherstack.util

/**
 * Created by Sargis Khlopuzyan, on 1/17/2020.
 *
 * @author Sargis Khlopuzyan (sargis.khlopuzyan@fcc.am)
 */

enum class StateMode constructor(var index: Int){

    Normal(0),
    Edit(1),
    Delete(2);

    public fun getStateModeIndex(): Int {
        return index
    }
}
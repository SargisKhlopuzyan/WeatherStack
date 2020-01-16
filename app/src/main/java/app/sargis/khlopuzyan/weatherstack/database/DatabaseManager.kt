package app.sargis.khlopuzyan.weatherstack.database

import android.content.Context
import androidx.lifecycle.LiveData
import app.sargis.khlopuzyan.weatherstack.model.Current
import javax.inject.Inject

/**
 * Created by Sargis Khlopuzyan, on 1/15/2020.
 *
 * @author Sargis Khlopuzyan (sargis.khlopuzyan@fcc.am)
 */
class DatabaseManager @Inject constructor(var context: Context) {

    fun saveWeatherInDatabase(current: Current): Long {
        return CurrentDatabase.getInstance(context).getCurrentDAO().insertCurrent(current)
    }

    fun deleteWeatherFromDatabase(current: Current): Int {
        return CurrentDatabase.getInstance(context).getCurrentDAO().deleteCurrentByQueryId(current.queryId)
    }

    fun getWeatherFromDatabase(name: String): Current? {
        return CurrentDatabase.getInstance(context).getCurrentDAO().getCurrentByQueryId(name)
    }

    fun getAllCachedWeathersFromDatabase(): List<Current> {
        return CurrentDatabase.getInstance(context).getCurrentDAO().getAllCachedCurrents()
    }

    fun getAllCachedWeathersLiveDataFromDatabase(): LiveData<List<Current>?> {
        return CurrentDatabase.getInstance(context).getCurrentDAO().getAllCachedCurrentsLiveData()
    }

}
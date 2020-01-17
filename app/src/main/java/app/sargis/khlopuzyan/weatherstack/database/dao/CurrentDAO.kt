package app.sargis.khlopuzyan.weatherstack.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import app.sargis.khlopuzyan.weatherstack.model.Current

/**
 * Created by Sargis Khlopuzyan, on 1/15/2020.
 *
 * @author Sargis Khlopuzyan (sargis.khlopuzyan@fcc.am)
 */
@Dao
interface CurrentDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCurrent(current: Current): Long

    @Update
    fun updateCurrent(vararg currents: Current): Int

    @Query("DELETE FROM current WHERE queryId = :queryId")
    fun deleteCurrentByQueryId(queryId: String?): Int

    @Query("SELECT * FROM current WHERE queryId = :queryId")
    fun getCurrentByQueryId(queryId: String?): Current?

    @Query("SELECT * FROM current")
    fun getAllCachedCurrents(): List<Current>

    @Query("SELECT * FROM current")
    fun getAllCachedCurrentsLiveData(): LiveData<List<Current>?>

}
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
    fun updateCurrents(currents: List<Current>): Int

    @Update
    fun updateCurrent(current: Current): Int

    @Query("DELETE FROM current WHERE `query` = :queryId")
    fun deleteCurrentByQueryId(queryId: String): Int

    @Query("SELECT * FROM current WHERE `query` = :queryId")
    fun getCurrentByQueryId(queryId: String): Current?

    @Query("SELECT * FROM current ORDER BY orderIndex DESC")
    fun getAllCachedCurrents(): List<Current>

    @Query("SELECT * FROM current ORDER BY orderIndex DESC")
    fun getAllCachedCurrentsLiveData(): LiveData<List<Current>?>

}
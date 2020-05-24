/*
 * Сreated by Igor Pokrovsky. 2020/5/24
 */

package com.nigtime.weatherapplication.storage.service

import androidx.room.Dao
import androidx.room.Query
import com.nigtime.weatherapplication.storage.tables.ReferenceCityTable

@Dao
interface ReferenceCitiesDao {
    @Query("SELECT * FROM reference_cities WHERE name LIKE :name ORDER BY name ASC LIMIT :startPosition, :count ")
    fun queryByName(name: String, startPosition: Int, count: Int): List<ReferenceCityTable>
}
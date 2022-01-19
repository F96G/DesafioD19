package com.example.monitoreodeterremotos.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.monitoreodeterremotos.Terremoto

@Dao
interface EqDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(eqList: MutableList<Terremoto>)


    @Query("SELECT * FROM terremotos")
    fun getTerremotoPorTiempo():MutableList<Terremoto>

    @Query("SELECT * FROM terremotos order by magnitud ASC")
    fun getTerremotoPorMagnitud():MutableList<Terremoto>
}
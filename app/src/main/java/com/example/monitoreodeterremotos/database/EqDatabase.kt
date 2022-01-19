package com.example.monitoreodeterremotos.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.monitoreodeterremotos.Terremoto


@Database(entities = [Terremoto::class], version = 1)
abstract class EqDatabase: RoomDatabase(){
    abstract val eqDao:EqDAO
}


private lateinit var INSTANCE:EqDatabase


fun getDatabase(context: Context): EqDatabase{
    synchronized(EqDatabase::class.java) {
        if (!::INSTANCE.isInitialized)
            INSTANCE = Room.databaseBuilder(context.applicationContext, EqDatabase::class.java,"terremotos_db").build()
    }
    return INSTANCE
}
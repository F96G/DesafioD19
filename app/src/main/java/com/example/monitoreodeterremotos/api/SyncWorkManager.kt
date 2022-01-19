package com.example.monitoreodeterremotos.api

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.monitoreodeterremotos.database.getDatabase
import com.example.monitoreodeterremotos.main.MainRepository


class SyncWorkManager(appContext: Context, params:WorkerParameters): CoroutineWorker(appContext, params) {

    companion object{
        const val WORK_NAME = "SyncWorkManager"
    }

    private val database = getDatabase(appContext)
    private val repositorio = MainRepository(database)


    override suspend fun doWork(): Result {
        repositorio.recuperarTerremotosDeDatabase(true)

        return Result.success()
    }
}
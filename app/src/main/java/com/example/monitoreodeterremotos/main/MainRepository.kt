package com.example.monitoreodeterremotos.main

import com.example.monitoreodeterremotos.Terremoto
import com.example.monitoreodeterremotos.api.EqJsonResponse
import com.example.monitoreodeterremotos.api.service
import com.example.monitoreodeterremotos.database.EqDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
class MainRepository(private val database: EqDatabase) {

     suspend fun recuperarTerremotos(tipoClas: Boolean): MutableList<Terremoto>{
        return withContext(Dispatchers.IO){
            val eqJsonResponse = service.getUltimoTerremoto()

            val listaTerremotos = parseEqResultado(eqJsonResponse)

            database.eqDao.insertAll(listaTerremotos)

            recuperarTerremotosDeDatabase(tipoClas)
        }
    }

    suspend fun recuperarTerremotosDeDatabase(tipoClas: Boolean): MutableList<Terremoto>{
        return withContext(Dispatchers.IO) {
            if (tipoClas)
                 database.eqDao.getTerremotoPorMagnitud()
            else
                 database.eqDao.getTerremotoPorTiempo()
        }
    }


    private fun parseEqResultado(eqJsonResponse: EqJsonResponse): MutableList<Terremoto>{
        val listaTerremotos = mutableListOf<Terremoto>()
        val featureList = eqJsonResponse.features

        for (feature in featureList){
            val id = feature.id

            val magnitud = feature.properties.mag
            val lugar = feature.properties.place
            val tiempo = feature.properties.time

            val longitud = feature.geometry.longitu
            val latitud = feature.geometry.latitud

            listaTerremotos.add(Terremoto(id,lugar,magnitud,tiempo,longitud,latitud))
        }
        return listaTerremotos
    }
}
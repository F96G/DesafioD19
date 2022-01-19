package com.example.monitoreodeterremotos.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.monitoreodeterremotos.Terremoto
import com.example.monitoreodeterremotos.api.ApiResposeStatus
import com.example.monitoreodeterremotos.database.getDatabase
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class MainViewModel(application: Application,private val tipoClas:Boolean): AndroidViewModel(application) {
    private var _eqList = MutableLiveData<MutableList<Terremoto>>()
    val eqList: LiveData<MutableList<Terremoto>> get() = _eqList

    private val database = getDatabase(application)
    private val repositorio = MainRepository(database)

    private val _status = MutableLiveData<ApiResposeStatus>()
    val status : LiveData<ApiResposeStatus> get() = _status

    init {
        cargarTerremotosDeDb(tipoClas)
    }

    fun cargarTerremotosDeDb(tClas:Boolean){
        viewModelScope.launch {
            _eqList.value = repositorio.recuperarTerremotosDeDatabase(tClas)
            //Si entra y la lista esta vacia llama a descargar los datos
            if (_eqList.value!!.isEmpty()){
                cargarTerremotos()
            }
        }

    }

    private fun cargarTerremotos() {
        viewModelScope.launch {
            try {
                _status.value = ApiResposeStatus.LOADING
                _eqList.value = repositorio.recuperarTerremotos(tipoClas)
                _status.value = ApiResposeStatus.DONE
            }catch (e:UnknownHostException){
                Log.d(MainViewModel::class.java.simpleName,"No internet connection", e )
                _status.value = ApiResposeStatus.ERROR
            }

        }
    }
}
package com.example.monitoreodeterremotos.Detalles

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.*

class DetallesViewModel: ViewModel() {
    private var _magnitud = MutableLiveData<Double>()
    val magnitud: LiveData<Double> get() = _magnitud

    private var _lugar = MutableLiveData<String>()
    val lugar: LiveData<String> get() = _lugar

    private var _tiempo = MutableLiveData<Date>()
    val tiempo:LiveData<Date> get() = _tiempo

    private var _latitud = MutableLiveData<Double>()
    val latitud:LiveData<Double> get() = _latitud

    private var _longitud =  MutableLiveData<Double>()
    val longitud: LiveData<Double> get() = _longitud

    fun setTerremoto(magnitud:Double, lugar:String, tiempo:Long, latitud:Double, longitud:Double){
        _magnitud.value = magnitud
        _lugar.value = lugar
        _latitud.value = latitud
        _longitud.value = longitud

        _tiempo.value = Date(tiempo)

    }

}
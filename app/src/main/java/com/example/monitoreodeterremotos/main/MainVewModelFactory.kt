package com.example.monitoreodeterremotos.main

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class MainVewModelFactory(private val application: Application, private val tipoClas: Boolean): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(application, tipoClas) as T
    }
}
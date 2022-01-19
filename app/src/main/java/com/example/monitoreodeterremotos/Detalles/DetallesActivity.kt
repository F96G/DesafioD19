package com.example.monitoreodeterremotos.Detalles

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.monitoreodeterremotos.Terremoto
import com.example.monitoreodeterremotos.databinding.ActivityDetallesBinding

class DetallesActivity : AppCompatActivity() {
    companion object{
        val KEY_TERREMOTO = "terremoto"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDetallesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel = ViewModelProvider(this).get(DetallesViewModel::class.java)

        val bundle = intent.extras!!

        val terremoto = bundle.getParcelable<Terremoto>(KEY_TERREMOTO)

        terremoto?.let {
            viewModel.setTerremoto(it.magnitud , it.lugar, it.time, it.latitud, it.longitud)
        }


        binding.tvMagnitud.text = viewModel.magnitud.value.toString()
        binding.tvLugar.text = viewModel.lugar.value
        binding.tvTiempo.text = viewModel.tiempo.value.toString()
        binding.tvLatitud.text = viewModel.latitud.value.toString()
        binding.tvLongitud.text = viewModel.longitud.value.toString()
    }
}
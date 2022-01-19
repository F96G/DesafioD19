package com.example.monitoreodeterremotos.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.monitoreodeterremotos.Detalles.DetallesActivity
import com.example.monitoreodeterremotos.EqAdapter
import com.example.monitoreodeterremotos.R
import com.example.monitoreodeterremotos.Terremoto
import com.example.monitoreodeterremotos.api.ApiResposeStatus
import com.example.monitoreodeterremotos.api.WorkerUtil
import com.example.monitoreodeterremotos.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    companion object{
        const val SORT_TYPE_KEY = "sort_key"
    }

    lateinit private var viewModel:MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        WorkerUtil.scheduleSync(this)

        var tipoClasific = getTipoClasific()

        binding.eqRecycler.layoutManager = LinearLayoutManager(this)
        viewModel = ViewModelProvider(this, MainVewModelFactory(application, tipoClasific)).get(MainViewModel::class.java)

        val adapter = EqAdapter(this)
        binding.eqRecycler.adapter = adapter

        viewModel.eqList.observe(this, Observer {
            eqList -> adapter.submitList(eqList)

            manejarListaVacia(eqList, binding)
        })

        viewModel.status.observe(this){
            when(it){
                (ApiResposeStatus.DONE)->{
                    binding.pbLoading.visibility = View.GONE
                }(ApiResposeStatus.LOADING)->{
                binding.pbLoading.visibility = View.VISIBLE
                }(ApiResposeStatus.ERROR)->{
                binding.pbLoading.visibility = View.GONE
                Toast.makeText(this, "Error de descarga, ver internet",Toast.LENGTH_SHORT).show()
                }

            }
        }


        adapter.onItemClickListener = {
            val intent = Intent(this, DetallesActivity::class.java)
            intent.putExtra(DetallesActivity.KEY_TERREMOTO, it)
            startActivity(intent)
        }
    }

    private fun getTipoClasific(): Boolean {
        return getPreferences(MODE_PRIVATE).getBoolean(SORT_TYPE_KEY, false)
    }

    //Guardara el tipo de clasificacion cuando la app cambia de estado
    private fun saveClasificacion(tipoClas:Boolean){
        val pref = getPreferences(MODE_PRIVATE)

        val editor = pref.edit()
        editor.putBoolean(SORT_TYPE_KEY, tipoClas)
        editor.apply()
    }



    private fun manejarListaVacia(eqList: MutableList<Terremoto>, binding: ActivityMainBinding){
        if (eqList.isEmpty())
            binding.eqEmptyView.visibility = View.VISIBLE
        else
            binding.eqEmptyView.visibility = View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.iSortMagnitude ->{
                viewModel.cargarTerremotosDeDb(true)
                saveClasificacion(true)
            }
            R.id.iSortTime ->{
                viewModel.cargarTerremotosDeDb(false)
                saveClasificacion(false)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
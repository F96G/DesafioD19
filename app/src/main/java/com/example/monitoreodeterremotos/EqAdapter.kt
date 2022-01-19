package com.example.monitoreodeterremotos

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DiffUtil
import  androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.monitoreodeterremotos.databinding.EqListaBinding

class EqAdapter(private val context:Context) :ListAdapter<Terremoto, EqAdapter.EqViewHolder>(DiffCallbak) {
    //Almaceno el tag de la clase para usar en Log, por si el nombre de la clase cambia
    private val TAG = EqAdapter::class.java.simpleName


    companion object DiffCallbak :DiffUtil.ItemCallback<Terremoto>(){
        override fun areItemsTheSame(oldItem: Terremoto, newItem: Terremoto): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Terremoto, newItem: Terremoto): Boolean {
            return oldItem == newItem
        }
    }

    lateinit var onItemClickListener: (Terremoto) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EqAdapter.EqViewHolder {
        val binding = EqListaBinding.inflate(LayoutInflater.from(parent.context))

        return EqViewHolder(binding)
    }


    override fun onBindViewHolder(holder: EqAdapter.EqViewHolder, position: Int) {
        val terremoto = getItem(position)
        holder.bind(terremoto)
    }


    inner class EqViewHolder(private val binding: EqListaBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(terremoto: Terremoto){
            binding.tvMagnitud.text = context.getString(R.string.magnitude_format, terremoto.magnitud)
            binding.tvLugar.text = terremoto.lugar

            binding.root.setOnClickListener {
                if(::onItemClickListener.isInitialized)
                    onItemClickListener(terremoto)
                else
                    Log.e(TAG, "onItemClickListener no esta inicializado")
            }
            //Esto no es muy necesario pero agiliza la velocidad del recyclerView en caso de listas muy grandes
            binding.executePendingBindings()
        }
    }
}
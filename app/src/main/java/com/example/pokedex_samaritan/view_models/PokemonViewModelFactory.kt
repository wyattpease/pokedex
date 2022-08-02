package com.example.pokedex_samaritan.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class PokemonViewModelFactory : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(PokemonViewModel::class.java)){
            return PokemonViewModel() as T
        }
        throw IllegalArgumentException ("UnknownViewModel")
    }
}
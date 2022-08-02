package com.example.pokedex_samaritan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.Bindable
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex_samaritan.adapters.CapturedRecyclerAdapter
import com.example.pokedex_samaritan.models.Captured
import pokedex_samaritan.R
import pokedex_samaritan.databinding.CapturedPokemonBinding

class CapturedActivity : AppCompatActivity(), Observable {

    @Bindable
    var capList = MutableLiveData<ArrayList<Captured>>()

    private lateinit var binding: CapturedPokemonBinding
    private lateinit var mainrecycler: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.captured_pokemon)
        binding.lifecycleOwner = this
        val dbh = DBHelper(this, null)

        mainrecycler = findViewById(R.id.recycler)
        initialiseAdapter()

        capList.postValue(dbh.getPokemon())
    }

    private fun initialiseAdapter(){
        mainrecycler.layoutManager = LinearLayoutManager(applicationContext)
        observeData()
    }

    private fun observeData(){
        capList.observe(this) {
            mainrecycler.adapter = CapturedRecyclerAdapter(it, this)
        }
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        TODO("Not yet implemented")
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        TODO("Not yet implemented")
    }
}
package com.example.pokedex_samaritan

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.Bindable
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex_samaritan.adapters.PokeRecyclerAdapter
import com.example.pokedex_samaritan.models.Pokemon
import com.example.pokedex_samaritan.view_models.PokemonViewModel
import com.example.pokedex_samaritan.view_models.PokemonViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton
import pokedex_samaritan.R
import pokedex_samaritan.databinding.MainActivityBinding


class MainActivity : AppCompatActivity(), Observable {

    private lateinit var viewModel: PokemonViewModel
    private lateinit var binding: MainActivityBinding
    private lateinit var mainrecycler: RecyclerView
    var isLastPage: Boolean = false
    var isLoading: Boolean = false

    @Bindable
    var pokeList = MutableLiveData<ArrayList<Pokemon>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.main_activity)
        mainrecycler = findViewById(R.id.recycler)
        val factory = PokemonViewModelFactory()
        viewModel = ViewModelProvider(this, factory).get(PokemonViewModel::class.java)
        binding.pokemonViewModel = viewModel
        binding.lifecycleOwner = this

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            view.context.startActivity(Intent(this, CapturedActivity::class.java))
        }

        getPokemon()
        initialiseAdapter()

        mainrecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    getMoreItems()
                }
            }
        })
    }
    private fun getPokemon(){
        val pokemon = viewModel.getData(null)
        pokeList.postValue(pokemon)
        mainrecycler.adapter?.notifyDataSetChanged()
    }

    private fun initialiseAdapter(){
        mainrecycler.layoutManager = GridLayoutManager(applicationContext, 2 )
        observeData()
    }

    private fun observeData(){
        pokeList.observe(this) {
            mainrecycler.adapter = PokeRecyclerAdapter(viewModel, it, this)
        }
    }

    private fun getMoreItems() {
        isLoading = false
        val temp = pokeList.value
        val pokemon = viewModel.getData(temp)

        pokeList.value=pokemon
        val size = pokeList.value?.size
        if (size != null) {
            mainrecycler.adapter?.notifyItemRangeChanged(size-1,20)
        }
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        TODO("Not yet implemented")
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        TODO("Not yet implemented")
    }
}
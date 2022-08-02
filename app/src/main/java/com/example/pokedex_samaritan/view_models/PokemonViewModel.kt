package com.example.pokedex_samaritan.view_models

import android.util.Log
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokedex_samaritan.models.PokeDetails
import com.example.pokedex_samaritan.models.Pokemon
import kotlinx.coroutines.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList

class PokemonViewModel : ViewModel(), Observable {
    @Bindable
    var pokeCall = MutableLiveData("https://pokeapi.co/api/v2/pokemon?offset=0&limit=20")

    private val client = OkHttpClient()

    fun getData(oldList : ArrayList<Pokemon>?) : ArrayList<Pokemon>{

        val newList = arrayListOf<Pokemon>()

        val request = Request.Builder()
            .url(pokeCall.value)
            .build()
        runBlocking {
            client.newCall(request).enqueue(object : Callback {

                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                }

                override fun onResponse(call: Call, response: Response) {
                    response.use {
                        if (!response.isSuccessful) throw IOException("Unexpected code $response")
                        val json = JSONObject(response.body()?.string())

                        pokeCall.postValue(json.getString("next"))

                        val pokemon = json.getJSONArray("results")
                        runBlocking {
                            for (i in 0 until pokemon.length()) {

                                val name = pokemon.getJSONObject(i)["name"].toString()
                                val url = pokemon.getJSONObject(i)["url"].toString()

                                val details = getDetails(url)
                                val p = Pokemon(
                                    '#' + details.order + " " + name.replaceFirstChar {
                                        if (it.isLowerCase()) it.titlecase(
                                            Locale.getDefault()
                                        ) else it.toString()
                                    },
                                    url,
                                    details
                                )
                                newList.add(p)
                            }
                        }
                    }
                }
            })
            if (!oldList.isNullOrEmpty()) {
                val total = oldList.plus(newList)
                Log.i("i", total.toString())
                return@runBlocking ArrayList(total)
            }
            else{

            }
        }

        return if (oldList != null) {
            ArrayList(oldList+newList)
        } else
            newList
    }

    fun getDetails(url: String): PokeDetails {
        val u = url.dropLast(1)
        val i = u.lastIndexOf('/')
        val order = u.substring(i+1).padStart(3,'0')
        var typeString = ""
        val image: String
        var hex = ""
        val stats = arrayListOf<Int>()

        val request = Request.Builder()
            .url(url)
            .build()

        val response = client.newCall(request).execute()

        val json = JSONObject(response.body()?.string())
        image = json.getJSONObject("sprites")
            .getJSONObject("other")
            .getJSONObject("official-artwork")["front_default"]
            .toString()

        val types = json.getJSONArray("types")
        for (i in 0 until types.length()){
            val t = types.getJSONObject(i)
                         .getJSONObject("type")["name"]

            if (hex == "") {
                hex = getHex(t.toString())
            }

            typeString += "${t.toString()
                .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }} • "

        }

        val sj = json.getJSONArray("stats")
        for (i in 0 until sj.length()){
            stats.add(sj.getJSONObject(i)["base_stat"] as Int)
        }

        return PokeDetails(
            typeString.dropLast(2),
            order,
            image,
            hex,
            json["height"].toString(),
            json["weight"].toString(),
            stats
        )
    }

    private fun getHex(type: String): String{
        if (type == "grass")
            return "#204000"
        if (type == "fire")
            return "#fc0c0b"
        if (type == "water")
            return "#08517a"
        if (type == "normal")
            return "#aca974"
        if (type == "flying")
            return "#085764"
        if (type == "bug")
            return "#91ba2e"
        if (type == "poison")
            return "#611380"
        if (type=="electric")
            return "#969101"
        if (type == "ground")
            return "#bfac21"
        if (type == "fighting")
            return "#800b11"
        if (type == "psychic")
            return "#8a0532"
        if (type == "rock")
            return "#474026"
        if (type == "ice")
            return "#103d43"
        if (type == "ghost")
            return "#472b53"
        if (type == "dragon")
            return "#29036a"
        if (type == "dark")
            return "#2d221c"
        if (type == "steel")
            return "#454545"

        return "#f87ea7"
    }
    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }
}
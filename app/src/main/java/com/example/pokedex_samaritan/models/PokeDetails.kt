package com.example.pokedex_samaritan.models

data class PokeDetails(
    val types: String,
    val order: String,
    val image: String,
    val hex: String,
    val height: String,
    val weight: String,
    val stats: ArrayList<Int>
)

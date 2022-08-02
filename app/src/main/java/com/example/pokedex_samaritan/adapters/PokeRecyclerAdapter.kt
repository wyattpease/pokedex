package com.example.pokedex_samaritan.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokedex_samaritan.DetailsActivity
import com.example.pokedex_samaritan.models.Pokemon
import com.example.pokedex_samaritan.view_models.PokemonViewModel
import kotlinx.android.synthetic.main.repo_view.view.*
import pokedex_samaritan.R


class PokeRecyclerAdapter(val viewModel: PokemonViewModel, private val arrayList: ArrayList<Pokemon>, private val context: Context): RecyclerView.Adapter<PokeRecyclerAdapter.PokeViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): PokeRecyclerAdapter.PokeViewHolder {
        val root = LayoutInflater.from(parent.context).inflate(R.layout.repo_view,parent,false)
        return PokeViewHolder(root)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: PokeRecyclerAdapter.PokeViewHolder, position: Int) {
        holder.bind(arrayList[position])

        holder.itemView.setOnClickListener { view ->
            val b  = Bundle()
            b.putString("types", "Type(s): " + arrayList[position].details.types)
            b.putString("image", arrayList[position].details.image)
            b.putString("hex", arrayList[position].details.hex)
            b.putString("height", "Height: " + arrayList[position].details.height + " m")
            b.putString("weight", "Weight: " + arrayList[position].details.weight + " kg")
            b.putString("name", arrayList[position].name)
            b.putIntegerArrayList("stats", arrayList[position].details.stats)

            val detailsIntent = Intent(context, DetailsActivity::class.java)
            detailsIntent.putExtras(b)
            view.context.startActivity(detailsIntent)
        }
        holder.itemView.findViewById<ImageView>(R.id.poke_image).setBackgroundColor(Color.parseColor(arrayList[position].details.hex))
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    inner class PokeViewHolder(private val binding: View) : RecyclerView.ViewHolder(binding) {
        @RequiresApi(Build.VERSION_CODES.O)

        fun bind(pokemon: Pokemon) {
            val iv = binding.findViewById<ImageView>(R.id.poke_image)
            binding.poke_name.text = pokemon.name
            binding.types.text = pokemon.details.types
            Glide.with(binding.context).load(pokemon.details.image)
                .override(500, 500)
                .into(iv)
        }
    }
}
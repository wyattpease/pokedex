package com.example.pokedex_samaritan.adapters

import android.annotation.SuppressLint
import com.example.pokedex_samaritan.models.Captured
import kotlinx.android.synthetic.main.captured_pokemon_item.view.*

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import pokedex_samaritan.R

class CapturedRecyclerAdapter(private val arrayList: ArrayList<Captured>, private val context: Context): RecyclerView.Adapter<CapturedRecyclerAdapter.CapturedViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CapturedRecyclerAdapter.CapturedViewHolder {
        val root = LayoutInflater.from(parent.context).inflate(R.layout.captured_pokemon_item,parent,false)
        return CapturedViewHolder(root)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: CapturedRecyclerAdapter.CapturedViewHolder, position: Int) {
        holder.bind(arrayList[position])
        holder.itemView.findViewById<ImageView>(R.id.captured_pokemon_img).setBackgroundColor(Color.parseColor(arrayList[position].hex))
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    inner class CapturedViewHolder(private val binding: View) : RecyclerView.ViewHolder(binding) {
        @SuppressLint("SetTextI18n")
        @RequiresApi(Build.VERSION_CODES.O)

        fun bind(captured: Captured) {
            val iv = binding.findViewById<ImageView>(R.id.captured_pokemon_img)
            binding.cap_name.text = captured.name
            binding.cap_date.text = "Captured on: ${captured.date}"
            binding.cap_level.text = "Captured Level: ${captured.lvl}"
            Glide.with(binding.context).load(captured.img)
                .override(500, 500)
                .into(iv)
        }
    }
}
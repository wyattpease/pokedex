package com.example.pokedex_samaritan

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Window
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
import pokedex_samaritan.R
import pokedex_samaritan.databinding.DetailsActivityBinding


class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: DetailsActivityBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val name  = intent.getStringExtra("name")?.substringAfter(" ")

        binding = DataBindingUtil.setContentView(this,R.layout.details_activity)
        binding.lifecycleOwner = this
        val pdi = findViewById<ImageView>(R.id.poke_image_details)
        pdi.setBackgroundColor(Color.parseColor(intent.getStringExtra("hex")))
        Glide.with(this).load(intent.getStringExtra("image"))
            .override(700, 700)
            .into(pdi)

        val dn = findViewById<TextView>(R.id.poke_name_details)
        dn.text = intent.getStringExtra("name")
        dn.setBackgroundColor(Color.parseColor(intent.getStringExtra("hex")))

        findViewById<TextView>(R.id.type_details).text = intent.getStringExtra("types")
        findViewById<TextView>(R.id.weight).text = intent.getStringExtra("weight")
        findViewById<TextView>(R.id.height).text = intent.getStringExtra("height")

        val stats = intent.getIntegerArrayListExtra("stats")

        findViewById<TextView>(R.id.HP).text = "HP: " + stats?.get(0)?.toString()
        findViewById<TextView>(R.id.atk).text ="Attack: " + stats?.get(1)?.toString()
        findViewById<TextView>(R.id.def).text ="Defence: " + stats?.get(2)?.toString()
        findViewById<TextView>(R.id.spc_atk).text ="Special Attack: " + stats?.get(3)?.toString()
        findViewById<TextView>(R.id.spc_def).text ="Special Defence: " + stats?.get(4)?.toString()
        findViewById<TextView>(R.id.spd).text ="Speed: " + stats?.get(5)?.toString()

        val button: MaterialButton = findViewById(R.id.capture)
        button.setOnClickListener {
            capture(name)
        }

    }

    private fun capture(name: String?){
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.capture_dialog)
        val title = "Capturing $name"
        dialog.findViewById<TextView>(R.id.captured_pokemon).text = title
        addListeners(dialog, name)
        dialog.show()
    }

    private fun addListeners(dialog : Dialog, name : String?){
        val button = dialog.findViewById(R.id.capture_dialog_btn) as MaterialButton
        val nickname = dialog.findViewById<EditText>(R.id.nickname)
        val capDate = dialog.findViewById<EditText>(R.id.date)
        val capLevel = dialog.findViewById<EditText>(R.id.level)

        button.setOnClickListener {
            val db = DBHelper(this, null)

            val nn = nickname.text.toString()
            val age = capDate.text.toString()
            val lvl = capLevel.text.toString()
            val img = intent.getStringExtra("image")
            val hex = intent.getStringExtra("hex")

            if (img != null && hex != null) {
                db.addPokemon(nn, age, lvl, img, hex)
                Toast.makeText(this, "Captured $name!", Toast.LENGTH_LONG).show()
            }

            dialog.dismiss()
        }

        nickname.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.toString().trim { it <= ' ' }.isNotEmpty() &&
                    capDate.text.isNotEmpty() &&
                    capLevel.text.isNotEmpty())
                {
                    button.isEnabled = true
                    button.setBackgroundColor(ContextCompat.getColor(dialog.context, R.color.capture_red))

                }
                else{
                    button.isEnabled = false
                    button.setBackgroundColor(ContextCompat.getColor(dialog.context, R.color.light_gray))
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int,after: Int) {}

            override fun afterTextChanged(s: Editable) {}
        })

        capDate.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.toString().trim { it <= ' ' }.isNotEmpty() &&
                    capLevel.text.isNotEmpty() &&
                    nickname.text.isNotEmpty())
                {
                    button.isEnabled = true
                    button.setBackgroundColor(ContextCompat.getColor(dialog.context, R.color.capture_red))

                }
                else{
                    button.isEnabled = false
                    button.setBackgroundColor(ContextCompat.getColor(dialog.context, R.color.light_gray))
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int,after: Int) {}

            override fun afterTextChanged(s: Editable) {}
        })

        capLevel.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.toString().trim { it <= ' ' }.isNotEmpty() &&
                    capDate.text.isNotEmpty() &&
                    nickname.text.isNotEmpty())
                {
                    button.isEnabled = true
                    button.setBackgroundColor(ContextCompat.getColor(dialog.context, R.color.capture_red))

                }
                else{
                    button.isEnabled = false
                    button.setBackgroundColor(ContextCompat.getColor(dialog.context, R.color.light_gray))
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int,after: Int) {}

            override fun afterTextChanged(s: Editable) {}
        })
    }
}
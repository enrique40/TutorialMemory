package com.example.tutorialmemory

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        var play: Button? = null
        var salir:android.widget.Button? = null

        play = findViewById(R.id.botonMainJugar)
        salir = findViewById(R.id.botonMainSalir)

        play.setOnClickListener(View.OnClickListener {
            println("iniciando juego...")
            iniciarJuego()
        })

        salir.setOnClickListener(View.OnClickListener { finish() })
    }

    private fun iniciarJuego() {
        val i = Intent(this, Juego::class.java)
        startActivity(i)
    }
}
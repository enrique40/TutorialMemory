package com.example.tutorialmemory

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import com.example.tutorialmemory.databinding.JuegoBinding
import java.util.Collections

class Juego : Activity() {
    var tablero: Array<ImageButton?> = arrayOfNulls(16)
    var puntuacion: Int = 0
    var aciertos: Int = 0

    //imagenes
    lateinit var imagenes: IntArray
    var fondo: Int = 0

    //variables del juego
    var arrayDesordenado: ArrayList<Int>? = null
    var primero: ImageButton? = null
    var numeroPrimero: Int = 0
    var numeroSegundo: Int = 0
    var bloqueo: Boolean = false
    val handler: Handler = Handler()
    lateinit var binding: JuegoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = JuegoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun cargarTablero() {

        tablero[0] = binding.boton00
        tablero[1] = binding.boton01
        tablero[2] = binding.boton02
        tablero[3] = binding.boton03
        tablero[4] = binding.boton04
        tablero[5] = binding.boton05
        tablero[6] = binding.boton06
        tablero[7] = binding.boton07
        tablero[8] = binding.boton08
        tablero[9] = binding.boton09
        tablero[10] = binding.boton10
        tablero[11] = binding.boton11
        tablero[12] = binding.boton12
        tablero[13] = binding.boton13
        tablero[14] = binding.boton14
        tablero[15] = binding.boton15
    }

    private fun cargarBotones() {
        binding.botonJuegoReiniciar.setOnClickListener { init() }
        binding.botonJuegoSalir.setOnClickListener { finish() }
    }

    private fun cargarTexto() {
        puntuacion = 0
        aciertos = 0
        binding.textoPuntuacion.setText("Puntuacion: $puntuacion")
    }

    private fun cargarImagenes() {
        imagenes = intArrayOf(
            R.drawable.la0,
            R.drawable.la1,
            R.drawable.la2,
            R.drawable.la3,
            R.drawable.la4,
            R.drawable.la5,
            R.drawable.la6,
            R.drawable.la7
        )
        fondo = R.drawable.fondo
    }

    private fun barajar(longitud: Int): ArrayList<Int> {
        val result = ArrayList<Int>()
        for (i in 0 until longitud * 2) {
            result.add(i % longitud)
        }
        Collections.shuffle(result)
        // System.out.println(Arrays.toString(result.toArray()));
        return result
    }

    private fun comprobar(i: Int, imgb: ImageButton?) {
        if (primero == null) {
            primero = imgb
            primero!!.scaleType = ImageView.ScaleType.CENTER_CROP
            primero!!.setImageResource(imagenes[arrayDesordenado!![i]])
            primero!!.isEnabled = false
            numeroPrimero = arrayDesordenado!![i]
        } else {
            bloqueo = true
            imgb!!.scaleType = ImageView.ScaleType.CENTER_CROP
            imgb.setImageResource(imagenes[arrayDesordenado!![i]])
            imgb.isEnabled = false
            numeroSegundo = arrayDesordenado!![i]
            if (numeroPrimero == numeroSegundo) {
                primero = null
                bloqueo = false
                aciertos++
                puntuacion++
                binding.textoPuntuacion.text = "Puntuación: $puntuacion"
                if (aciertos == imagenes.size) {
                    val toast =
                        Toast.makeText(applicationContext, "Has ganado!!", Toast.LENGTH_LONG)
                    toast.show()
                }
            } else {
                handler.postDelayed({
                    primero!!.scaleType = ImageView.ScaleType.CENTER_CROP
                    primero!!.setImageResource(fondo)
                    primero!!.isEnabled = true
                    imgb.scaleType = ImageView.ScaleType.CENTER_CROP
                    imgb.setImageResource(fondo)
                    imgb.isEnabled = true
                    bloqueo = false
                    primero = null
                    puntuacion--
                    binding.textoPuntuacion.text = "Puntuación: $puntuacion"
                }, 1000)
            }
        }
    }

    private fun init() {
        cargarTablero()
        cargarBotones()
        cargarTexto()
        cargarImagenes()
        arrayDesordenado = barajar(imagenes.size)
        for (i in tablero.indices) {
            tablero[i]!!.scaleType = ImageView.ScaleType.CENTER_CROP
            tablero[i]!!.setImageResource(imagenes[arrayDesordenado!![i]])
            //tablero[i].setImageResource(fondo);
        }
        handler.postDelayed({
            for (i in tablero.indices) {
                tablero[i]!!.scaleType = ImageView.ScaleType.CENTER_CROP
                //tablero[i].setImageResource(imagenes[arrayDesordenado.get(i)]);
                tablero[i]!!.setImageResource(fondo)
            }
        }, 500)
        for (i in tablero.indices) {
            val j = i
            tablero[i]!!.isEnabled = true
            tablero[i]!!.setOnClickListener { if (!bloqueo) comprobar(j, tablero[j]) }
        }
    }
}

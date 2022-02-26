package com.diplomado.practica1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.diplomado.practica1.databinding.ActivityFormula1Binding
import com.diplomado.practica1.databinding.ActivityMainBinding

class FormulaRes : AppCompatActivity() {
    private lateinit var binding: ActivityFormula1Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formula1)

        binding = ActivityFormula1Binding.inflate(layoutInflater)
        setContentView(binding.root)



        val parametros = intent.extras
        setFormulas()
    }

    fun setFormulas(){
        val parametros = intent.extras
        val opcFormula = parametros?.getInt("paramOpc")

        with(binding) {

            when (opcFormula) {
                1 -> {
                    imgVExplica.setImageResource(R.drawable.tablaimc)

                    tvResultado.text = getString(R.string.resIMC, imc())
                }
                2 -> {
                    imgVExplica.setImageResource(R.drawable.imgia)
                    tvResultado.text = getString(R.string.resIA, ia())
                }
                3 ->{
                    imgVExplica.setImageResource(R.drawable.igcimg)
                    tvResultado.text = getString(R.string.resGrasa, grasa())
                }

            }
        }

    }

    fun imc(): Double{
        //imc = estatura/peso al cuadrado
        val parametros = intent.extras
        val estaturaMts = parametros?.getDouble("paramEstatura")
        val peso = parametros!!.getDouble("paramPeso")
        val imc =  peso / (estaturaMts!! * estaturaMts)
        return imc
    }

    fun ia(): Double{
        val parametros = intent.extras
        val estaCM = (parametros!!.getDouble("paramEstatura")) * 100 // cambio de mts a cms
        val peso = parametros!!.getDouble("paramPeso")
        val cintura = parametros.getDouble("paramCintura")
        val cadera = parametros.getDouble("paramCadera")
        val ia = 10*(estaCM / (Math.cbrt(peso*cintura*cadera)))
        return ia
    }

    fun grasa(): Double{
        val parametros = intent.extras

        val fM = parametros!!.getInt("paramFM")
        val estaCM = (parametros!!.getDouble("paramEstatura")) * 100 // cambio de mts a cms
        val peso = parametros!!.getDouble("paramPeso")
        val cintura = parametros.getDouble("paramCintura")
        val cadera = parametros.getDouble("paramCadera")
        var grasa = .1

        //constantes para calcular la grasa
        val f1M = 90.41
        val f2M = 3.30
        val f1F = 100.67
        val f2F = 3.06
        val ia = 10*(estaCM / Math.cbrt(peso*cintura*cadera))

        if (fM == 1) {
            grasa = f1M - (f2M * ia)
        }
        if (fM == 0){
            grasa = f1F - (f2F * ia)
        }

        return grasa
    }
}
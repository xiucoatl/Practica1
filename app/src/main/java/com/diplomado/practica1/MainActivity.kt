package com.diplomado.practica1

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.view.get
import androidx.core.view.isVisible
import kotlin.math.log
import com.diplomado.practica1.databinding.ActivityMainBinding
import java.text.AttributedString

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var binding: ActivityMainBinding


    public var opc = 0 // spinner items
    public var fM = 0 // variable para sexo [0 -> Femenino] [1 -> Masculino]

   override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //setSupportActionBar(binding.toolbar)

        val spinner: Spinner = binding.opcionesSpinner
        ArrayAdapter.createFromResource(
            this,
            R.array.opcionesSpinner, android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter=adapter
        }

        //val spinner: Spinner = findViewById(R.id.opciones_spinner)
        spinner.onItemSelectedListener = this

        val radioF: RadioButton = binding.radioFemenino
        //val radioM: RadioButton = binding.radioMasculino

        val txt: TextView = binding.tvTxt


    }

    //class SpinnerActivity : Activity(), AdapterView.OnItemSelectedListener {

        override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
            opc = parent.getItemIdAtPosition(pos).toInt()
            when (opc){
                1 -> visualizaIMC()
                2 -> visualizaIA()
                3 -> visualizaGC()
            }

            // An item was selected. You can retrieve the selected item using
            // parent.getItemAtPosition(pos)
        }

    fun visualizaIMC(){
        with(binding){
            tvTxt.setText(R.string.indicaciones)
            binding.tvTxt.isVisible=true;
            tVDescripcion.setText(R.string.descIMC)
            tVDescripcion.isVisible = true
            iVFormula.isVisible = true
            iVFormula.setImageResource (R.drawable.imgimc)
            etdNestatura.isVisible = true
            etdNPeso.isVisible = true

            etdNCintura.isVisible = false
            etdNcadera.isVisible = false

            btnCalcula.isVisible = true
        }
    }

    fun visualizaIA() {
        with(binding){
            tvTxt.setText(R.string.indicaciones)
            binding.tvTxt.isVisible=true;
            tVDescripcion.setText(R.string.descIAnt)
            tVDescripcion.isVisible = true
            iVFormula.isVisible = true
            iVFormula.setImageResource (R.drawable.imgia)
            etdNestatura.isVisible = true
            etdNPeso.isVisible = true
            etdNCintura.isVisible = true
            etdNcadera.isVisible = true
            btnCalcula.isVisible = true
        }
    }

    fun visualizaGC() {
        with(binding) {
            tvTxt.setText(R.string.indicaciones)
            binding.tvTxt.isVisible = true;
            tVDescripcion.setText(R.string.descGC)
            tVDescripcion.isVisible = true
            iVFormula.isVisible = true
            iVFormula.setImageResource(R.drawable.gcc)
            etdNestatura.isVisible = true
            etdNPeso.isVisible = true
            etdNCintura.isVisible = true
            etdNcadera.isVisible = true
            btnCalcula.isVisible = true
        }
    }

    fun mensaje(mensaje:String){
        Toast.makeText(this@MainActivity, mensaje, Toast.LENGTH_SHORT).show()
    }


        override fun onNothingSelected(parent: AdapterView<*>) {
            // Another interface callback
        }

    fun calcular(view: View) {
        val intent = Intent(this, FormulaRes::class.java)
        val parametros = Bundle()


        with(binding) {
            parametros.putInt("paramOpc", opc)
            parametros.putInt("paramFM", fM)
            if (opc == 1) {
                if (validaCamposIMC()) {
                    parametros.putDouble("paramPeso", etdNPeso.text.toString().toDouble())
                    parametros.putDouble("paramEstatura", etdNestatura.text.toString().toDouble())
                    intent.putExtras(parametros)
                    startActivity(intent)
                }else{
                    when{
                        etdNestatura.text.toString() == "" -> {etdNestatura.error = getString(R.string.campoOb)
                            etdNestatura.requestFocus()
                        }
                        etdNPeso.text.toString() == "" -> { etdNPeso.error = getString(R.string.campoOb)
                            etdNPeso.requestFocus()
                        }
                    }
                    mensaje(getString(R.string.ingresaV))
                }
            }
            else{
                if (validaCamposIA()) {
                    parametros.putDouble("paramPeso", etdNPeso.text.toString().toDouble())
                    parametros.putDouble("paramEstatura", etdNestatura.text.toString().toDouble())
                    parametros.putDouble("paramCintura", etdNCintura.text.toString().toDouble())
                    parametros.putDouble("paramCadera", etdNcadera.text.toString().toDouble())
                    intent.putExtras(parametros)
                    startActivity(intent)
                }else{
                    when{
                        etdNestatura.text.toString() == "" -> {etdNestatura.error = getString(R.string.campoOb)
                            etdNestatura.requestFocus()
                        }
                        etdNPeso.text.toString() == "" -> { etdNPeso.error = getString(R.string.campoOb)
                            etdNPeso.requestFocus()
                        }
                        etdNCintura.text.toString() == "" -> { etdNCintura.error = getString(R.string.campoOb)
                            etdNCintura.requestFocus()
                        }
                        etdNcadera.text.toString() == "" -> { etdNcadera.error = getString(R.string.campoOb)
                            etdNcadera.requestFocus()
                        }
                    }
                    mensaje(getString(R.string.ingresaV))
                }
            }

        }

    }

    private fun validaCamposIMC(): Boolean{
        if(binding.etdNPeso.text.toString() != "" && binding.etdNestatura.text.toString() != "") return true
        else return false
    }

    private fun validaCamposIA(): Boolean{
        if(binding.etdNPeso.text.toString() != "" && binding.etdNestatura.text.toString() != "" && binding.etdNCintura.text.toString() != "" && binding.etdNcadera.text.toString()!="") return true
        else return false
    }

    fun clicRB(view: View) {
        if(binding.radioFemenino.isChecked) {
            fM = 0
            mensaje("Femenino")
        }
        else
        {
            fM = 1
            mensaje("Masculino")
        }
    }


}
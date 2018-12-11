package com.loquat.jose.gestordecontratos

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.DisplayMetrics
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.modal.*
import org.json.JSONObject


class Pop : Activity() {

    //Variables que contienen los campos
    internal var nomEmpresa: String = ""
    internal var tipo: String = ""
    internal var fech_iniOk: String = ""
    internal var fech_finOk: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.modal)

        //Especificaciones de la ventana nueva:

        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)
        val width = dm.widthPixels
        val heigth = dm.heightPixels
        window.setLayout(width, heigth)

        //Botones:
        cerrarCrearContrato.setOnClickListener {
            finish()
        }
        continuar.setOnClickListener {
            //Recolectamos los datos de los Plaintext y los validamos para insertarlos bien:
            if (validarCampos()) {
                insertEnContratos()
                //Pasamos a la asignación de servicios para el contrato
                //val intent = Intent(this, Pop2::class.java)
                //startActivity(intent)
                }
            }
        }


    private fun validarCampos(): Boolean {
        if (TextUtils.isEmpty(nombreEmpresa.text)) run {
            nombreEmpresa.setError(getString(R.string.error_required_field))
            nombreEmpresa.requestFocus()
            return false
        } else {
            nomEmpresa = nombreEmpresa.text.toString()
        }
        if (TextUtils.isEmpty(tipoContrato.text)) run {
            tipoContrato.setError(getString(R.string.error_required_field))
            tipoContrato.requestFocus()
            return false
        } else {
            tipo = tipoContrato.text.toString()
        }
        if (TextUtils.isEmpty(fecha_inicio.text)) run {
            fecha_inicio.setError(getString(R.string.error_required_field))
            fecha_inicio.requestFocus()
            return false
        } else {
            if (estructuraFecha(fecha_inicio.text.toString())) {//Compruebo el formato de las fecha
                var fech_ini = fecha_inicio.text.toString()
                var fech_iniRA = arrayOf(fech_ini.split("/"))
                fech_iniOk = fech_iniRA[3].toString() + "/" + fech_iniRA[2].toString() + "/" + fech_iniRA[1].toString()
            } else {
                fecha_inicio.setError(getString(R.string.error_wrong_format))
                fecha_inicio.requestFocus()
            }
        }
        if (TextUtils.isEmpty(fecha_fin.text)) run {
            fecha_fin.setError(getString(R.string.error_required_field))
            fecha_fin.requestFocus()
            return false
        } else {
            if (estructuraFecha(fecha_fin.text.toString())) {//Compruebo el formato de las fecha
                var fech_fin = fecha_inicio.text.toString()
                var fech_finRA = arrayOf(fech_fin.split("/"))
                fech_finOk = fech_finRA[3].toString() + "/" + fech_finRA[2].toString() + "/" + fech_finRA[1].toString()
            } else {
                fecha_fin.setError(getString(R.string.error_wrong_format))
                fecha_fin.requestFocus()
            }
        }
        return true
    }

    private fun insertEnContratos() {
        val url = "http://localhost:8696/contratos"
        val objJson=JSONObject()
        objJson.put("tipo",tipo)
        objJson.put("fecha_inicio",fech_iniOk)
        objJson.put("fecha_fin",fech_finOk)
        objJson.put("empresaId",Integer.parseInt(nomEmpresa))
        val queue = Volley.newRequestQueue(this)
        val req = JsonObjectRequest(
            Request.Method.POST, url, objJson,
            Response.Listener { response ->
                Toast.makeText(this, "Insert Exitoso", Toast.LENGTH_SHORT).show()
            },
            Response.ErrorListener { error ->
                Toast.makeText(this, "Falló", Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(req)
    }

    private fun estructuraFecha(aux: String): Boolean {
        var patron = "[0-3][0-9]/[0-1][0-9]/[0-9][0-9][0-9][0-9]".toRegex()
        return patron.matches(aux)
    }

    private fun isNumeric(s: String?): Boolean {
        return s != null && s.matches("[-+]?\\d*\\.?\\d+".toRegex())
    }
}
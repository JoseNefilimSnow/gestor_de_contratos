/*package com.loquat.jose.gestordecontratos

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.util.DisplayMetrics
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.modal.*
import org.json.JSONObject


class Pop : AppCompatActivity() {

    //Variables que contienen los campos


    val sqLiteDB = DatabaseHandler(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        Toast.makeText(this, "Estamos dentro", Toast.LENGTH_SHORT).show()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.modal)
        var tipo: String = "a"
        var fech_iniOk = "2003-01-08"
        var fech_finOk = "2004-10-02"
        var nomEmpresa = "1"
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
            validarCampos()


        }
    }

    private fun validarCampos() {
        var aux = 0
        var tipo = ""
        var fech_iniOk = ""
        var fech_finOk = ""
        var nomEmpresa =""
        Toast.makeText(this, "Llego aquí", Toast.LENGTH_SHORT).show()

        var patron = "[0-3][0-9]/[0-1][0-9]/[0-9][0-9][0-9][0-9]".toRegex()//Patrón para comprobar las fechas

        if (TextUtils.isEmpty(nombreEmpresa.text)) run {
            nombreEmpresa.error = (getString(R.string.error_required_field))

            aux++
        } else {
             nomEmpresa = nombreEmpresa.text.toString()
        }
        if (TextUtils.isEmpty(tipoContrato.text)) run {
            tipoContrato.error = (getString(R.string.error_required_field))

            aux++

        } else {
            tipo = tipoContrato.text.toString()
        }
        if (patron.matches(fecha_inicio.text.toString())) {//Compruebo el formato de las fecha
            var fech_ini = fecha_inicio.text.toString()

            //Reordenamos las fechas para que se  quede cn el formato de MYSQL

            var fech_iniRA = arrayOf(fech_ini.split("/"))

            fech_iniOk = fech_iniRA[3].toString() + "/" + fech_iniRA[2].toString() + "/" + fech_iniRA[1].toString()
        } else {
            fecha_inicio.error = (getString(R.string.error_wrong_format))

            aux++
        }
        if (patron.matches(fecha_fin.text.toString())) {//Compruebo el formato de las fecha
            var fech_fin = fecha_inicio.text.toString()

            //Reordenamos las fechas para que se  quede cn el formato de MYSQL

            var fech_finRA = arrayOf(fech_fin.split("/"))

            fech_finOk = fech_finRA[3].toString() + "/" + fech_finRA[2].toString() + "/" + fech_finRA[1].toString()
        } else {
            fecha_fin.error = (getString(R.string.error_wrong_format))

            aux++
        }
        if (aux == 0) {
            postYContinuar(tipo,fech_iniOk,fech_finOk,nomEmpresa)
        } else {

            Toast.makeText(this, "Algo ha ido mal", Toast.LENGTH_SHORT).show()
        }

    }

    private fun postYContinuar(tipo:String,fech_iniOk:String,fech_finOk:String,nomEmpresa:String) {
        //val sqLiteDB = DatabaseHandler(this)
        //sqLiteDB.insertDataContratos(tipo,fech_iniOk,fech_finOk,nomEmpresa)

        //val url2 = "http://192.168.1.35:8696/contratos"

        //Creamos el objeto JSON
        var objJson = JSONObject()
        objJson.put("tipo", tipo)
        objJson.put("fecha_inicio", fech_iniOk)
        objJson.put("fecha_fin", fech_finOk)
        objJson.put("empresaId", Integer.parseInt(nomEmpresa))

        json.setText(""+objJson.toString())

        //val arrayJson = JSONArray()
        // arrayJson.put(objJson)

        val queue2 = Volley.newRequestQueue(this)
        val req = JsonObjectRequest(
            Request.Method.POST, url2, objJson,
            Response.Listener { response ->
                Toast.makeText(this, "Insert Exitoso", Toast.LENGTH_SHORT).show()
            },
            Response.ErrorListener { error: VolleyError ->
                println("Error $error.message")
            }
        )
        queue2.add(req)
        /**
         * Pasamos a la asignación de servicios para el contrato
         * val intent = Intent(this, Pop2::class.java)
         * startActivity(intent)
         */
         *
    }
}
*/
package com.loquat.jose.gestor_de_contratosv3

import android.content.ContentValues
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_add_contrato.*
import org.json.JSONObject
import java.lang.Exception

class AddContratoActivity : AppCompatActivity() {

    val dbTable = "Contratos"
    var id = 0
    var id_empresa=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_contrato)
        val bundle:Bundle? = intent.extras
        id_empresa=bundle!!.getInt("empresaId")
        try{
            id=bundle!!.getInt("id",0)
            if (id!=0){
                tipoEt.setText(bundle.getString("tipo"))
                fechaIniEt.setText(bundle.getString("fecha_ini"))
                fechaFinEt.setText(bundle.getString("fecha_fin"))
            }
        }catch (ex: Exception){}
    }

    fun addFunc(view: View) {
        if(validarCampos(tipoEt.text.toString(),fechaIniEt.text.toString(),fechaFinEt.text.toString())) {
            var dbManager = DbManager(this)

            var values = ContentValues()
            values.put("tipo", tipoEt.text.toString())
            values.put("fecha_inicio", fechaIniEt.text.toString())
            values.put("fecha_fin", fechaFinEt.text.toString())
            values.put("empresaId", id_empresa)


            if (id == 0) {
                val ID = dbManager.insertContrato(values)
                if (ID > 0) {

                    //Creamos el objeto JSON
                    var objJson = JSONObject()
                    objJson.put("tipo", tipoEt.text.toString())
                    objJson.put("fecha_inicio", fechaIniEt.text.toString())
                    objJson.put("fecha_fin", fechaFinEt.text.toString())
                    objJson.put("empresaId", id_empresa)

                    //Cambiar url cada vez
                    var url = "http://192.168.202.98:8696/contratos"

                    val queue = Volley.newRequestQueue(this)
                    val req = JsonObjectRequest(
                        Request.Method.POST, url, objJson,
                        Response.Listener { response ->
                            Toast.makeText(this, "Insert Exitoso", Toast.LENGTH_SHORT).show()
                        },
                        Response.ErrorListener { error: VolleyError ->
                            println("Error $error.message")
                        }
                    )
                    queue.add(req)

                    Toast.makeText(this, "Contrato Añadido", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                }
            } else {

                var selectionArgs = arrayOf(id.toString())
                //Update aun no en linea
                val ID = dbManager.updateContratos(values, "id=?", selectionArgs)
                //Update en linea
                var url = "http://192.168.202.98:8696/contratos/getById/"+id

                var objJson = JSONObject()
                objJson.put("id",id)
                objJson.put("tipo", tipoEt.text.toString())
                objJson.put("fecha_inicio", fechaIniEt.text.toString())
                objJson.put("fecha_fin", fechaFinEt.text.toString())
                objJson.put("empresaId", id_empresa)
                val queueUpdate = Volley.newRequestQueue(this)
                val req = JsonObjectRequest(
                    Request.Method.PUT, url, objJson,
                    Response.Listener { response ->
                        Toast.makeText(this, "Editar Exitoso", Toast.LENGTH_SHORT).show()
                    },
                    Response.ErrorListener { error: VolleyError ->
                        println("Error $error.message")
                    }
                )
                queueUpdate.add(req)
                if (ID > 0) {
                    Toast.makeText(this, "Contrato editado", Toast.LENGTH_SHORT).show()


                    finish()
                } else {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        }else{
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
        }
    }
    private fun validarCampos(tipo:String,fech_iniOk:String,fech_finOk:String):Boolean {
        var aux = 0
        var patron = "[0-9][0-9][0-9][0-9]-[0-3][0-9]-[0-1][0-9]".toRegex()//Patrón para comprobar las fechas

        if (TextUtils.isEmpty(tipo)) run {
            tipoEt.error = (getString(R.string.error_required_field))
            aux++

        }
        if (!patron.matches(fech_iniOk)) {//Compruebo el formato de las fecha
            fechaIniEt.error = (getString(R.string.error_wrong_format))
            aux++
        }
        if (!patron.matches(fech_finOk)) {//Compruebo el formato de las fecha
            fechaFinEt.error = (getString(R.string.error_wrong_format))
            aux++
        }
        if (aux == 0) {
            return true
        } else {
            return false
        }

    }
    //Añadir asignacióm de productos y servicios
}

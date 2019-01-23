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
import kotlinx.android.synthetic.main.activity_add_empresa.*
import org.json.JSONObject
import java.lang.Exception

class AddEmpresa : AppCompatActivity() {

    val dbTable = "Empresas"
    var id = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_empresa)
        val bundle:Bundle? = intent.extras
        try{
            id=bundle!!.getInt("id",0)
            if (id!=0){
                nombreEt.setText(bundle.getString("nombre"))
                dirEt.setText(bundle.getString("direccion"))
                telEt.setText(bundle.getString("telefono"))
                correoEt.setText(bundle.getString("correo"))
            }
        }catch (ex: Exception){}
    }

    fun addFunc(view: View) {
        if(validarCampos(nombreEt.text.toString(),dirEt.text.toString(),correoEt.text.toString(),telEt.text.toString())) {
            var dbManager = DbManager(this)

            var values = ContentValues()
            values.put("nombre", nombreEt.text.toString())
            values.put("direccion", dirEt.text.toString())
            values.put("telefono", telEt.text.toString())
            values.put("correo", correoEt.text.toString())


            if (id == 0) {
                val ID = dbManager.insertEmpresas(values)
                if (ID > 0) {

                    //Creamos el objeto JSON
                    var objJson = JSONObject()
                    objJson.put("nombre", nombreEt.text.toString())
                    objJson.put("direccion", dirEt.text.toString())
                    objJson.put("telefono", telEt.text.toString())
                    objJson.put("correo", correoEt.text.toString())

                    //Cambiar url cada vez
                    var url = "http://192.168.202.42:8696/empresas"

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

                    Toast.makeText(this, "Empresa Añadida", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                }
            } else {

                var selectionArgs = arrayOf(id.toString())
                //Update aun no en linea
                val ID = dbManager.updateEmpresas(values, "id=?", selectionArgs)
                //Update en linea
                var url = "http://192.168.202.42:8696/empresas/getById/"+id

                var objJson = JSONObject()
                objJson.put("id",id)
                objJson.put("nombre", nombreEt.text.toString())
                objJson.put("direccion", dirEt.text.toString())
                objJson.put("telefono", telEt.text.toString())
                objJson.put("correo", correoEt.text.toString())
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
    private fun validarCampos(nombre:String,dir:String,correo:String,tel:String):Boolean {
        var aux = 0
        var patron = "[a-zA-Z0-9.!#\$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)".toRegex()//Patrón para comprobar

        if (TextUtils.isEmpty(nombre)) run {
            nombreEt.error = (getString(R.string.error_required_field))
            aux++

        }
        if (TextUtils.isEmpty(nombre)) run {
            nombreEt.error = (getString(R.string.error_required_field))
            aux++

        }
        if (!patron.matches(correo)) {//Compruebo el formato de las fecha
            correoEt.error = (getString(R.string.error_wrong_format))
            aux++
        }
        if (TextUtils.isEmpty(tel)) run {
            telEt.error = (getString(R.string.error_required_field))
            aux++

        }else if(valTel(tel)){

        }
        if (aux == 0) {
            return true
        } else {
            return false
        }
    }

    private fun valTel(tel: String): Boolean {
        val num = "[0-9]".toRegex()
        var ok :Boolean=true
        for (i in 0 until tel.length) {
            if(!num.matches(tel.get(i).toString())){
                ok=false
                break;
            }
        }
        return ok
    }

    fun closeTab(view: View){
        finish()
    }
}

package com.loquat.jose.gestor_de_contratosv3


import android.app.SearchManager
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.rowempresa.view.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    var listEmpresa = ArrayList<Empresa>()
    var arrayContratos = JSONArray()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Cargar db local
        LoadQuery("%")
        LoadQueryContratos("%")
        //Cargar db-servidor

        //Toast.makeText(this, arrayContratos.toString(), Toast.LENGTH_SHORT).show()

        var url = "http://192.168.201.91:8696/empresas"

        val queue = Volley.newRequestQueue(this)

        val req = JsonArrayRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                try {
                    if(response.length()!= empresasLv.count){
                        var dbManager = DbManager(this)
                        dbManager.deleteAllEmpresas()
                        LoadQuery("%")
                        //Borrar aqui
                    }
                    // Loop de array
                    for (i in 0 until response.length()) {
                        var values = ContentValues()
                        val aux = response.getJSONObject(i)
                        values.put("id", aux.getInt("id"))
                        values.put("nombre", aux.getString("nombre"))
                        values.put("direccion", aux.getString("direccion"))
                        values.put("telefono", aux.getString("telefono"))
                        values.put("correo", aux.getString("correo"))

                        var dbManager = DbManager(this)
                        dbManager.insertEmpresas(values)
                        onResume()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error: VolleyError ->
                //Toast.makeText(this, "No conectado a Internet", Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(req)

       var contratosInDb=JSONArray()
        var url2 = "http://192.168.201.91:8696/contratos"
        val queue2 = Volley.newRequestQueue(this)
        /*
        val req2 = JsonArrayRequest(
            Request.Method.GET, url2, null,
            Response.Listener { response ->
                try {
                    // Loop de array
                    for (i in 0 until response.length()) {
                        var values = ContentValues()
                        val aux = response.getJSONObject(i)
                        contratosInDb.put(aux)
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error: VolleyError ->
                Toast.makeText(this, "No conectado a Internet", Toast.LENGTH_SHORT).show()
            }
        )
        queue2.add(req2)
        */
        var aAniadir=JSONArray()

        var aBorrar=JSONArray()

        if(contratosInDb.length()>arrayContratos.length()){

            for (i in 0 until contratosInDb.length()) {
                for (j in 0 until arrayContratos.length()) {
                    if(contratosInDb.getJSONObject(i).getInt("id")==arrayContratos.getJSONObject(j).getInt("id")){
                        break
                    }else{
                        if(contratosInDb.getJSONObject(i).getInt("id")<arrayContratos.getJSONObject(j).getInt("id")) {
                            if (arrayContratos.getJSONObject(j).getInt("id") > contratosInDb.length()) {
                                aAniadir.put(arrayContratos.getJSONObject(j))
                            }
                        }
                        if(contratosInDb.getJSONObject(i).getInt("id")>arrayContratos.getJSONObject(j).getInt("id")) {
                            if (arrayContratos.getJSONObject(j).getInt("id") > arrayContratos.length()) {
                                aBorrar.put(arrayContratos.getJSONObject(j))
                            }
                        }
                    }
                }
            }
        }else{
            for (i in 0 until arrayContratos.length()) {
                for (j in 0 until contratosInDb.length()) {
                    if(contratosInDb.getJSONObject(j).getInt("id")==arrayContratos.getJSONObject(i).getInt("id")){
                        break
                    }else{
                        if(contratosInDb.getJSONObject(j).getInt("id")<arrayContratos.getJSONObject(i).getInt("id")) {
                            if (arrayContratos.getJSONObject(j).getInt("id") > contratosInDb.length()) {
                                aAniadir.put(arrayContratos.getJSONObject(j))
                            }
                        }
                        if(contratosInDb.getJSONObject(j).getInt("id")>arrayContratos.getJSONObject(i).getInt("id")) {
                            if (arrayContratos.getJSONObject(j).getInt("id") > arrayContratos.length()) {
                                aBorrar.put(arrayContratos.getJSONObject(j))
                            }
                        }
                    }
                }
            }
        }

        for (i in 0 until aAniadir.length()) {
            val queue2 = Volley.newRequestQueue(this)
            var auxJson:JSONObject=aAniadir.getJSONObject(i)
            val req = JsonObjectRequest(
                Request.Method.POST, url2, auxJson,
                Response.Listener { response ->
                    Toast.makeText(this, "Insert Exitoso", Toast.LENGTH_SHORT).show()
                },
                Response.ErrorListener { error: VolleyError ->
                    println("Error $error.message")
                }
            )
            queue2.add(req)
        }
        /*
        for (i in 0 until aBorrar.length()) {
            val queue2 = Volley.newRequestQueue(this)
            var id:JSONObject=aBorrar.getJSONObject(i)
            var url = "http://192.168.202.94:8696/contratos/getById/"+id
            val req = JsonObjectRequest(
                Request.Method.DELETE, url2, null,
                Response.Listener { response ->
                    Toast.makeText(this, "Borrado Exitoso", Toast.LENGTH_SHORT).show()
                },
                Response.ErrorListener { error: VolleyError ->
                    println("Error $error.message")
                }
            )
            queue2.add(req)
        }*/

    }

    override fun onResume() {
        super.onResume()
        LoadQuery("%")
    }

    private fun LoadQuery(title: String) {
        val dbManager = DbManager(this)
        val projections = arrayOf("id", "nombre", "direccion", "telefono", "correo")
        val selectionArgs = arrayOf(title)
        val cursor = dbManager.QueryEmpresas(projections, "nombre like ?", selectionArgs, "nombre")
        listEmpresa.clear()

        if (cursor!!.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex("id"))
                val nombre = cursor.getString(cursor.getColumnIndex("nombre"))
                val dir = cursor.getString(cursor.getColumnIndex("direccion"))
                val tel = cursor.getString(cursor.getColumnIndex("telefono"))
                val mail = cursor.getString(cursor.getColumnIndex("correo"))

                listEmpresa.add(Empresa(id, nombre, dir, tel, mail))
            } while (cursor.moveToNext())
        }
        var myEmpresaAdapter = myEmpresaAdapter(this, listEmpresa)
        //set adapter
        empresasLv.adapter = myEmpresaAdapter
        //Total number of task
        val total = empresasLv.count
        //Action Bar
        val mActionBar = supportActionBar
        if (mActionBar != null) {
            //set to action bar as subtitle of actionbat
            mActionBar.subtitle = "-Hay " + total + " empresas-"
        }
    }
    private fun LoadQueryContratos(title: String) {
        val dbManager = DbManager(this)
        val projections = arrayOf("id", "tipo", "fecha_inicio", "fecha_fin", "empresaId")
        val selectionArgs = arrayOf(title)
        val cursor = dbManager.QueryContratos(projections, "tipo like ?", selectionArgs, "tipo")
        arrayContratos= JSONArray()

        if (cursor!!.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex("id"))
                val tipo = cursor.getString(cursor.getColumnIndex("tipo"))
                val fecha_ini = cursor.getString(cursor.getColumnIndex("fecha_inicio"))
                val fecha_fin = cursor.getString(cursor.getColumnIndex("fecha_fin"))
                val idEmpresa = cursor.getInt(cursor.getColumnIndex("empresaId"))

                var auxiliar = JSONObject()
                auxiliar.put("id",id)
                auxiliar.put("tipo",tipo)
                auxiliar.put("fecha_inicio",fecha_ini)
                auxiliar.put("fecha_fin",fecha_fin)
                auxiliar.put("empresaId",idEmpresa)

                arrayContratos.put(auxiliar)

            } while (cursor.moveToNext())
        }
        var myEmpresaAdapter = myEmpresaAdapter(this, listEmpresa)
        //set adapter
        empresasLv.adapter = myEmpresaAdapter
        //Total number of task
        val total = empresasLv.count
        //Action Bar
        val mActionBar = supportActionBar
        if (mActionBar != null) {
            //set to action bar as subtitle of actionbat
            mActionBar.subtitle = "-Hay " + total + " empresas-"
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        val sv: SearchView = menu!!.findItem(R.id.app_bar_search).actionView as SearchView
        val sm = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        sv.setSearchableInfo(sm.getSearchableInfo(componentName))
        sv.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                LoadQuery("%" + query + "%")
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                LoadQuery("%" + newText + "%")
                return false
            }

        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item != null) {
            when (item.itemId) {
                R.id.sync -> {

                    LoadQuery("%")
                    LoadQueryContratos("%")

                    //Toast.makeText(this, arrayContratos.toString(), Toast.LENGTH_SHORT).show()

                    var url = "http://192.168.201.91:8696/empresas"
                    val queue1 = Volley.newRequestQueue(this)
                    val req = JsonArrayRequest(
                        Request.Method.GET, url, null,
                        Response.Listener { response ->
                            try {
                                if(response.length()!= empresasLv.count){
                                    var dbManager = DbManager(this)
                                    dbManager.deleteAllEmpresas()
                                    LoadQuery("%")
                                    //Borrar aqui
                                }
                                // Loop through the array elements
                                for (i in 0 until response.length()) {
                                    var values = ContentValues()
                                    val aux = response.getJSONObject(i)
                                    values.put("id", aux.getInt("id"))
                                    values.put("nombre", aux.getString("nombre"))
                                    values.put("direccion", aux.getString("direccion"))
                                    values.put("telefono", aux.getString("telefono"))
                                    values.put("correo", aux.getString("correo"))

                                    var dbManager = DbManager(this)
                                    dbManager.insertEmpresas(values)
                                }
                            } catch (e: JSONException) {
                                e.printStackTrace()
                            }
                        },
                        Response.ErrorListener { error: VolleyError ->
                            println("Error $error.message")
                        }
                    )
                    queue1.add(req)
                    /*var contratosInDb=JSONArray()
                    var url2 = "http://192.168.202.94:8696/contratos"
                    val queue2 = Volley.newRequestQueue(this)

                    val req2 = JsonArrayRequest(
                        Request.Method.GET, url2, null,
                        Response.Listener { response ->
                            try {
                                // Loop de array
                                for (i in 0 until response.length()) {
                                    var values = ContentValues()
                                    val aux = response.getJSONObject(i)
                                    contratosInDb.put(aux)
                                }
                            } catch (e: JSONException) {
                                e.printStackTrace()
                            }
                        },
                        Response.ErrorListener { error: VolleyError ->
                            Toast.makeText(this, "No conectado a Internet", Toast.LENGTH_SHORT).show()
                        }
                    )
                    queue2.add(req2)

                    var aAniadir=JSONArray()

                    var aBorrar=JSONArray()

                    if(contratosInDb.length()>arrayContratos.length()){

                        for (i in 0 until contratosInDb.length()) {
                            for (j in 0 until arrayContratos.length()) {
                                if(contratosInDb.getJSONObject(i).getInt("id")==arrayContratos.getJSONObject(j).getInt("id")){
                                    break
                                }else{
                                    if(contratosInDb.getJSONObject(i).getInt("id")<arrayContratos.getJSONObject(j).getInt("id")) {
                                        if (arrayContratos.getJSONObject(j).getInt("id") > contratosInDb.length()) {
                                            aAniadir.put(arrayContratos.getJSONObject(j))
                                        }
                                    }
                                    if(contratosInDb.getJSONObject(i).getInt("id")>arrayContratos.getJSONObject(j).getInt("id")) {
                                        if (arrayContratos.getJSONObject(j).getInt("id") > arrayContratos.length()) {
                                            aBorrar.put(arrayContratos.getJSONObject(j))
                                        }
                                    }
                                }
                            }
                        }
                    }else{
                        for (i in 0 until arrayContratos.length()) {
                            for (j in 0 until contratosInDb.length()) {
                                if(contratosInDb.getJSONObject(j).getInt("id")==arrayContratos.getJSONObject(i).getInt("id")){
                                    break
                                }else{
                                    if(contratosInDb.getJSONObject(j).getInt("id")<arrayContratos.getJSONObject(i).getInt("id")) {
                                        if (arrayContratos.getJSONObject(j).getInt("id") > contratosInDb.length()) {
                                            aAniadir.put(arrayContratos.getJSONObject(j))
                                        }
                                    }
                                    if(contratosInDb.getJSONObject(j).getInt("id")>arrayContratos.getJSONObject(i).getInt("id")) {
                                        if (arrayContratos.getJSONObject(j).getInt("id") > arrayContratos.length()) {
                                            aBorrar.put(arrayContratos.getJSONObject(j))
                                        }
                                    }
                                }
                            }
                        }
                    }

                    for (i in 0 until aAniadir.length()) {
                        val queue2 = Volley.newRequestQueue(this)
                        var auxJson:JSONObject=aAniadir.getJSONObject(i)
                        val req = JsonObjectRequest(
                            Request.Method.POST, url2, auxJson,
                            Response.Listener { response ->
                                Toast.makeText(this, "Insert Exitoso", Toast.LENGTH_SHORT).show()
                            },
                            Response.ErrorListener { error: VolleyError ->
                                println("Error $error.message")
                            }
                        )
                        queue2.add(req)
                    }
                    for (i in 0 until aBorrar.length()) {
                        val queue2 = Volley.newRequestQueue(this)
                        var id:JSONObject=aBorrar.getJSONObject(i)
                        var url = "http://192.168.202.94:8696/contratos/getById/"+id
                        val req = JsonObjectRequest(
                            Request.Method.DELETE, url2, null,
                            Response.Listener { response ->
                                Toast.makeText(this, "Borrado Exitoso", Toast.LENGTH_SHORT).show()
                            },
                            Response.ErrorListener { error: VolleyError ->
                                println("Error $error.message")
                            }
                        )
                        queue2.add(req)
                    }*/
                    Toast.makeText(this, "Empresas borradas y sincronizado", Toast.LENGTH_SHORT).show()
                    onResume()
                }
                R.id.action_settings -> {
                    Toast.makeText(this, "Configuraciones por añadir", Toast.LENGTH_SHORT).show()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    inner class myEmpresaAdapter : BaseAdapter {
        var listEmpresaAdapter = ArrayList<Empresa>()
        var context: Context? = null

        constructor(context: Context, listEmpresaAdapter: ArrayList<Empresa>) : super() {
            this.listEmpresaAdapter = listEmpresaAdapter
            this.context = context
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var myView = layoutInflater.inflate(R.layout.rowempresa, null)
            var myEmpresa = listEmpresaAdapter[position]
            myView.nombreEmpresaTv.text = myEmpresa.nombre
            //Delete
            myView.details.setOnClickListener {
                goToDetails(myEmpresa)
            }
            return myView
        }


        override fun getItem(position: Int): Any {
            return listEmpresaAdapter[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return listEmpresaAdapter.size
        }
    }

    private fun goToDetails(aux: Empresa) {
        var intent = Intent(this, ListaContratosEmpresa::class.java)
        intent.putExtra("id", aux.id)
        intent.putExtra("nombre", aux.nombre)
        intent.putExtra("direccion", aux.direccion)
        intent.putExtra("telefono", aux.tel)
        intent.putExtra("correo", aux.correo)
        startActivity(intent)
    }
}

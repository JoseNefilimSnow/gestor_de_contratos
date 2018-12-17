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
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.rowempresa.view.*
import org.json.JSONException

class MainActivity : AppCompatActivity() {

    var listEmpresa = ArrayList<Empresa>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Load from DB
        LoadQuery("%")
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
                    var url = "http://192.168.1.35:8696/empresas"
                    val queue1 = Volley.newRequestQueue(this)
                    val req = JsonArrayRequest(
                        Request.Method.GET, url, null,
                        Response.Listener { response ->
                            try {
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
                    Toast.makeText(this, "Empresas borradas y sincronizado", Toast.LENGTH_SHORT).show()

                }
                R.id.action_settings -> {
                    Toast.makeText(this, "Configuraciones WIP", Toast.LENGTH_SHORT).show()
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

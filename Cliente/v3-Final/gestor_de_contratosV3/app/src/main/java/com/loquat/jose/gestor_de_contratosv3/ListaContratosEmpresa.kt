package com.loquat.jose.gestor_de_contratosv3

import android.app.SearchManager
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
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_lista_contratos_empresa.*
import kotlinx.android.synthetic.main.rowcontratos.view.*
import java.lang.Exception

class ListaContratosEmpresa : AppCompatActivity() {
    var id_empresa = 0
    var listContratos = ArrayList<Contrato>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_contratos_empresa)
        try{
            val bundle:Bundle? = intent.extras
            id_empresa=bundle!!.getInt("id",0)
            nombreEmpresaDetailTv.setText(bundle.getString("nombre"))
            direccionEmpresaDetailTv.setText(bundle.getString("direccion"))
            telefonoEmpresaDetailTv.setText(bundle.getString("telefono"))
            correoEmpresaDetailTv.setText(bundle.getString("correo"))
        }catch (ex: Exception){}
        LoadQuery("%")
    }
    override fun onResume() {
        super.onResume()
        LoadQuery("%")
    }
    private fun LoadQuery(title: String) {
        var dbManager = DbManager(this)
        val projections = arrayOf("id", "tipo", "fecha_inicio","fecha_fin","empresaId")
        val selectionArgs = arrayOf(title)
        val cursor = dbManager.QueryContratos(projections, "tipo like ?", selectionArgs, "tipo")
        listContratos.clear()

        if (cursor!!.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex("id"))
                val tipo = cursor.getString(cursor.getColumnIndex("tipo"))
                val fecha_inicio = cursor.getString(cursor.getColumnIndex("fecha_inicio"))
                val fecha_fin = cursor.getString(cursor.getColumnIndex("fecha_fin"))
                val empresaId = cursor.getInt(cursor.getColumnIndex("empresaId"))
                if(id_empresa==empresaId) {
                    listContratos.add(Contrato(id, tipo, fecha_inicio, fecha_fin, empresaId))
                }
            } while (cursor.moveToNext())
        }
        var myContratoAdapter = MyContratoAdapter(this, listContratos)
        //set adapter
        contratosLv.adapter = myContratoAdapter
        //Total number of task
        val total = contratosLv.count
        //Action Bar
        val mActionBar = supportActionBar
        if (mActionBar != null) {
            //set to action bar as subtitle of actionbat
            mActionBar.subtitle = "Tienes " + total + " contratos"
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu2, menu)

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
                R.id.addContrato -> {
                    var intent = Intent(this, AddContratoActivity::class.java)
                    intent.putExtra("empresaId", id_empresa)
                    startActivity(intent)

                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
    inner class MyContratoAdapter : BaseAdapter {
        var listContratoAdapter = ArrayList<Contrato>()
        var context: Context? = null

        constructor(context: Context, listContratoAdapter: ArrayList<Contrato>) : super() {
            this.listContratoAdapter = listContratoAdapter
            this.context = context
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var myView = layoutInflater.inflate(R.layout.rowcontratos, null)
            var myContrato = listContratoAdapter[position]
            myView.contratosTv.text = "Contrato " + myContrato.id+""
            myView.tipoTv.text = myContrato.tipo
            myView.fecha_inicioTv.text = myContrato.fecha_ini
            myView.fecha_finTv.text = myContrato.fecha_fin
            //Delete
            myView.deleteBtn.setOnClickListener {
                var dbManager = DbManager(this.context!!)
                val selectionArgs = arrayOf(myContrato.id.toString())
                dbManager.deleteContratos("id=?", selectionArgs)
                LoadQuery("%")

                //Delete en base de datos
                //Cambiar url cada vez
                var url = "http://192.168.202.95:8696/contratos/getById/"+myContrato.id

                val queue2 = Volley.newRequestQueue(context)
                val req = JsonObjectRequest(
                    Request.Method.DELETE, url, null,
                    Response.Listener { response ->
                        Toast.makeText(context, "Borrado Exitoso", Toast.LENGTH_SHORT).show()
                    },
                    Response.ErrorListener { error: VolleyError ->
                        println("Error $error.message")
                    }
                )
                queue2.add(req)
            }
            myView.editBtn.setOnClickListener {
                goToUpdateFun(myContrato)
            }
            return myView
        }


        override fun getItem(position: Int): Any {
            return listContratoAdapter[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return listContratoAdapter.size
        }
    }

    private fun goToUpdateFun(myContrato: Contrato) {
        var intent = Intent(this, AddContratoActivity::class.java)
        intent.putExtra("id", myContrato.id)
        intent.putExtra("tipo", myContrato.tipo)
        intent.putExtra("fecha_ini", myContrato.fecha_ini)
        intent.putExtra("fecha_fin", myContrato.fecha_fin)
        intent.putExtra("empresaId", id_empresa)
        startActivity(intent)
    }
}

package com.loquat.jose.gestordecontratos


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
import kotlinx.android.synthetic.main.activity_add_to_empresa.view.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.modalempresa.view.*


class MainActivity : AppCompatActivity() {
    var listaEmpresas = ArrayList<Empresa>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Cargamos los datos
        LoadQuery("%")

    }

    override fun onResume() {
        super.onResume()
        LoadQuery("%")
    }

    private fun LoadQuery(nombre: String) {
        var dbManager = DatabaseHandler(this)
        val projections = arrayOf("nombre", "direccion", "telefono", "correo")
        val selectionArgs = arrayOf(nombre)
        val cursor = dbManager.Query(projections, "nombre like ?", selectionArgs, "nombre")
        listaEmpresas.clear()
        if (cursor!!.moveToFirst()) {
            do {
                val nombre_empresa = cursor!!.getString(cursor.getColumnIndex("nombre"))
                val direccion_empresa = cursor!!.getString(cursor.getColumnIndex("direccion"))
                val telefono_empresa = cursor!!.getString(cursor.getColumnIndex("telefono"))
                val correo_empresa = cursor!!.getString(cursor.getColumnIndex("correo"))
            } while (cursor.moveToNext())
        }
        var empresasAdapter = EmpresasAdapter(this, listaEmpresas)
        //Set Adapter
        listEmpresas.adapter=empresasAdapter
        //Pillar todas las tareas de la lista
        var total = listEmpresas.count
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        val sv: SearchView = menu!!.findItem(R.id.app_search).actionView as SearchView
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
                /*
                Para el Add:
                R.id.add ->{
                    startActivity(Intent(this, AddToEmpresa::class.java))
                }*/
                R.id.action_settings -> {
                    Toast.makeText(this,"Aún tengo que añadir esto",Toast.LENGTH_SHORT).show()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }



    inner class EmpresasAdapter : BaseAdapter {
        var listEmpresaAdapter = ArrayList<Empresa>()
        var context: Context? = null

        constructor(context: Context?, listaEmpresaArray: ArrayList<Empresa>) : super() {

            this.listEmpresaAdapter = listaEmpresaArray
            this.context = context

        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var vista = layoutInflater.inflate(R.layout.modalempresa, null)
            var empresaVista = listEmpresaAdapter[position]
            vista.nombredelaempresa.text = empresaVista.nombre

             vista.moreBtn.setOnClickListener {
                 verYAñadirAEmpresa(empresaVista);
            }

             /** codigo que añadir para eliminar y editar:
             * vista.botondeborrar.setOnClickListener{
             *  var dbManager = DatabaseHandler (this)
             *  val selectionArgs = arrayof(objeto *en este caso empresaVista*.id.toString())
             *  dbManager.delete("id?",selectionArgs)
             *  LoadQuery("%")
             * }
             * vista.botondeeditar.setOnClickListener{
             *  GoToUpdateFun(objeto*en este caso empresaVista*)
             *  (private fun GoToUpdateFun(objeto:Objeto){
             *      var intent = Intent(this,AddToEmpresa::class.java)
             *      intent.putExtra("nombre de campo",objeto.datoAPoner)//poner los valores en los campos
             *      startActivity(intent)
             *  })
             * }
             * */
            return vista
        }

        private fun verYAñadirAEmpresa(aux:Empresa) {
            var intent = Intent(this.context, SeleccionEmpresa::class.java)
            intent.putExtra("id",aux.id)
            intent.putExtra("nombre",aux.nombre)
            intent.putExtra("dirección",aux.direccion)
            intent.putExtra("telefono",aux.tel)
            intent.putExtra("correo",aux.correo)
            startActivity(intent)        }

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
}



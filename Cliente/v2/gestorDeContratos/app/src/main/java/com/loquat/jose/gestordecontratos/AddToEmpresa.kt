package com.loquat.jose.gestordecontratos

import android.content.ContentValues
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_to_empresa.*
import java.lang.Exception

class AddToEmpresa : AppCompatActivity() {

    val TABLE_EMPRESA = "empresas"
    val TABLE_CONTRATOS = "contratos"
    var id_contratos = 0

    var idtest = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_to_empresa)

        try{
            val bundle=intent.extras
            id_contratos=bundle.getInt("id",0)
            if(id_contratos!=0){
                tipoContrato.setText(bundle.getString("tipo"))
                fecha_inicio.setText(bundle.getString("fecha_inicio"))
                fecha_fin.setText(bundle.getString("fecha_fin"))
            }
        }catch(e:Exception){}
    }

    fun addFunc(view: View) {
        var db = DatabaseHandler(this)
        var values = ContentValues()
        var aux: Contrato =
            Contrato(
                tipoContrato.text.toString(),
                fecha_inicio.text.toString(),
                fecha_fin.text.toString(),
                idtest
            )//"Select id from" + TABLE_EMPRESA + "where nombre =" + nombreEmpresaTag.text)
        values.put("tipo", tipoContrato.text.toString())
        values.put("fecha_inicio", fecha_inicio.text.toString())
        values.put("fecha_fin", fecha_fin.text.toString())
        //values.put("empresaId",
        // "Select id from" + TABLE_EMPRESA + "where nombre =" + nombreEmpresaTag.text)

        if (id_contratos == 0) {
            val insert = db.insertDataContratos(aux)
            if (id_contratos > 0) {
                Toast.makeText(this, "Insertado", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "No insertado", Toast.LENGTH_SHORT).show()
            }
        } else {
            var selectionArgs = arrayOf(id_contratos.toString())
            val ID = db.update(values, "id=?", selectionArgs)
            if (id_contratos > 0) {
                Toast.makeText(this, "Actualizado", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "No actualizado", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

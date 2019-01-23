package com.loquat.jose.gestor_de_contratosv3

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class ListaCategorias : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_categorias)
    }
    fun closeTab(view: View){
        finish()
    }
}

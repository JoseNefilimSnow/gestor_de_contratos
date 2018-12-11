package com.loquat.jose.gestordecontratos


import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addContrato.setOnClickListener {
            // Handler code here.
            val intent = Intent(this, Pop::class.java)
            startActivity(intent)
        }
    }
}
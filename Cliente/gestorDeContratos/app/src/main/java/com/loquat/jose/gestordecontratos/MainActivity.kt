package com.loquat.jose.gestordecontratos

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {
    internal var add: Button = findViewById(R.id.add)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        add.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this@MainActivity, Pop::class));
        })
    }
}

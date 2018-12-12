package com.loquat.jose.gestordecontratos


import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val url = "http://192.168.202.99:8696/contratos"

        val queue1 = Volley.newRequestQueue(this)
        val req = JsonArrayRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                see.setText(response.toString())
            },
            Response.ErrorListener { error: VolleyError ->
                println("Error $error.message")
            }
        )
        queue1.add(req)

        addContrato.setOnClickListener {
            // Handler code here.
            val intent = Intent(this, Pop::class.java)
            startActivity(intent)

        }


    }
}
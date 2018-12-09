package com.loquat.jose.gestordecontratos

import android.app.Activity
import android.os.Bundle
import android.util.DisplayMetrics


class Pop : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.modal)
        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)

        val width = dm.widthPixels
        val heigth = dm.heightPixels


        window.setLayout(width, heigth)

    }

    private fun isNumeric(s: String?): Boolean {
        return s != null && s.matches("[-+]?\\d*\\.?\\d+".toRegex())
    }
}
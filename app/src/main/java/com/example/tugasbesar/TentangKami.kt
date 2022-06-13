package com.example.tugasbesar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

class TentangKami : AppCompatActivity() {

    lateinit var btnLokasi : ImageView
    lateinit var btnBeranda : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tentang_kami)

        btnBeranda = findViewById(R.id.btn_beranda)
        btnLokasi = findViewById(R.id.map)

        btnBeranda.setOnClickListener {
            val i = Intent(this@TentangKami, HalamanAdmin::class.java)
            startActivity(i)
        }
        btnLokasi.setOnClickListener {
            val i = Intent(this@TentangKami, Lokasi::class.java)
            startActivity(i)
        }

    }
}
package com.example.tugasbesar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

class ProfilAdmin : AppCompatActivity() {

    lateinit var btnTentangKami : Button
    lateinit var btnLokasi : ImageView
    lateinit var btnBeranda : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profil_admin)

        btnTentangKami = findViewById(R.id.btn_tentangkami)
        btnLokasi = findViewById(R.id.map)
        btnBeranda = findViewById(R.id.btn_beranda)

        btnTentangKami.setOnClickListener {
            val i = Intent(this@ProfilAdmin, TentangKami::class.java)
            startActivity(i)
        }
        btnLokasi.setOnClickListener {
            val i = Intent(this@ProfilAdmin, Lokasi::class.java)
            startActivity(i)
        }
        btnBeranda.setOnClickListener {
            val i = Intent(this@ProfilAdmin, HalamanAdmin::class.java)
            startActivity(i)
        }
    }
}
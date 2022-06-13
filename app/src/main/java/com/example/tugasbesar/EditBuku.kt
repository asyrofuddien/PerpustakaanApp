package com.example.tugasbesar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

class EditBuku : AppCompatActivity() {

    lateinit var btnLokasi: ImageView
    lateinit var btnTentangKami : Button
    lateinit var btnBeranda : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_buku)

        btnLokasi = findViewById(R.id.map)
        btnTentangKami = findViewById(R.id.btn_tentangkami)
        btnBeranda = findViewById(R.id.btn_beranda)

        btnTentangKami.setOnClickListener {
            val i = Intent(this@EditBuku, TentangKami::class.java)
            startActivity(i)
        }
        btnBeranda.setOnClickListener {
            val i = Intent(this@EditBuku, HalamanAdmin::class.java)
            startActivity(i)
        }
        btnLokasi.setOnClickListener {
            val i = Intent(this@EditBuku, Lokasi::class.java)
            startActivity(i)
        }
    }
}
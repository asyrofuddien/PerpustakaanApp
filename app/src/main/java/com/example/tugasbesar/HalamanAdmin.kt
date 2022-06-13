package com.example.tugasbesar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

class HalamanAdmin : AppCompatActivity() {

    lateinit var btnKelolaBuku : Button
    lateinit var btnTentangKami : Button
    lateinit var btnLogOut : Button
    lateinit var btnLokasi : ImageView
    lateinit var btnDetailPinjaman : Button
    lateinit var btnProfil : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_halaman_admin)

        btnKelolaBuku = findViewById(R.id.btn_buku)
        btnLogOut = findViewById(R.id.btn_logout)
        btnTentangKami = findViewById(R.id.btn_tentangkami)
        btnLokasi = findViewById(R.id.map)
        btnDetailPinjaman = findViewById(R.id.btn_peminjaman)
        btnProfil = findViewById(R.id.profil)

        btnKelolaBuku.setOnClickListener {
            val i = Intent(this@HalamanAdmin, KelolaBuku::class.java)
            startActivity(i)
        }


        btnTentangKami.setOnClickListener {
            val i = Intent(this@HalamanAdmin, TentangKami::class.java)
            startActivity(i)
        }
        btnLokasi.setOnClickListener {
            val i = Intent(this@HalamanAdmin, Lokasi::class.java)
            startActivity(i)
        }
        btnDetailPinjaman.setOnClickListener {
            val i = Intent(this@HalamanAdmin, DetailPinjaman::class.java)
            startActivity(i)
        }
        btnProfil.setOnClickListener {
            val i = Intent(this@HalamanAdmin, ProfilAdmin::class.java)
            startActivity(i)
        }
        btnLogOut.setOnClickListener {
            val i = Intent(this@HalamanAdmin, MainActivity::class.java)
            startActivity(i)
        }

    }
}
package com.example.tugasbesar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

class HalamanUser : AppCompatActivity() {

    lateinit var btnTentangKami : Button
    lateinit var btnLokasi : ImageView
    lateinit var btnLogOut : Button
    lateinit var btnKoleksiBuku : Button
    lateinit var btnRiwayatPeminjaman : Button
    lateinit var btnProfil : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_halaman_user)

        btnLogOut = findViewById(R.id.btn_logout2)
        btnTentangKami = findViewById(R.id.btn_tentangkami)
        btnLokasi = findViewById(R.id.map)
        btnKoleksiBuku = findViewById(R.id.btn_koleksi)
        btnRiwayatPeminjaman = findViewById(R.id.btn_riwayat)
        btnProfil = findViewById(R.id.profil)


        btnTentangKami.setOnClickListener {
            val i = Intent(this@HalamanUser, TentangKami::class.java)
            startActivity(i)
        }
        btnLokasi.setOnClickListener {
            val i = Intent(this@HalamanUser, Lokasi::class.java)
            startActivity(i)
        }
        btnKoleksiBuku.setOnClickListener {
            val i = Intent(this@HalamanUser, KoleksiBuku::class.java)
            startActivity(i)
        }
        btnRiwayatPeminjaman.setOnClickListener {
            val i = Intent(this@HalamanUser, RiwayatPeminjaman::class.java)
            startActivity(i)
        }
        btnProfil.setOnClickListener {
            val i = Intent(this@HalamanUser, ProfilUser::class.java)
            startActivity(i)
        }
        btnLogOut.setOnClickListener {
            val i = Intent(this@HalamanUser, MainActivity::class.java)
            startActivity(i)
        }


    }
}
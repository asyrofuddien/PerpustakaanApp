package com.example.tugasbesar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    lateinit var btnDaftarSiswa : Button
    lateinit var btnDaftarPetugas : Button
    lateinit var btnLoginAdmin : Button
    lateinit var btnLoginSiswa : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnDaftarSiswa = findViewById(R.id.btn_daftar_siswa)
        btnDaftarPetugas = findViewById(R.id.btn_daftar_petugas)
        btnLoginAdmin = findViewById(R.id.btn_login_admin)
        btnLoginSiswa = findViewById(R.id.btn_login_siswa)


        btnDaftarSiswa.setOnClickListener {
            val i = Intent(this@MainActivity, DaftarSiswa::class.java)
            startActivity(i)
        }
        btnDaftarPetugas.setOnClickListener {
            val i = Intent(this@MainActivity, DaftarPetugas::class.java)
            startActivity(i)
        }
        btnLoginAdmin.setOnClickListener {
            val i = Intent(this@MainActivity, HalamanAdmin  ::class.java)
            startActivity(i)
        }
        btnLoginSiswa.setOnClickListener {
            val i = Intent(this@MainActivity, HalamanUser  ::class.java)
            startActivity(i)
        }
    }

}
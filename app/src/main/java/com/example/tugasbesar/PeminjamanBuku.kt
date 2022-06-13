package com.example.tugasbesar

import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class PeminjamanBuku : AppCompatActivity() {

    lateinit var i:Intent

    lateinit var btnTentangKami : Button
    lateinit var btnLokasi : ImageView
    lateinit var btnBeranda : Button
    lateinit var btnPinjam : Button

    lateinit var edIdBuku : EditText
    lateinit var edIdPetugsa : EditText
    lateinit var edIdSiswa : EditText
    lateinit var edTglPinjam : EditText
    lateinit var edTglKembali : EditText


    val calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_peminjaman_buku)

        btnTentangKami = findViewById(R.id.btn_tentangkami)
        btnLokasi = findViewById(R.id.map)
        btnBeranda = findViewById(R.id.btn_beranda)

        btnPinjam = findViewById(R.id.btn_peminjaman)
        edIdBuku = findViewById(R.id.ed_IdBuku)
        edTglPinjam = findViewById(R.id.ed_TanggalPinjam)
        edTglKembali = findViewById(R.id.ed_TanggalKembali)
        edIdPetugsa = findViewById(R.id.ed_idPetugas)
        edIdSiswa = findViewById(R.id.ed_IdSiswa)


        i=intent
        if (i.hasExtra("editmode")){
            if (i.getStringExtra("editmode").equals("1")){
                onEditMode()
            }
        }
        val dateSetListener_pinjam = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateinViewPinjam()
            }


        }
        val dateSetListener_kembali = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateinViewKemballi()
            }


        }
        edTglPinjam.setOnClickListener {
            DatePickerDialog(
                this,
                dateSetListener_pinjam,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
        edTglKembali.setOnClickListener {
            DatePickerDialog(
                this,
                dateSetListener_kembali,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }



        btnTentangKami.setOnClickListener {
            val i = Intent(this@PeminjamanBuku, TentangKami::class.java)
            startActivity(i)
        }
        btnLokasi.setOnClickListener {
            val i = Intent(this@PeminjamanBuku, Lokasi::class.java)
            startActivity(i)
        }
        btnBeranda.setOnClickListener {
            val i = Intent(this@PeminjamanBuku, HalamanUser::class.java)
            startActivity(i)
        }
        btnPinjam.setOnClickListener {
            val loading = ProgressDialog(this)
            loading.setMessage("Menambahkan data...")
            loading.show()

            AndroidNetworking.post(ApiEndPoint.CREATE_peminjaman)
                .addBodyParameter("tanggal_pinjam", edTglPinjam.text.toString())
                .addBodyParameter("tanggal_kembali", edTglKembali.text.toString())
                .addBodyParameter("id_buku", edIdBuku.text.toString())
                .addBodyParameter("id_siswa", edIdSiswa.text.toString())
                .addBodyParameter("id_petugas", edIdPetugsa.text.toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {
                    override fun onResponse(response: JSONObject?) {
                        loading.dismiss()
                        Toast.makeText(applicationContext,response?.getString("message"), Toast.LENGTH_SHORT).show()

                        if(response?.getString("message")?.contains("successfully")!!){
                            this@PeminjamanBuku.finish()
                        }
                        startActivity(Intent(this@PeminjamanBuku,KoleksiBuku::class.java))
                    }

                    override fun onError(anError: ANError?) {
                        loading.dismiss()
                        Log.d("ON ERROR", anError?.errorDetail.toString())
                        Toast.makeText(applicationContext, "Failure", Toast.LENGTH_SHORT).show()
                    }

                })
        }

    }

    private fun updateDateinViewKemballi() {
        val newFormat = "yyyy/MM/dd"
        val sdf = SimpleDateFormat(newFormat, Locale.US)
        edTglKembali.setText(sdf.format(calendar.time))
    }

    private fun updateDateinViewPinjam() {
        val newFormat = "yyyy/MM/dd"
        val sdf = SimpleDateFormat(newFormat, Locale.US)
        edTglPinjam.setText(sdf.format(calendar.time))
    }

    private fun onEditMode() {
        edIdBuku.setText(i.getStringExtra("txtIdBuku"))
    }
}
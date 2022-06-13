package com.example.tugasbesar

import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class TambahBuku : AppCompatActivity() {

    lateinit var i:Intent

    lateinit var  btnTentangKami : Button
    lateinit var  btnLokasi : ImageView
    lateinit var  btnBeranda : Button

    lateinit var edIdBuku : EditText
    lateinit var edKodeBuku : EditText
    lateinit var edJudul : EditText
    lateinit var edPenulis : EditText
    lateinit var edPenerbit : EditText
    lateinit var edStok : EditText
    lateinit var edTahun : EditText
    lateinit var add : Button
    lateinit var update : Button
    lateinit var delete : Button


    val calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_buku)

        btnTentangKami = findViewById(R.id.btn_tentangkami)
        btnBeranda = findViewById(R.id.btn_beranda)
        btnLokasi = findViewById(R.id.map)

        edIdBuku =findViewById(R.id.ed_idBuku)
        edKodeBuku = findViewById(R.id.ed_KodeBuku)
        edJudul = findViewById(R.id.ed_JudulBuku)
        edPenulis = findViewById(R.id.ed_NamaPenulis)
        edPenerbit = findViewById(R.id.ed_penerbit)
        edStok = findViewById(R.id.ed_stok)
        edTahun = findViewById(R.id.ed_TahunTerbit)
        add = findViewById(R.id.btn_tambah)
        update = findViewById(R.id.btn_update)
        delete = findViewById(R.id.btn_Hapus)

        edIdBuku.visibility = View.GONE
        add.visibility = View.VISIBLE
        update.visibility = View.GONE
        delete.visibility = View.GONE


        i=intent
        if (i.hasExtra("editmode")){
            if (i.getStringExtra("editmode").equals("1")){
                onEditMode()
            }
        }

        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateinView()
            }

        }
        edTahun.setOnClickListener {
            DatePickerDialog(
                this,
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        btnTentangKami.setOnClickListener {
            val i = Intent(this@TambahBuku, TentangKami::class.java)
            startActivity(i)
        }
        btnBeranda.setOnClickListener {
            val i = Intent(this@TambahBuku, HalamanAdmin::class.java)
            startActivity(i)
        }
        btnLokasi.setOnClickListener {
            val i = Intent(this@TambahBuku, Lokasi::class.java)
            startActivity(i)
        }
        add.setOnClickListener {
            val loading = ProgressDialog(this)
            loading.setMessage("Menambahkan data...")
            loading.show()

            AndroidNetworking.post(ApiEndPoint.CREATE_buku)
                .addBodyParameter("kode_buku", edKodeBuku.text.toString())
                .addBodyParameter("judul_buku", edJudul.text.toString())
                .addBodyParameter("penulis", edPenulis.text.toString())
                .addBodyParameter("penerbit", edPenerbit.text.toString())
                .addBodyParameter("tahun_terbit", edTahun.text.toString())
                .addBodyParameter("stok_buku", edStok.text.toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {
                    override fun onResponse(response: JSONObject?) {
                        loading.dismiss()
                        Toast.makeText(applicationContext,response?.getString("message"), Toast.LENGTH_SHORT).show()

                        if(response?.getString("message")?.contains("successfully")!!){
                            this@TambahBuku.finish()
                        }
                        startActivity(Intent(this@TambahBuku,KelolaBuku::class.java))
                    }

                    override fun onError(anError: ANError?) {
                        loading.dismiss()
                        Log.d("ON ERROR", anError?.errorDetail.toString())
                        Toast.makeText(applicationContext, "Failure", Toast.LENGTH_SHORT).show()
                    }

                })
    }
        update.setOnClickListener {
            val loading = ProgressDialog(this)
            loading.setMessage("Mengubah data.........")
            loading.show()

            AndroidNetworking.post(ApiEndPoint.UPDATE_buku)
                .addBodyParameter("id_buku", edIdBuku.text.toString())
                .addBodyParameter("kode_buku", edKodeBuku.text.toString())
                .addBodyParameter("judul_buku", edJudul.text.toString())
                .addBodyParameter("penulis", edPenulis.text.toString())
                .addBodyParameter("penerbit", edPenerbit.text.toString())
                .addBodyParameter("tahun_terbit", edTahun.text.toString())
                .addBodyParameter("stok_buku", edStok.text.toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener{
                    override fun onResponse(response: JSONObject?) {
                        loading.dismiss()
                        Toast.makeText(applicationContext, response?.getString("message"), Toast.LENGTH_SHORT).show()

                        if (response?.getString("message")?.contains("successfully")!!){
                            this@TambahBuku.finish()
                        }
                        startActivity(Intent(this@TambahBuku,KelolaBuku::class.java))
                    }

                    override fun onError(anError: ANError?) {
                        loading.dismiss()
                        Log.d("ONERROR", anError?.errorDetail.toString())
                        Toast.makeText(applicationContext, "Connection Failure", Toast.LENGTH_SHORT).show()

                    }
                })
        }
        delete.setOnClickListener {
            val loading = ProgressDialog(this)
            loading.setMessage("Mengubah data.........")
            loading.show()

            AndroidNetworking.post(ApiEndPoint.DELETE_buku)
                .addBodyParameter("id_buku", edIdBuku.text.toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {
                    override fun onError(anError: ANError?) {
                        loading.dismiss()
                        Log.d("ONERROR", anError?.errorDetail.toString())
                        Toast.makeText(applicationContext, "Connection failure", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(response: JSONObject?) {
                        loading.dismiss()
                        Toast.makeText(applicationContext, response?.getString("message"), Toast.LENGTH_SHORT).show()
                        if (response?.getString("message")?.contains("successfully")!!){
                            this@TambahBuku.finish()
                        }
                        startActivity(Intent(this@TambahBuku,KelolaBuku::class.java))
                    }
                })

        }

}

    private fun onEditMode() {
        edIdBuku.setText(i.getStringExtra("txtIdBuku"))
        edKodeBuku.setText(i.getStringExtra("TxtKodeBuku"))
        edJudul.setText(i.getStringExtra("TxtJudul"))
        edPenulis.setText(i.getStringExtra("txtPenulis"))
        edPenerbit.setText(i.getStringExtra("txtPenerbit"))
        edTahun.setText(i.getStringExtra("txtTahun"))
        edStok.setText(i.getStringExtra("txtStok"))

        edIdBuku.isEnabled = false
        add.visibility = View.GONE
        update.visibility = View.VISIBLE
        delete.visibility = View.VISIBLE

    }

    private fun updateDateinView() {
        val newFormat = "yyyy/MM/dd"
        val sdf = SimpleDateFormat(newFormat, Locale.US)
        edTahun.setText(sdf.format(calendar.time))
    }
}
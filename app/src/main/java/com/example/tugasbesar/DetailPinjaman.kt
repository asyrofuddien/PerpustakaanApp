package com.example.tugasbesar

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import org.json.JSONObject
import java.util.ArrayList

class DetailPinjaman : AppCompatActivity() {

    lateinit var  btnTentangKami : Button
    lateinit var  btnLokasi : ImageView
    lateinit var  btnBeranda : Button

    private var arrayList = ArrayList<Peminjaman>()
    private lateinit var rvPeminjaman : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_pinjaman)

        btnTentangKami = findViewById(R.id.btn_tentangkami)
        btnBeranda = findViewById(R.id.btn_beranda)
        btnLokasi = findViewById(R.id.map)

        rvPeminjaman = findViewById(R.id.rvBuku)
        rvPeminjaman.setHasFixedSize(true)
        rvPeminjaman.layoutManager= LinearLayoutManager(this)

        btnTentangKami.setOnClickListener {
            val i = Intent(this@DetailPinjaman, TentangKami::class.java)
            startActivity(i)
        }
        btnBeranda.setOnClickListener {
            val i = Intent(this@DetailPinjaman, HalamanAdmin::class.java)
            startActivity(i)
        }
        btnLokasi.setOnClickListener {
            val i = Intent(this@DetailPinjaman, Lokasi::class.java)
            startActivity(i)
        }

        loadAllBuku()
    }

    private fun loadAllBuku() {
        val loading = ProgressDialog(this)
        loading.setMessage("Memuat data...")
        loading.show()

        AndroidNetworking.get(ApiEndPoint.READ_peminjaman)
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject?) {
                    arrayList.clear()
                    val jsonArray = response?.optJSONArray("result")

                    if (jsonArray?.length() == 0)  {
                        loading.dismiss()
                        Toast.makeText(applicationContext, "Data Buku Kosong", Toast.LENGTH_SHORT).show()
                    }

                    for (i in 0 until jsonArray?.length()!!){
                        val jsonObject = jsonArray.optJSONObject(i)
                        arrayList.add(Peminjaman(jsonObject.getString("id_pinjam"),jsonObject.getString("tanggal_pinjam"),
                            jsonObject.getString("tanggal_kembali"),jsonObject.getString("id_buku"),
                            jsonObject.getString("id_siswa"),jsonObject.getString("id_petugas")))

                        if (jsonArray.length()-1 == i){
                            loading.dismiss()
                        }
                    }

                    val adapter = RVAdapterDetailPeminjaman(applicationContext, arrayList)
                    adapter.notifyDataSetChanged()
                    rvPeminjaman.adapter = adapter
                    Log.i("DetailPinjaman ", "Load all notes : "+ arrayList.size.toString())
                }

                override fun onError(anError: ANError?) {
                    loading.dismiss()
                    Log.d("ON ERROR", anError?.errorDetail.toString())
                    Toast.makeText(applicationContext, "Failure," + anError.toString(), Toast.LENGTH_SHORT).show()
                }
            })
    }
}
package com.example.tugasbesar

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.google.android.material.snackbar.Snackbar
import org.json.JSONObject
import java.util.ArrayList

class KelolaBuku : AppCompatActivity() {

    private var arrayList = ArrayList<Buku>()
    private lateinit var rvBuku : RecyclerView
    lateinit var btnLokasi: ImageView
    lateinit var btnBeranda : Button
    lateinit var  btnTentangKami : Button
    lateinit var btnTambahBuku : View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kelola_buku)

        rvBuku = findViewById(R.id.rvBuku)
        rvBuku.setHasFixedSize(true)
        rvBuku.layoutManager=LinearLayoutManager(this)

        btnTambahBuku = findViewById(R.id.fab)
        btnTentangKami = findViewById(R.id.btn_tentangkami)
        btnBeranda = findViewById(R.id.btn_beranda)
        btnLokasi= findViewById(R.id.map)


        btnTambahBuku.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
            startActivity(Intent(this, TambahBuku::class.java))
        }
        btnTentangKami.setOnClickListener {
            val i = Intent(this@KelolaBuku, TentangKami::class.java)
            startActivity(i)
        }
        btnBeranda.setOnClickListener {
            val i = Intent(this@KelolaBuku, HalamanAdmin::class.java)
            startActivity(i)
        }
        btnLokasi.setOnClickListener {
           val i = Intent(this@KelolaBuku, Lokasi::class.java)
            startActivity(i)
        }


        loadAllBuku()
    }

    override fun onResume() {
        super.onResume()
        loadAllBuku()
    }

    private fun loadAllBuku() {
        val loading = ProgressDialog(this)
        loading.setMessage("Memuat data...")
        loading.show()

        AndroidNetworking.get(ApiEndPoint.READ_buku)
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
                        arrayList.add(Buku(jsonObject.getString("id_buku"),jsonObject.getString("kode_buku"),
                            jsonObject.getString("judul_buku"),jsonObject.getString("penulis"),
                            jsonObject.getString("penerbit"),jsonObject.getString("tahun_terbit"),
                        jsonObject.getString("stok_buku")))

                        if (jsonArray.length()-1 == i){
                            loading.dismiss()
                        }
                    }

                    val adapter = RVAdapterBuku(applicationContext, arrayList)
                    adapter.notifyDataSetChanged()
                    rvBuku.adapter = adapter
                    Log.i("KelolaBuku ", "Load all notes : "+ arrayList.size.toString())
                }

                override fun onError(anError: ANError?) {
                    loading.dismiss()
                    Log.d("ON ERROR", anError?.errorDetail.toString())
                    Toast.makeText(applicationContext, "Failure," + anError.toString(), Toast.LENGTH_SHORT).show()
                }
            })
    }
}
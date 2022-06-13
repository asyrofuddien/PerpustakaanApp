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

class DaftarPetugas : AppCompatActivity() {

    lateinit var edtTglLahir : EditText
    val calendar = Calendar.getInstance()
    lateinit var i : Intent
    lateinit var add : Button
    private lateinit var edNik : EditText
    private lateinit var edNama : EditText
    private lateinit var rgJenisKelamin : RadioGroup
    private lateinit var rbLaki : RadioButton
    private lateinit var rbPerempuan : RadioButton
    private lateinit var spPendidikan : Spinner
    private lateinit var spJabatan : Spinner
    private lateinit var edPass : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daftar_petugas)

        edNama =findViewById(R.id.ed_nama)
        edPass =findViewById(R.id.ed_pass)
        edNik =findViewById(R.id.ed_nik)
        rgJenisKelamin = findViewById(R.id.rb_jk)
        add = findViewById(R.id.btn_daftar)
        spPendidikan = findViewById(R.id.sp_pendidikan)
        spJabatan = findViewById(R.id.sp_jabatan)
        edtTglLahir = findViewById(R.id.ed_tgl_lahir)
        i = intent
        edtTglLahir = findViewById(R.id.ed_tgl_lahir)

        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateinView()
            }

        }
        edtTglLahir.setOnClickListener {
            DatePickerDialog(
                this,
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        add.setOnClickListener {
            val loading = ProgressDialog(this)
            loading.setMessage("Menambahkan data...")
            loading.show()

            val intSelectedRb = rgJenisKelamin.checkedRadioButtonId
            val radioButton = findViewById<RadioButton>(intSelectedRb)
            val gender = radioButton.text.toString()

            AndroidNetworking.post(ApiEndPoint.CREATE_petugas)
                .addBodyParameter("nik", edNik.text.toString())
                .addBodyParameter("nama_petugas", edNama.text.toString())
                .addBodyParameter("jenis_kelamin", gender)
                .addBodyParameter("tanggal_lahir", edtTglLahir.text.toString())
                .addBodyParameter("pendidikan_terakhir", spPendidikan.selectedItem.toString())
                .addBodyParameter("jabatan", spJabatan.selectedItem.toString())
                .addBodyParameter("password", edPass.text.toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {
                    override fun onResponse(response: JSONObject?) {
                        loading.dismiss()
                        Toast.makeText(applicationContext,response?.getString("message"),Toast.LENGTH_SHORT).show()

                        if(response?.getString("message")?.contains("successfully")!!){
                            this@DaftarPetugas.finish()

                        }
                        startActivity(Intent(this@DaftarPetugas,MainActivity::class.java))
                    }

                    override fun onError(anError: ANError?) {
                        loading.dismiss()
                        Log.d("ON ERROR", anError?.errorDetail.toString())
                        Toast.makeText(applicationContext, "Failure", Toast.LENGTH_SHORT).show()
                    }

                })

        }
    }
    private fun updateDateinView() {
        val newFormat = "yyyy/MM/dd"
        val sdf = SimpleDateFormat(newFormat, Locale.US)
        edtTglLahir.setText(sdf.format(calendar.time))
    }
}
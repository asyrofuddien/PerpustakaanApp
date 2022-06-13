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

class DaftarSiswa : AppCompatActivity() {

    lateinit var edtTglLahir : EditText
    val calendar = Calendar.getInstance()
    lateinit var i : Intent
    lateinit var add : Button
    private lateinit var edNisn : EditText
    private lateinit var edNama : EditText
    private lateinit var rgJenisKelamin : RadioGroup
    private lateinit var rbLaki : RadioButton
    private lateinit var rbPerempuan : RadioButton
    private lateinit var edPass : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daftar_siswa)

        edNama =findViewById(R.id.ed_nama)
        edPass =findViewById(R.id.ed_pass)
        edNisn =findViewById(R.id.ed_nisn)
        rgJenisKelamin = findViewById(R.id.rb_jk)
        add = findViewById(R.id.btn_daftar)
        edtTglLahir = findViewById(R.id.ed_tgl_lahir)
        i = intent


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

            AndroidNetworking.post(ApiEndPoint.CREATE_siswa)
                .addBodyParameter("NIS", edNisn.text.toString())
                .addBodyParameter("nama_siswa", edNama.text.toString())
                .addBodyParameter("jenis_kelamin", gender)
                .addBodyParameter("tanggal_lahir", edtTglLahir.text.toString())
                .addBodyParameter("password", edPass.text.toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener{
                    override fun onResponse(response: JSONObject?) {
                        loading.dismiss()
                        Toast.makeText(applicationContext,response?.getString("message"),Toast.LENGTH_SHORT).show()

                        if(response?.getString("message")?.contains("successfully")!!){
                            this@DaftarSiswa.finish()

                        }
                        startActivity(Intent(this@DaftarSiswa,MainActivity::class.java))
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
        val newFormat = "yyyy-MM-dd"
        val sdf = SimpleDateFormat(newFormat, Locale.US)
        edtTglLahir.setText(sdf.format(calendar.time))
    }


}
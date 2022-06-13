package com.example.tugasbesar

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList

class RVAdapterDetailPeminjaman(private val context: Context, private val arrayList: ArrayList<Peminjaman>)
    : RecyclerView.Adapter<RVAdapterDetailPeminjaman.Holder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.peminjaman_list,parent,false))
    }

    override fun getItemCount(): Int = arrayList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.tvIdBuku.text = "ID BUKU\t\t\t\t\t\t\t\t\t\t\t:\t"+this.arrayList.get(position).id_buku.toString()
        holder.tvIdSiswa.text = "ID SISWA\t\t\t\t\t\t\t\t\t\t:\t"+arrayList.get(position).id_siswa
        holder.tvIdPetugas.text = "ID PETUGAS\t\t\t\t\t\t\t:\t"+arrayList.get(position).id_petugas
        holder.tvTglPinjam.text = "TANGGAL PINJAM\t\t\t:\t"+arrayList.get(position).tanggal_pinjam
        holder.tvTglKembali.text = "TANGGAL KEMBALI\t\t:\t"+arrayList.get(position).tanggal_kembali

        Log.d("RVAdapter, ", "onBindViewHolder : " + arrayList.size.toString())
    }

    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val tvIdBuku : TextView
        val tvIdSiswa : TextView
        val tvIdPetugas : TextView
        val tvTglPinjam : TextView
        val tvTglKembali : TextView


        init {
            tvIdBuku = view.findViewById(R.id.tv_idBuku)
            tvIdSiswa = view.findViewById(R.id.tv_idSiswa)
            tvIdPetugas = view.findViewById(R.id.tv_idPetugas)
            tvTglPinjam = view.findViewById(R.id.tv_TanggalPinjam)
            tvTglKembali = view.findViewById(R.id.tv_TanggalKembali)
        }
    }

}

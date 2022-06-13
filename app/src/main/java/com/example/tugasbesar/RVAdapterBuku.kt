package com.example.tugasbesar

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class RVAdapterBuku (private val context: Context, private val arrayList: ArrayList<Buku>)
    : RecyclerView.Adapter<RVAdapterBuku.Holder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.buku_list,parent,false))
    }

    override fun getItemCount(): Int = arrayList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.tvIdBuku.text = "ID BUKU\t\t\t\t\t\t:\t"+this.arrayList.get(position).id_buku.toString()
        holder.tvKodeBuku.text = "KODE BUKU\t\t\t\t:\t"+arrayList.get(position).kode_buku
        holder.tvJudul.text = "JUDUL BUKU\t\t\t:\t"+arrayList.get(position).judul_buku
        holder.tvPenulis.text = "PENULIS\t\t\t\t\t\t:\t"+arrayList.get(position).penulis
        holder.tvPenerbit.text = "PENERBIT\t\t\t\t\t:\t"+arrayList.get(position).penerbit
        holder.tvTahunTerbit.text = "TAHUN TERBIT\t:\t"+arrayList.get(position).tahun_terbit
        holder.tvStokBuku.text = "STOK BUKU\t\t\t\t:\t"+arrayList.get(position).stok_buku

        holder.cvBuku.setOnClickListener {
            val i = Intent(context, TambahBuku::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            i.putExtra("editmode","1")
            i.putExtra("txtIdBuku", arrayList.get(position).id_buku)
            i.putExtra("TxtKodeBuku", arrayList.get(position).kode_buku)
            i.putExtra("TxtJudul", arrayList.get(position).judul_buku)
            i.putExtra("txtPenulis", arrayList.get(position).penulis)
            i.putExtra("txtPenerbit", arrayList.get(position).penerbit)
            i.putExtra("txtTahun", arrayList.get(position).tahun_terbit)
            i.putExtra("txtStok", arrayList.get(position).stok_buku)
            context.startActivity(i)
        }

        Log.d("RVAdapter, ", "onBindViewHolder : " + arrayList.size.toString())
    }

    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val tvIdBuku : TextView
        val tvKodeBuku : TextView
        val tvJudul : TextView
        val tvPenerbit : TextView
        val tvPenulis : TextView
        val tvTahunTerbit : TextView
        val tvStokBuku : TextView
        val cvBuku : CardView


        init {
            tvIdBuku = view.findViewById(R.id.tv_idBuku)
            tvKodeBuku = view.findViewById(R.id.tv_kode)
            tvPenerbit = view.findViewById(R.id.tv_penerbit)
            tvPenulis = view.findViewById(R.id.tv_penulis)
            tvTahunTerbit = view.findViewById(R.id.tv_tahun)
            tvJudul = view.findViewById(R.id.tv_judul)
            tvStokBuku = view.findViewById(R.id.tv_stok)
            cvBuku = view.findViewById(R.id.cvBuku)
        }
    }

}

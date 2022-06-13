package com.example.tugasbesar

class ApiEndPoint {
    companion object {
        private val SERVER = "http://192.168.48.179/tugasbesar/"

        //CREATE var
        val CREATE_siswa = SERVER+"create_siswa.php"
        val CREATE_petugas = SERVER+"create_petugas.php"
        val CREATE_buku = SERVER+"create_buku.php"
        val CREATE_peminjaman = SERVER+"create_peminjaman.php"


        //READ var
        val READ_buku = SERVER+"read_buku.php"
        val READ_peminjaman = SERVER+"read_peminjaman.php"

        //UPDATE var
        val UPDATE_buku = SERVER+"update_buku.php"

        //DELETE var
        val DELETE_buku = SERVER+"delete_buku.php"
    }
}
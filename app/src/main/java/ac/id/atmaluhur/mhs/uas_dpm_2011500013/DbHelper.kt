package ac.id.atmaluhur.mhs.uas_dpm_2011500013

import android.content.*
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbHelper (context:Context):SQLiteOpenHelper(context,"campus",null,1) {
    var Nidn = ""
    var NmDosen = ""
    var Jabatan = 0
    var Golongan = 0
    var Pendidikan = ""
    var Keahlian = ""
    var Studi = ""

    private val tabel ="campus"
    private var sql=""

    override fun onCreate(db: SQLiteDatabase?) {
        sql ="""create table lecturer $tabel(
        NIDN char(10) primary key,
        Nama_Dosen varchar(50) not null,
        Jabatan varchar(15) not null,
        golongan_pangkat varchar(30) not null,
        pendidikan char(2) not null,
        Keahlian varchar(30) not null
        Program_Studi varchar(50) not null,
        )
        """.trimIndent()
        db?.execSQL(sql)
    }
    override fun onUpgrade(db:SQLiteDatabase?, oldVersion :Int,newVersion :Int){
        sql="drop table if exists$tabel"
        db?.execSQL(sql)
    }

    fun simpan():Boolean{
        val db = writableDatabase
        val cv= ContentValues()
        with(cv){
            put("NIDN",Nidn)
            put("Nama_Dosen",NmDosen)
            put("Jabatan",Jabatan)
            put("golongan_pangkat",Golongan)
            put("pendidikan",Pendidikan)
            put("Keahlian",Keahlian)
            put("Program_Studi",Studi)
        }
        val cmd=db.insert(tabel,null,cv)
        db.close()
        return cmd !=-1L
    }
    fun ubah(kode: String): Boolean {
        val db = writableDatabase
        val cv = ContentValues()
        with(cv) {
            put("Nama_Dosen",NmDosen)
            put("Jabatan",Jabatan)
            put("golongan_pangkat",Golongan)
            put("pendidikan",Pendidikan)
            put("Keahlian",Keahlian)
            put("Program_Studi",Studi)
        }
        val cmd = db.update(tabel, cv, "NIDN = ?", arrayOf(kode))
        db.close()
        return cmd != -1
    }
    fun hapus(kode: String): Boolean{
        val db = writableDatabase
        val cmd = db.delete(tabel, "NIDN = ?", arrayOf(kode))
        return cmd != -1
    }

    fun tampil():Cursor{
        val db=writableDatabase
        val reader = db.rawQuery("select * from $tabel",null)
        return reader
    }
}
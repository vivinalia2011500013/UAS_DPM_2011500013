package ac.id.atmaluhur.mhs.uas_dpm_2011500013

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class entri_dosen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.entri_data_dosen)

        val modeEdit = intent.hasExtra("NIDN") && intent.hasExtra("Nama Dosen") &&
                intent.hasExtra("Jabatan") && intent.hasExtra("Golongan Pangkat") && intent.hasExtra(
            "Pendidikan Terakhir"
        ) &&
                intent.hasExtra("Bidang Keahlian") && intent.hasExtra("Program Studi")
        title = if (modeEdit) "Entri Data Dosen" else "Edit Data Dosen"

        val etNidn = findViewById<EditText>(R.id.etNidn)
        val etNmDosen = findViewById<EditText>(R.id.etNmDosen)
        val spnJabatan = findViewById<Spinner>(R.id.spnJabatan)
        val spnGolongan = findViewById<Spinner>(R.id.spnGolongan)
        val rdS2 = findViewById<RadioButton>(R.id.rdS2)
        val rdS3 = findViewById<RadioButton>(R.id.rdS3)
        val btnSimpan = findViewById<Button>(R.id.btnSimpan)
        val Jabatan = arrayOf("Tenaga Pengajar", "Asisten Ahli", "Lektor", "Lektor Kepala", "Guru Besar")
        val adpJabatan = ArrayAdapter(
            this@entri_dosen,
            android.R.layout.simple_spinner_dropdown_item,
            Jabatan
        )
        spnJabatan.adapter = adpJabatan
        val Golongan = arrayOf(
            "III/a - Penata Muda",
            "III/b - Penata Muda Tingkat I",
            "III/c - Penata",
            "III/d - Penata Tingkat I",
            "IV/a - Pembina",
            "IV/b - Pembina Tingkat I",
            "IV/c - Pembina Utama Muda",
            "IV/d - Pembina Utama Madya",
            "IV/e - Pembina Utama"
        )
        val adpGolongan = ArrayAdapter(
            this@entri_dosen,
            android.R.layout.simple_spinner_dropdown_item,
            Golongan
        )
        spnGolongan.adapter = adpGolongan
        val etKeahlian = findViewById<EditText>(R.id.etKeahlian)
        val etStudi = findViewById<EditText>(R.id.etStudi)

        if (modeEdit) {
            val Nidn = intent.getStringExtra("NIDN")
            val NmDosen = intent.getStringExtra("Nama Dosen")
            val jabatan = intent.getStringExtra("Jabatan",)
            val golongan = intent.getStringExtra("Golongan Pangkat",)
            val Pendidikan = intent.getStringExtra("Pendidikan Terakhir")
            val Bidang = intent.getStringExtra("Bidang Keahlian")
            val Studi = intent.getStringExtra("Program Studi")

            etNidn.setText(Nidn)
            etNmDosen.setText(NmDosen)
            spnJabatan.setSelection(Jabatan.indexOf(jabatan))
            spnGolongan.setSelection(Golongan.indexOf(golongan))
            if (Pendidikan == "S2") rdS2.isChecked = true else rdS3.isChecked = true
            etKeahlian.setText(Bidang)
            etStudi.setText(Studi)
        }
        etNidn.isEnabled = !modeEdit

        btnSimpan.setOnClickListener {
            if ("${etNidn.text}".isNotEmpty() && "${etNmDosen.text}".isNotEmpty() &&
                (rdS2.isChecked || rdS3.isChecked)
            ) {
                val db = DbHelper(this@entri_dosen)
                db.Nidn = "${etNidn.text}"
                db.NmDosen = "${etNmDosen.text}"
                db.Jabatan = spnJabatan.selectedItem as Int
                db.Golongan = spnGolongan.selectedItem as Int
                db.Pendidikan = if (rdS2.isChecked) "S2" else "S3"
                db.Keahlian = "${etKeahlian.text}"
                db.Studi = "${etStudi.text}"
                if (if (!modeEdit) db.simpan() else db.ubah("${etNidn.text}")) {

                    Toast.makeText(
                        this@entri_dosen,
                        "Data Dosen Berhasil Disimpan",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                } else
                    Toast.makeText(
                        this@entri_dosen,
                        "Data Dosen Gagal Disimpan",
                        Toast.LENGTH_SHORT
                    ).show()
            } else
                Toast.makeText(
                    this@entri_dosen,
                    "Data Dosen Belum Lengkap",
                    Toast.LENGTH_SHORT
                ).show()


        }
    }
}
package ac.id.atmaluhur.mhs.uas_dpm_2011500013

import android.app.Activity
import android.app.AlertDialog
import android.content.*
import android.view.*
import android.widget.*

class AdapterDataDosen (
    private val getContext: Context,
    private val customListItem:ArrayList<Data_Dosen>
): ArrayAdapter<Data_Dosen>(getContext,0,customListItem) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var listLayout = convertView
        val holder: ViewHolder
        if (listLayout == null) {
            val inflateList = (getContext as Activity).layoutInflater
            listLayout = inflateList.inflate(R.layout.layout_item, parent, false)
            holder = ViewHolder()
            with(holder) {
                tvNmDosen = listLayout.findViewById(R.id.tvNmDosen)
                tvNidn = listLayout.findViewById(R.id.tvNidn)
                tvStudi = listLayout.findViewById(R.id.tvStudi)
                btnEdit = listLayout.findViewById(R.id.btnEdit)
                btnHapus = listLayout.findViewById(R.id.btnHapus)
            }
            listLayout.tag = holder
        } else
            holder = listLayout.tag as ViewHolder
        val listItem = customListItem[position]
        holder.tvNmDosen!!.setText(listItem.NmDosen)
        holder.tvNidn!!.setText(listItem.Nidn)
        holder.tvStudi!!.setText(listItem.Studi)

        holder.btnEdit!!.setOnClickListener {
            val i = Intent(context, entri_dosen::class.java)
            i.putExtra("NIDN", listItem.Nidn)
            i.putExtra("Nama Dosen", listItem.NmDosen)
            i.putExtra("Golongan Pangkat", listItem.Golongan)
            i.putExtra("Program Studi", listItem.Studi)
            context.startActivity(i)

        }
        holder.btnHapus!!.setOnClickListener {
            val db = DbHelper(context)
            val alb = AlertDialog.Builder(context)
            val NamaDosen = holder.tvNmDosen!!.text
            val NIDN = holder.tvNidn!!.text
            with(alb) {
                setTitle("Konfirmasi Penghapusan")
                setCancelable(false)
                setMessage(
                    """
                        Apakah Anda Yakin Akan Menghapus Data Ini?
                        $NIDN[kode]
                        """.trimIndent()
                )
                setPositiveButton("Ya") { _, _ ->
                    if (db.hapus("$NamaDosen"))
                        Toast.makeText(
                            context,
                            "Data Mata Kuliah Berhasil Dihapus",
                            Toast.LENGTH_SHORT
                        ).show()
                    else
                        Toast.makeText(
                            context,
                            "Data Mata Kuliah Gagal Dihapus",
                            Toast.LENGTH_SHORT
                        ).show()
                }
                setNegativeButton("Tidak", null)
                create().show()
            }
        }
        return listLayout!!
    }

    class ViewHolder{

        internal var
                tvNmDosen: TextView? = null
        internal var
                tvNidn: TextView? = null
        internal var
                tvStudi: TextView? = null
        internal var
                btnEdit: ImageButton? = null
        internal var
                btnHapus: ImageButton? = null
    }
}
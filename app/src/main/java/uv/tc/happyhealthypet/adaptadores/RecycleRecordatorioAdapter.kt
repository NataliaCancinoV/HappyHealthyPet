package uv.tc.happyhealthypet.adaptadores

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import uv.tc.happyhealthypet.R
import uv.tc.happyhealthypet.poko.Recordatorio

class RecycleRecordatorioAdapter(var lista: ArrayList<Recordatorio>): RecyclerView.Adapter<RecycleRecordatorioAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var nombreRecordatorio: TextView = itemView.findViewById(R.id.tv_nombre_recordatorio_recycler)
        var fechaRecordatorio: TextView = itemView.findViewById(R.id.tv_fecha_recordatorio_recycler_recuperada)
        var horarioRecordatorio: TextView = itemView.findViewById(R.id.tv_horario_recordatorio_recycler_recuperada)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_recordatorio,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var recordatorio = lista[position]
        holder.nombreRecordatorio.text = recordatorio.nombreRecordatorio
        holder.fechaRecordatorio.text = recordatorio.fecha
        holder.horarioRecordatorio.text = recordatorio.horario
    }
}
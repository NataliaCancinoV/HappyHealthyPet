package uv.tc.happyhealthypet.adaptadores

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import uv.tc.happyhealthypet.R
import uv.tc.happyhealthypet.interfaces.ListenerRecyclerRecordatorios
import uv.tc.happyhealthypet.poko.Recordatorio

class RecycleRecordatorioAdapter(var lista: ArrayList<Recordatorio>, var bool: Boolean, var listener:ListenerRecyclerRecordatorios): RecyclerView.Adapter<RecycleRecordatorioAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var nombreRecordatorio: EditText = itemView.findViewById(R.id.et_nombre_recordatorio_recycler)
        var fechaRecordatorio: EditText = itemView.findViewById(R.id.et_fecha_recordatorio_recycler_recuperada)
        var horarioRecordatorio: EditText = itemView.findViewById(R.id.et_horario_recordatorio_recycler_recuperada)
        var btnEditar: ImageButton = itemView.findViewById(R.id.img_btn_edit)
        var nombreMascotaRecordatorio : EditText = itemView.findViewById(R.id.et_nombre_recordatorio_recycler_recuperada)
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
        holder.nombreRecordatorio.setText(recordatorio.nombreRecordatorio)
        holder.fechaRecordatorio.setText(recordatorio.fecha)
        holder.horarioRecordatorio.setText(recordatorio.horario)
        holder.nombreMascotaRecordatorio.setText(recordatorio.mascotaAsociada)

        holder.nombreRecordatorio.isEnabled=bool
        holder.fechaRecordatorio.isEnabled=bool
        holder.horarioRecordatorio.isEnabled=bool
        holder.nombreMascotaRecordatorio.isEnabled=bool

        holder.btnEditar.setOnClickListener{
            listener.clicEditarRecordatorio(position, holder.nombreRecordatorio.text.toString(), holder.fechaRecordatorio.text.toString(), holder.horarioRecordatorio.text.toString(), holder.nombreMascotaRecordatorio.text.toString())
        }
    }
}
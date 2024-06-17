package uv.tc.happyhealthypet.fragmentos

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import uv.tc.happyhealthypet.R
import uv.tc.happyhealthypet.adaptadores.RecycleRecordatorioAdapter
import uv.tc.happyhealthypet.interfaces.ListenerRecyclerRecordatorios
import uv.tc.happyhealthypet.modelo.RecordatoriosDB
import uv.tc.happyhealthypet.poko.Recordatorio

class VaccineFragment : Fragment() , ListenerRecyclerRecordatorios {
    private lateinit var recordatoriosDB: RecordatoriosDB
    private lateinit var listaRecordatorios: ArrayList<Recordatorio>
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecycleRecordatorioAdapter
    private var contador = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vaccine, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recordatoriosDB = RecordatoriosDB(requireContext())
        val archivoPreferenciasDefault = requireContext().getSharedPreferences("datosLogin", Context.MODE_PRIVATE)
        val correoShared = archivoPreferenciasDefault.getString("correo","")

        recyclerView = view.findViewById(R.id.recycler_vacunas)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        listaRecordatorios = arrayListOf<Recordatorio>()
        listaRecordatorios = recordatoriosDB.obtenerRecordatorios(correoShared.toString(),"Vacunas")
        var recordatorio = Recordatorio(null,correoShared.toString(),1,"Vacunas",
            "Kia","MedicinaDefault","12:00","10/07/2024")
        val btnAgregarVacuna : FloatingActionButton = view.findViewById(R.id.btn_agregar_recordatorio_vacuna)

        btnAgregarVacuna.setOnClickListener {
            listaRecordatorios.add(recordatorio)
            adapter= RecycleRecordatorioAdapter(listaRecordatorios,false, this)
            recyclerView.adapter = adapter
            val resultado = recordatoriosDB.agregarRecordatorio(recordatorio)
            if(resultado>0){
                Toast.makeText(requireContext(),"RECORDATORIO MEDICINA AGREGADO", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(requireContext(),"ERROR AL AGREGAR RECORDATORIO", Toast.LENGTH_SHORT).show()

            }
        }

    }
    override fun onResume() {
        super.onResume()
        adapter= RecycleRecordatorioAdapter(listaRecordatorios,false, this)
        recyclerView.adapter = adapter
    }


    override fun clicEditarRecordatorio(
        position: Int,
        nombreRecordatorio: String,
        fechaRecordatorio: String,
        horarioRecordatorio: String,
        nombreMascota: String
    ) {
        adapter= RecycleRecordatorioAdapter(listaRecordatorios,true, this)
        recyclerView.adapter = adapter
        val recordatorio = listaRecordatorios[position]
        Toast.makeText(requireContext(), "ID DEL RECORDATORIO : ${recordatorio.id}", Toast.LENGTH_SHORT).show()
        val archivoPreferenciasDefault = requireContext().getSharedPreferences("datosLogin", Context.MODE_PRIVATE)
        val correoShared = archivoPreferenciasDefault.getString("correo","")
        if(contador%2==0){
            // Toast.makeText(requireContext(), "DATOS NUEVOS GUARDADOS", Toast.LENGTH_SHORT).show()
            val nuevoRecodatorio = recordatoriosDB.actualizarRecordatorio(recordatorio.id!!,nombreRecordatorio,fechaRecordatorio,horarioRecordatorio, nombreMascota )
            if(nuevoRecodatorio>0){
                Toast.makeText(requireContext(), "DATOS EDITADOS CORRECTAMENTE", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(requireContext(), "ERROR AL EDITAR DATOS", Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(requireContext(), "DATOS PARA EDITAR", Toast.LENGTH_SHORT).show()

        }
        contador++
    }

    override fun clicEliminarRecordatorio(idRecordatorio: Recordatorio, position: Int) {

    }


}
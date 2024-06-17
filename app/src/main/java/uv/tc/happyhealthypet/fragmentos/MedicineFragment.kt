package uv.tc.happyhealthypet.fragmentos

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import uv.tc.happyhealthypet.NavActivity
import uv.tc.happyhealthypet.PetProfileActivity
import uv.tc.happyhealthypet.R
import uv.tc.happyhealthypet.adaptadores.RecycleRecordatorioAdapter
import uv.tc.happyhealthypet.interfaces.ListenerRecyclerRecordatorios
import uv.tc.happyhealthypet.modelo.RecordatoriosDB
import uv.tc.happyhealthypet.poko.Recordatorio

class MedicineFragment : Fragment(), ListenerRecyclerRecordatorios {
    private lateinit var recordatoriodDB: RecordatoriosDB
    private lateinit var listaRecordatorios: ArrayList<Recordatorio>
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecycleRecordatorioAdapter
    private var correo = ""
    private var contador = 1
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_medicine, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       // correo = arguments?.getString("correo")!!
     //   Toast.makeText(requireContext(),"CORREO DEL FRAMGNETO MEDICINA =$correo",Toast.LENGTH_LONG).show()
        val archivoPreferenciasDefault = requireContext().getSharedPreferences("datosLogin", Context.MODE_PRIVATE)
        val correoShared = archivoPreferenciasDefault.getString("correo","")
      //  Toast.makeText(requireContext(), "CORREO SHARED MEDICINA : ${correoShared}", Toast.LENGTH_SHORT).show()

        recordatoriodDB= RecordatoriosDB(requireContext())
        recordatoriodDB.crearTabla()

        val btnAgregar: FloatingActionButton = view.findViewById(R.id.btn_agregar_recordatorio)
        val btnAgregarMascota: ImageButton = view.findViewById(R.id.btn_agregar_mascota)


        recyclerView=view.findViewById(R.id.recycler_medicamentos)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        listaRecordatorios = arrayListOf<Recordatorio>()
        listaRecordatorios = recordatoriodDB.obtenerRecordatorios(correoShared.toString(),"Medicamentos")
        var recordatorio = Recordatorio(null,correoShared.toString(),1,"Medicamentos",
            "Kia","MedicinaDefault","12:00","10/07/2024")

        btnAgregar.setOnClickListener{
            listaRecordatorios.add(recordatorio)
            adapter= RecycleRecordatorioAdapter(listaRecordatorios,false, this)
            recyclerView.adapter = adapter
            val resultado = recordatoriodDB.agregarRecordatorio(recordatorio)
            if(resultado>0){
                Toast.makeText(requireContext(),"RECORDATORIO MEDICINA AGREGADO", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(requireContext(),"ERROR AL AGREGAR RECORDATORIO", Toast.LENGTH_SHORT).show()

            }
        }
        btnAgregarMascota.setOnClickListener{
            val intent = Intent(requireContext(), PetProfileActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onResume() {
        super.onResume()
        adapter= RecycleRecordatorioAdapter(listaRecordatorios,false, this)
        recyclerView.adapter = adapter
    }

    fun navDrawer(){
        val intent = Intent(requireContext(), NavActivity::class.java)
        intent.putExtra("navDrawer", "nav")
        startActivity(intent)
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
            val nuevoRecodatorio = recordatoriodDB.actualizarRecordatorio(recordatorio.id!!,nombreRecordatorio,fechaRecordatorio,horarioRecordatorio,nombreMascota )
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

    fun onClickScheduledDate(v: View?){

    }

}